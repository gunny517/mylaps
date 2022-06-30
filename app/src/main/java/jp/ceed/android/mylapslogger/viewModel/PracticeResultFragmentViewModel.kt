package jp.ceed.android.mylapslogger.viewModel

import android.app.Application
import android.location.Location
import androidx.lifecycle.*
import jp.ceed.android.mylapslogger.PracticeResultsFragmentArgs
import jp.ceed.android.mylapslogger.R
import jp.ceed.android.mylapslogger.dto.PracticeResultsItem
import jp.ceed.android.mylapslogger.entity.SessionInfo
import jp.ceed.android.mylapslogger.model.PracticeResult
import jp.ceed.android.mylapslogger.repository.ApiRepository
import jp.ceed.android.mylapslogger.repository.LocationRepository
import jp.ceed.android.mylapslogger.repository.SessionInfoRepository
import jp.ceed.android.mylapslogger.repository.WeatherRepository
import jp.ceed.android.mylapslogger.util.DateUtil
import jp.ceed.android.mylapslogger.util.ExceptionUtil
import jp.ceed.android.mylapslogger.util.LogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PracticeResultFragmentViewModel(val args: PracticeResultsFragmentArgs, val application: Application) : ViewModel() {

    @Inject lateinit var apiRepository: ApiRepository

    @Inject lateinit var weatherRepository: WeatherRepository

    @Inject lateinit var locationRepository: LocationRepository

    @Inject lateinit var sessionInfoRepository:SessionInfoRepository

    val practiceResult: MutableLiveData<PracticeResult> = MutableLiveData()

    val progressVisibility: MutableLiveData<Boolean> = MutableLiveData(false)


    init {
        loadPracticeResult()
    }

    fun loadPracticeResult() {
        progressVisibility.value = true
        apiRepository.sessionRequest(args.activityId, args.trackLength, args.sessionNo) {
            it.onSuccess { practiceResult ->
                applySessionInfoLabel(practiceResult)
            }.onFailure { t ->
                ExceptionUtil(application).save(t, viewModelScope)
            }
            progressVisibility.value = false
        }
    }

    private fun applySessionInfoLabel(practiceResult: PracticeResult) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                for (entry in practiceResult.sessionData) {
                    when (entry) {
                        is PracticeResultsItem.Section -> {
                            entry.sessionInfoLabel = if (sessionInfoRepository.findBySessionId(entry.sessionId) == null) {
                                application.getString(R.string.label_practice_result_section_no_session_info)
                            } else {
                                application.getString(R.string.label_practice_result_section_has_session_info)
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
                            loadWeatherData(location, sessionId)
                        }.onFailure { t ->
                            ExceptionUtil(application).save(t, viewModelScope)
                        }
                    }
                }
            }
        }
    }


    private fun loadWeatherData(location: Location, sessionId: Long) {
        weatherRepository.getWeatherDataByLocation(location.latitude, location.longitude) { result ->
            result.onSuccess { weatherDto ->
                saveSessionData(
                    SessionInfo(
                        sessionId = sessionId,
                        temperature = weatherDto.temperature,
                        humidity = weatherDto.humidity,
                        pressure = weatherDto.pressure
                    )
                )
            }.onFailure { t ->
                ExceptionUtil(application).save(t, viewModelScope)
            }
        }
    }

    private fun saveSessionData(sessionInfo: SessionInfo) {
        viewModelScope.launch {
            sessionInfoRepository.insert(sessionInfo)
        }
    }

    /**
     * [PracticeResultFragmentViewModel]にパラメータを渡すためのFactory
     */
    class Factory(private val practiceResultsFragmentArgs: PracticeResultsFragmentArgs, val application: Application) : ViewModelProvider.Factory {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PracticeResultFragmentViewModel(practiceResultsFragmentArgs, application) as T
        }
    }

}