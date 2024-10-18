package jp.ceed.android.mylapslogger.viewModel

import android.location.Location
import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ceed.android.mylapslogger.R
import jp.ceed.android.mylapslogger.dto.PracticeResultsItem
import jp.ceed.android.mylapslogger.entity.SessionInfo
import jp.ceed.android.mylapslogger.model.PracticeResult
import jp.ceed.android.mylapslogger.repository.ApiRepository
import jp.ceed.android.mylapslogger.repository.LocationRepository
import jp.ceed.android.mylapslogger.repository.PracticeResultsRepository
import jp.ceed.android.mylapslogger.repository.ResourceRepository
import jp.ceed.android.mylapslogger.repository.SessionInfoRepository
import jp.ceed.android.mylapslogger.repository.UserAccountRepository
import jp.ceed.android.mylapslogger.repository.WeatherRepository
import jp.ceed.android.mylapslogger.util.AppSettings
import jp.ceed.android.mylapslogger.util.DateUtil
import jp.ceed.android.mylapslogger.util.ExceptionUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PracticeResultComposeFragmentViewModel @Inject constructor (
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

    val practiceResult: MutableState<PracticeResult?> = mutableStateOf(null)

    val showProgress: MutableLiveData<Boolean> = MutableLiveData(false)

    val activityId: Long = state.get<Long>("activityId") ?: 0

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
                    showProgress.value = true
                    val result = apiRepository.getPracticeResult(
                        token = token,
                        activityId = activityId,
                        trackLength = trackLength,
                        sessionNo = sessionNo,
                    )
                    applySessionInfoLabel(result.sessionData)
                    applySessionInfoLabel(result.sessionSummary)
                    this@PracticeResultComposeFragmentViewModel.practiceResult.value =result
                    onLoadResult(result.dateStartTime)
                    showProgress.value = false
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
                practiceResultsRepository.sessionFlow.collect{ result ->
                    applySessionInfoLabel(result.sessionData)
                    applySessionInfoLabel(result.sessionSummary)
                    this@PracticeResultComposeFragmentViewModel.practiceResult.value = result
                    onLoadResult(result.dateStartTime)
                }
            }
        }
    }

    private fun applySessionInfoLabel(list: List<PracticeResultsItem>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                for (entry in list) {
                    when (entry) {
                        is PracticeResultsItem.Section -> {
                            entry.sessionInfoLabelColor = if (sessionInfoRepository.findBySessionId(entry.sessionId) == null) {
                                R.color.text_disabled
                            } else {
                                R.color.text_inverted
                            }
                        }
                        else -> continue
                    }
                }
            }
        }
    }

    private fun onLoadResult(dataStartTime: String?) {
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