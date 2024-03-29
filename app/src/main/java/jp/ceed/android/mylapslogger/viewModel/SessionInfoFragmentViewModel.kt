package jp.ceed.android.mylapslogger.viewModel

import android.location.Location
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ceed.android.mylapslogger.args.SessionInfoFragmentParams
import jp.ceed.android.mylapslogger.entity.Event
import jp.ceed.android.mylapslogger.entity.EventState
import jp.ceed.android.mylapslogger.entity.SessionInfo
import jp.ceed.android.mylapslogger.repository.LocationRepository
import jp.ceed.android.mylapslogger.repository.SessionInfoRepository
import jp.ceed.android.mylapslogger.repository.WeatherRepository
import jp.ceed.android.mylapslogger.util.ExceptionUtil
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionInfoFragmentViewModel @Inject constructor (
    savedStateHandle: SavedStateHandle,
    var sessionInfoRepository: SessionInfoRepository,
    var locationRepository: LocationRepository,
    var weatherRepository: WeatherRepository,
    var exceptionUtil: ExceptionUtil,
) : ViewModel() {

    var sessionInfo: MutableLiveData<SessionInfo> = MutableLiveData()

    var weatherButtonEnable: MutableLiveData<Boolean> = MutableLiveData()

    var averageDuration: MutableLiveData<String> = MutableLiveData()

    var medianDuration: MutableLiveData<String> = MutableLiveData()

    val progressVisibility: MutableLiveData<Boolean> = MutableLiveData(false)

    @VisibleForTesting
    var isInsert: Boolean = false

    var onSaved: MutableLiveData<Event<EventState>> = MutableLiveData()

    private var sessionId: Long? = null

    init {
        val params: SessionInfoFragmentParams = savedStateHandle.get("params") ?: throw IllegalStateException("Should have session params")
        averageDuration.value = params.averageDuration
        medianDuration.value = params.medianDuration
        loadSessionInfo(params.sessionId)
    }

    @VisibleForTesting
    fun loadSessionInfo(id: Long) {
        sessionId = id
        viewModelScope.launch {
            val entity: SessionInfo? = sessionInfoRepository.findBySessionId(id)
            sessionInfo.value = entity ?: SessionInfo(sessionId = id)
            isInsert = entity == null
            weatherButtonEnable.value = isInsert
        }
    }

    fun saveSessionInfo() {
        sessionInfo.value?.let {
            viewModelScope.launch {
                if (isInsert) {
                    sessionInfoRepository.insert(it)
                } else {
                    sessionInfoRepository.update(it)
                }
                onSaved.value = Event(EventState.SAVED)
            }
        }
    }

    fun clearEditText() {
        sessionInfo.value?.let {
            sessionInfo.value = SessionInfo(
                sessionId = it.sessionId
            )
        }
    }

    fun getLocationForWeather(){
        progressVisibility.value = true
        weatherButtonEnable.value = false
        sessionId?.let { _sessionId ->
            viewModelScope.launch {
                val sessionInfo = sessionInfoRepository.findBySessionId(_sessionId)
                if (sessionInfo == null) {
                    locationRepository.getLocation {
                        it.onSuccess { location ->
                            loadWeatherDataByKtor(location, _sessionId)
                        }.onFailure { t ->
                            exceptionUtil.save(t, viewModelScope)
                            progressVisibility.value = false
                            weatherButtonEnable.value = true
                        }
                    }
                }
            }
        }
    }

    private fun loadWeatherDataByKtor(location: Location, sessionId: Long) {
        viewModelScope.launch {
            val weatherDto = weatherRepository.getWeatherDataByLocationWithKtor(
                lat = location.latitude,
                lon = location.longitude
            )
            weatherDto?.let {
                sessionInfo.value = SessionInfo(
                    sessionId = sessionId,
                    temperature = weatherDto.temperature,
                    humidity = weatherDto.humidity,
                    pressure = weatherDto.pressure
                )
            }
            progressVisibility.value = false
            weatherButtonEnable.value = true
        }
    }
}
