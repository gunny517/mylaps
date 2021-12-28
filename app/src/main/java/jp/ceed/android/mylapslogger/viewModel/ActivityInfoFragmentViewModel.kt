package jp.ceed.android.mylapslogger.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import jp.ceed.android.mylapslogger.entity.ActivityInfo
import jp.ceed.android.mylapslogger.entity.Event
import jp.ceed.android.mylapslogger.entity.EventState
import jp.ceed.android.mylapslogger.repository.ActivityInfoRepository
import kotlinx.coroutines.launch

class ActivityInfoFragmentViewModel(val id: Int, val application: Application) : ViewModel() {

    private val sessionInfoRepository = ActivityInfoRepository(application)

    var description: MutableLiveData<String> = MutableLiveData()

    var activityId: MutableLiveData<Int> = MutableLiveData()

    var onSaved: MutableLiveData<Event<EventState>> = MutableLiveData()

    var hadRecord = false


    init {
    	loadSessionInfo(id)
    }

    private fun loadSessionInfo(_activityId: Int) {
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

    class Factory(val id: Int, val application: Application): ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ActivityInfoFragmentViewModel(id, application) as T
        }
    }

}