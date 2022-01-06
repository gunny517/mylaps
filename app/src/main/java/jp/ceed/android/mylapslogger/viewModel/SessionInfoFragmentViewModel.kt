package jp.ceed.android.mylapslogger.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import jp.ceed.android.mylapslogger.args.SessionInfoFragmentParams
import jp.ceed.android.mylapslogger.entity.Event
import jp.ceed.android.mylapslogger.entity.EventState
import jp.ceed.android.mylapslogger.entity.SessionInfo
import jp.ceed.android.mylapslogger.repository.SessionInfoRepository
import kotlinx.coroutines.launch

class SessionInfoFragmentViewModel(params: SessionInfoFragmentParams, val application: Application) : ViewModel() {

    private val sessionInfoRepository = SessionInfoRepository(application.applicationContext)

    var sessionInfo: MutableLiveData<SessionInfo> = MutableLiveData()

    var weatherApplyButtonEnable: MutableLiveData<Boolean> = MutableLiveData()

    var averageDuration: MutableLiveData<String> = MutableLiveData()

    var medianDuration: MutableLiveData<String> = MutableLiveData()

    private var isInsert: Boolean = false

    var onSaved: MutableLiveData<Event<EventState>> = MutableLiveData()

    private var currentTemperature: String? = null

    private var currentPressure: String? = null

    private var currentHumidity: String? = null


    init {
        averageDuration.value = params.averageDuration
        medianDuration.value = params.medianDuration
        loadSessionInfo(params.sessionId)
    }

    private fun loadSessionInfo(_sessionId: Long) {
        viewModelScope.launch {
            val _sessionInfo: SessionInfo? = sessionInfoRepository.findBySessionId(_sessionId)
            sessionInfo.value = _sessionInfo ?: SessionInfo(sessionId = _sessionId)
            isInsert = _sessionInfo == null
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

    fun applyWeatherData() {
        sessionInfo.value?.let {
            sessionInfo.value = SessionInfo(
                sessionId = it.sessionId,
                temperature = currentTemperature,
                pressure = currentPressure,
                humidity = currentHumidity,
                description = it.description
            )
        }
    }

    fun clearEditText() {
        sessionInfo.value?.let {
            sessionInfo.value = SessionInfo(
                sessionId = it.sessionId
            )
        }
    }

    /**
     * [SessionInfoFragmentViewModel]にパラメータを渡すためのFactory
     */
    class Factory(val params: SessionInfoFragmentParams, val application: Application) : ViewModelProvider.Factory {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SessionInfoFragmentViewModel(params, application) as T
        }
    }

}
