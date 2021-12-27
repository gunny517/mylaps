package jp.ceed.android.mylapslogger.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import jp.ceed.android.mylapslogger.entity.ActivityInfo
import jp.ceed.android.mylapslogger.entity.Event
import jp.ceed.android.mylapslogger.entity.EventState
import jp.ceed.android.mylapslogger.repository.ActivityInfoRepository
import kotlinx.coroutines.launch

class ActivityInfoFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val sessionInfoRepository = ActivityInfoRepository(getApplication())

    var description: MutableLiveData<String> = MutableLiveData()

    var activityId: MutableLiveData<Int> = MutableLiveData()

    var onSaved: MutableLiveData<Event<EventState>> = MutableLiveData()

    var hadRecord = false


    fun loadSessionInfo(_activityId: Int) {
        activityId.value = _activityId
        viewModelScope.launch {
            sessionInfoRepository.findById(_activityId)?.let {
                description.value = it.description
                hadRecord = true
            }
        }
    }


    fun saveSessionInfo() {
        activityId.value?.let { it1 ->
            description.value?.let { it2 ->
                val dto = ActivityInfo(it1, it2)
                viewModelScope.launch {
                    if(hadRecord){
                        sessionInfoRepository.update(dto)
                    }else{
                        sessionInfoRepository.insert(dto)
                    }
                    onSaved.value = Event(EventState.SAVED)
                }
            }
        }
    }

}