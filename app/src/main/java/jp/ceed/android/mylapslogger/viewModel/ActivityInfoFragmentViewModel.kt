package jp.ceed.android.mylapslogger.viewModel

import android.app.Application
import androidx.lifecycle.*
import jp.ceed.android.mylapslogger.entity.ActivityInfo
import jp.ceed.android.mylapslogger.entity.Event
import jp.ceed.android.mylapslogger.entity.EventState
import jp.ceed.android.mylapslogger.repository.ActivityInfoRepository
import jp.ceed.android.mylapslogger.util.Util
import kotlinx.coroutines.launch

class ActivityInfoFragmentViewModel(val id: Int, val application: Application) : ViewModel() {

    private val sessionInfoRepository = ActivityInfoRepository(application)

    var description: MutableLiveData<String> = MutableLiveData()

    var onSaved: MutableLiveData<Event<EventState>> = MutableLiveData()

    var isUpdate = false


    init {
    	loadSessionInfo()
    }

    private fun loadSessionInfo() {
        viewModelScope.launch {
            sessionInfoRepository.findById(id)?.let {
                description.value = it.description
                isUpdate = true
                Util.checkThread(application, "viewModelScope.launch")
            }
        }
    }


    fun saveSessionInfo() {
        description.value?.let {
            val dto = ActivityInfo(id, it)
            viewModelScope.launch {
                if(isUpdate){
                    sessionInfoRepository.update(dto)
                }else{
                    sessionInfoRepository.insert(dto)
                }
                onSaved.value = Event(EventState.SAVED)
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