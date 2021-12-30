package jp.ceed.android.mylapslogger.viewModel

import android.app.Application
import android.content.Context
import android.hardware.SensorManager
import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import jp.ceed.android.mylapslogger.entity.Event
import jp.ceed.android.mylapslogger.entity.EventState
import jp.ceed.android.mylapslogger.entity.SessionInfo
import jp.ceed.android.mylapslogger.repository.LocationRepository
import jp.ceed.android.mylapslogger.repository.SessionInfoRepository
import jp.ceed.android.mylapslogger.repository.WeatherRepository
import jp.ceed.android.mylapslogger.util.LogUtil
import kotlinx.coroutines.launch

class SessionInfoFragmentViewModel(val id: Long, val application: Application) : ViewModel() {

	private val sessionInfoRepository = SessionInfoRepository(application.applicationContext)

	private val locationRepository = LocationRepository(application.applicationContext)

	private val weatherRepository = WeatherRepository()

	var sessionInfo: MutableLiveData<SessionInfo> = MutableLiveData()

	var weatherApplyButtonEnable: MutableLiveData<Boolean> = MutableLiveData()

	private var isInsert: Boolean = false

	var onSaved: MutableLiveData<Event<EventState>> = MutableLiveData()

	private val sensorManager: SensorManager = application.getSystemService(Context.SENSOR_SERVICE) as SensorManager

	private var currentTemperature: Double? = null

	private var currentPressure: Int? = null

	private var currentHumidity: Int? = null



	init{
		loadSessionInfo(id)
		initLocation()
	}

	private fun loadSessionInfo(_sessionId: Long){
		viewModelScope.launch {
			val _sessionInfo: SessionInfo? = sessionInfoRepository.findBySessionId(_sessionId)
			sessionInfo.value = _sessionInfo ?: SessionInfo(sessionId = _sessionId)
			isInsert = _sessionInfo == null
		}
	}

	fun saveSessionInfo(){
		sessionInfo.value?.let {
			viewModelScope.launch {
				if(isInsert){
					sessionInfoRepository.insert(it)
				}else{
					sessionInfoRepository.update(it)
				}
				onSaved.value = Event(EventState.SAVED)
			}
		}
	}

	private fun initLocation(){
		locationRepository.getLocation {
			it.onSuccess { location ->
				getWeatherData(location)
			}.onFailure {
				// Nothing to do.
			}
		}
	}

	private fun getWeatherData(location: Location){
		weatherRepository.getWeatherData(location.latitude, location.longitude){ result ->
			result.onSuccess {
				currentTemperature = it.main.temp
				currentHumidity = it.main.humidity
				currentPressure = it.main.pressure
				weatherApplyButtonEnable.value = true
			}.onFailure {
				LogUtil.e(it.message)
			}
		}
	}

	fun applyWeatherData(){
		sessionInfo.value?.let {
			sessionInfo.value = SessionInfo(
				it.sessionId,
				currentTemperature.toString(),
				currentHumidity.toString(),
				currentPressure.toString(),
				it.description
			)
		}
	}

	class Factory(val id: Long, val application: Application): ViewModelProvider.Factory {
		@Suppress("unchecked_cast")
		override fun <T : ViewModel?> create(modelClass: Class<T>): T {
			return SessionInfoFragmentViewModel(id, application) as T
		}
	}

}
