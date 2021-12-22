package jp.ceed.android.mylapslogger.viewModel

import android.app.Application
import androidx.lifecycle.*
import jp.ceed.android.mylapslogger.entity.Event
import jp.ceed.android.mylapslogger.entity.SessionInfo
import jp.ceed.android.mylapslogger.repository.SessionInfoRepository
import kotlinx.coroutines.launch

class SessionInfoFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val sessionInfoRepository = SessionInfoRepository(getApplication())

    var description: MutableLiveData<String> = MutableLiveData()

    var sessionId: MutableLiveData<Int> = MutableLiveData()

    var onSaved: MutableLiveData<Event<String>> = MutableLiveData()

    var hasRecord = false


    fun loadSessionInfo(_sessionId: Int) {
        sessionId.value = _sessionId
        viewModelScope.launch {
            sessionInfoRepository.findBySessionId(_sessionId)?.let {
                description.value = it.description
                hasRecord = true
            }
        }
    }


    fun saveSessionInfo() {
        sessionId.value?.let { it1 ->
            description.value?.let { it2 ->
                val dto = SessionInfo(it1, it2)
                viewModelScope.launch {
                    if(hasRecord){
                        sessionInfoRepository.updateSessionInfo(dto)
                    }else{
                        sessionInfoRepository.insertSessionInfo(dto)
                    }
                }
            }
        }
        onSaved.value = Event("saved")
    }

}