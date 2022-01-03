package jp.ceed.android.mylapslogger.viewModel

import android.app.Application
import android.location.Location
import androidx.lifecycle.*
import jp.ceed.android.mylapslogger.entity.SessionInfo
import jp.ceed.android.mylapslogger.model.PracticeResult
import jp.ceed.android.mylapslogger.repository.ApiRepository
import jp.ceed.android.mylapslogger.repository.LocationRepository
import jp.ceed.android.mylapslogger.repository.SessionInfoRepository
import jp.ceed.android.mylapslogger.repository.WeatherRepository
import jp.ceed.android.mylapslogger.util.DateUtil
import kotlinx.coroutines.launch

class PracticeResultFragmentViewModel(val id: Int, val application: Application) : ViewModel() {

    private val apiRepository = ApiRepository(application)

    private val weatherRepository = WeatherRepository(application)

    private val locationRepository = LocationRepository(application.applicationContext)

    private val sessionInfoRepository = SessionInfoRepository(application.applicationContext)

    val lapList: MutableLiveData<PracticeResult> = MutableLiveData()

    val progressVisibility: MutableLiveData<Boolean> = MutableLiveData(false)

    var dataStartTime: String? = null


    init {
        getPracticeResult()
    }

    fun getPracticeResult() {
        if (lapList.value?.sessionData?.isNotEmpty() == true) {
            return
        }
        progressVisibility.value = true
        apiRepository.sessionRequest(id) {
            it.onSuccess { it3 ->
                lapList.value = it3
                dataStartTime = it3.dateStartTime
                onLoadResult()
            }.onFailure {
                // Nothing to do.
            }
            progressVisibility.value = false
        }
    }

    private fun onLoadResult(){
        if(DateUtil.isToday(dataStartTime)){
            return
        }
        lapList.value?.let{
            val lastItem = it.sessionData[it.sessionData.size - 1]
            viewModelScope.launch {
                val sessionInfo = sessionInfoRepository.findBySessionId(lastItem.sessionId)
                if(sessionInfo == null){
                    locationRepository.getLocation {
                        it.onSuccess { location ->
                            loadWeatherData(location, lastItem.sessionId)
                        }.onFailure {
                            // Nothing to do.
                        }
                    }
                }
            }
        }
    }


    private fun loadWeatherData(location: Location, sessionId: Long){
        viewModelScope.launch {
            weatherRepository.getWeatherDataByLocation(location.longitude, location.longitude){result ->
                result.onSuccess { weatherDto ->
                    saveSessionData(SessionInfo(
                        sessionId = sessionId,
                        temperature = weatherDto.temperature,
                        humidity = weatherDto.humidity,
                        pressure = weatherDto.pressure
                    ))
                }
            }
        }
    }

    private fun saveSessionData(sessionInfo: SessionInfo){
        viewModelScope.launch {
            sessionInfoRepository.insert(sessionInfo)
        }
    }

    class Factory(val id: Int, val application: Application) : ViewModelProvider.Factory {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PracticeResultFragmentViewModel(id, application) as T
        }
    }

}