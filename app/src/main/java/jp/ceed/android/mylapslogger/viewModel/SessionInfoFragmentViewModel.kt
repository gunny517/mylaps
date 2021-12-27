package jp.ceed.android.mylapslogger.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import jp.ceed.android.mylapslogger.entity.Event
import jp.ceed.android.mylapslogger.entity.EventState
import jp.ceed.android.mylapslogger.entity.SessionInfo
import jp.ceed.android.mylapslogger.repository.SessionInfoRepository
import kotlinx.coroutines.launch

class SessionInfoFragmentViewModel(application: Application) : AndroidViewModel(application) {

	private val sessionInfoRepository = SessionInfoRepository(application.applicationContext)

	var sessionInfo: MutableLiveData<SessionInfo> = MutableLiveData()

	var isInsert: Boolean = false

	var onSaved: MutableLiveData<Event<EventState>> = MutableLiveData()


	fun loadSessionInfo(_sessionId: Int){
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

}
