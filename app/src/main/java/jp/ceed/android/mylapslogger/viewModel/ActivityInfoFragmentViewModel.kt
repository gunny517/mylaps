package jp.ceed.android.mylapslogger.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import jp.ceed.android.mylapslogger.entity.ActivityInfo
import jp.ceed.android.mylapslogger.entity.Event
import jp.ceed.android.mylapslogger.repository.ActivityInfoRepository
import kotlinx.coroutines.launch

class ActivityInfoFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val sessionInfoRepository = ActivityInfoRepository(getApplication())

    var description: MutableLiveData<String> = MutableLiveData()

    var activityId: MutableLiveData<Int> = MutableLiveData()

    var onSaved: MutableLiveData<Event<String>> = MutableLiveData()

    var hasRecord = false


    fun loadSessionInfo(_sessionId: Int) {
        activityId.value = _sessionId
        viewModelScope.launch {
            sessionInfoRepository.findBySessionId(_sessionId)?.let {
                description.value = it.description
                hasRecord = true
            }
        }
    }


    fun saveSessionInfo() {
        activityId.value?.let { it1 ->
            description.value?.let { it2 ->
                val dto = ActivityInfo(it1, it2)
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