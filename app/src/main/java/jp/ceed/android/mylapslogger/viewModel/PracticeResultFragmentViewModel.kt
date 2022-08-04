package jp.ceed.android.mylapslogger.viewModel

import android.location.Location
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ceed.android.mylapslogger.R
import jp.ceed.android.mylapslogger.dto.PracticeResultsItem
import jp.ceed.android.mylapslogger.entity.SessionInfo
import jp.ceed.android.mylapslogger.model.PracticeResult
import jp.ceed.android.mylapslogger.repository.*
import jp.ceed.android.mylapslogger.util.AppSettings
import jp.ceed.android.mylapslogger.util.DateUtil
import jp.ceed.android.mylapslogger.util.ExceptionUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PracticeResultFragmentViewModel @Inject constructor (
    state: SavedStateHandle,
    var appSettings: AppSettings,
    var userAccountRepository: UserAccountRepository,
    var apiRepository: ApiRepository,
    var weatherRepository: WeatherRepository,
    var locationRepository: LocationRepository,
    var sessionInfoRepository: SessionInfoRepository,
    var exceptionUtil: ExceptionUtil,
    var resourceRepository: ResourceRepository,
    var practiceResultsRepository: PracticeResultsRepository,
) : ViewModel() {

    val practiceResult: MutableLiveData<PracticeResult> = MutableLiveData()

    val progressVisibility: MutableLiveData<Boolean> = MutableLiveData(false)

    val activityId: Int = state.get<Int>("activityId") ?: 0

    val trackLength: Int = state.get<Int>("trackLength") ?: 0

    @VisibleForTesting
    val sessionNo: Int = state.get<Int>("sessionNo") ?: 0

    init {
        if(appSettings.isAllowSessionAutoLoading()){
            startAutoLoading()
        }else{
            loadPracticeResult()
        }
    }

    fun loadPracticeResult() {
        userAccountRepository.getAccessToken()?.let { token ->
            viewModelScope.launch {
                try {
                    progressVisibility.value = true
                    applySessionInfoLabel(
                        apiRepository.getPracticeResult(
                            token = token,
                            activityId = activityId,
                            trackLength = trackLength,
                            sessionNo = sessionNo,
                        )
                    )
                    progressVisibility.value = false
                } catch (e: Exception) {
                    exceptionUtil.save(e, viewModelScope)
                }
            }
        }
    }

    private fun startAutoLoading(){
        userAccountRepository.getAccessToken()?.let { token ->
            viewModelScope.launch {
                practiceResultsRepository.args = PracticeResultsRepository.Args(
                    activityId = activityId,
                    token = token,
                    trackLength = trackLength,
                    sessionNo = sessionNo,
                )
                practiceResultsRepository.sessionFlow.collect{ practiceResult ->
                    applySessionInfoLabel(practiceResult)
                }
            }
        }
    }

    private fun applySessionInfoLabel(practiceResult: PracticeResult) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                for (entry in practiceResult.sessionData) {
                    when (entry) {
                        is PracticeResultsItem.Section -> {
                            entry.sessionInfoLabel = if (sessionInfoRepository.findBySessionId(entry.sessionId) == null) {
                                resourceRepository.getString(R.string.label_practice_result_section_no_session_info)
                            } else {
                                resourceRepository.getString(R.string.label_practice_result_section_has_session_info)
                            }
                        }
                        else -> continue
                    }
                }
                this@PracticeResultFragmentViewModel.practiceResult.postValue(practiceResult)
                onLoadResult(practiceResult.dateStartTime)
            }
        }
    }

    private fun onLoadResult(dataStartTime: String) {
        if (!DateUtil.isValidForWeather(dataStartTime)) {
            return
        }
        practiceResult.value?.let {
            val lastItem: PracticeResultsItem = it.sessionData[it.sessionData.size - 1]
            val sessionId: Long = when (lastItem) {
                is PracticeResultsItem.Section -> lastItem.sessionId
                is PracticeResultsItem.Lap -> lastItem.sessionId
                is PracticeResultsItem.Summary -> lastItem.sessionId
            }
            viewModelScope.launch {
                val sessionInfo = sessionInfoRepository.findBySessionId(sessionId)
                if (sessionInfo == null) {
                    locationRepository.getLocation { result ->
                        result.onSuccess { location ->
                            loadAndSaveWeatherData(location, sessionId)
                        }.onFailure { t ->
                            exceptionUtil.save(t, viewModelScope)
                        }
                    }
                }
            }
        }
    }

    private fun loadAndSaveWeatherData(location: Location, sessionId: Long) {
        viewModelScope.launch {
            val weatherDto = weatherRepository.getWeatherDataByLocationWithKtor(location.latitude, location.longitude)
            weatherDto?.let {
                saveSessionData(
                    SessionInfo(
                        sessionId = sessionId,
                        temperature = it.temperature,
                        humidity = it.humidity,
                        pressure = it.pressure
                    )
                )
            }
        }
    }

    private fun saveSessionData(sessionInfo: SessionInfo) {
        viewModelScope.launch {
            sessionInfoRepository.insert(sessionInfo)
        }
    }
}