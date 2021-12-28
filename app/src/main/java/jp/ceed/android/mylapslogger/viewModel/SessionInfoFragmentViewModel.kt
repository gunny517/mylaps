package jp.ceed.android.mylapslogger.viewModel

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.*
import jp.ceed.android.mylapslogger.entity.Event
import jp.ceed.android.mylapslogger.entity.EventState
import jp.ceed.android.mylapslogger.entity.SessionInfo
import jp.ceed.android.mylapslogger.repository.SessionInfoRepository
import jp.ceed.android.mylapslogger.util.LogUtil
import jp.ceed.android.mylapslogger.util.Util
import kotlinx.coroutines.launch

class SessionInfoFragmentViewModel(val id: Long, val application: Application) : ViewModel(), SensorEventListener {

	private val sessionInfoRepository = SessionInfoRepository(application.applicationContext)

	var sessionInfo: MutableLiveData<SessionInfo> = MutableLiveData()

	private var isInsert: Boolean = false

	var onSaved: MutableLiveData<Event<EventState>> = MutableLiveData()

	private val sensorManager: SensorManager = application.getSystemService(Context.SENSOR_SERVICE) as SensorManager

	private var temperatureSensor: Sensor? = null

	private var pressureSensor: Sensor? = null

	private var humiditySensor: Sensor? = null

	private var currentTemperature: Float? = null

	private var currentPressure: Float? = null

	private var currentHumidity: Float? = null


	init{
		loadSessionInfo(id)
		initSensor()
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

	private fun initSensor(){
		Util.checkSensor(application)
		temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
		pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
		humiditySensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)
	}

	fun startSensor(){
		temperatureSensor?.let {
			sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
		}
		pressureSensor?.let{
			sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
		}
		humiditySensor?.let {
			sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
		}
	}

	fun stopSensor(){
		temperatureSensor?.let {
			sensorManager.unregisterListener(this, it)
		}
		pressureSensor?.let {
			sensorManager.unregisterListener(this, it)
		}
		humiditySensor?.let {
			sensorManager.unregisterListener(this, it)
		}
	}

	override fun onSensorChanged(sensorEvent: SensorEvent?) {
		sensorEvent?.let {
			when(it.sensor.type){
				Sensor.TYPE_AMBIENT_TEMPERATURE -> {
					currentTemperature = it.values[0]
					LogUtil.d("[temperature]" + it.values[0])
				}
				Sensor.TYPE_PRESSURE -> {
					currentPressure = it.values[0]
					LogUtil.d("[pressure]" + it.values[0])
				}
				Sensor.TYPE_RELATIVE_HUMIDITY -> {
					currentHumidity = it.values[0]
					LogUtil.d("[humidity]" + it.values[0])
				}
			}
		}
	}

	override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
		// Nothing to do.
	}

	class Factory(val id: Long, val application: Application): ViewModelProvider.Factory {
		@Suppress("unchecked_cast")
		override fun <T : ViewModel?> create(modelClass: Class<T>): T {
			return SessionInfoFragmentViewModel(id, application) as T
		}
	}

}
