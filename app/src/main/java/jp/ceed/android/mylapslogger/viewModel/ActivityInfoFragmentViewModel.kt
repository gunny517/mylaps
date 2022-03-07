package jp.ceed.android.mylapslogger.viewModel

import android.app.Application
import androidx.lifecycle.*
import jp.ceed.android.mylapslogger.ActivityInfoFragmentArgs
import jp.ceed.android.mylapslogger.entity.ActivityInfo
import jp.ceed.android.mylapslogger.entity.Event
import jp.ceed.android.mylapslogger.entity.EventState
import jp.ceed.android.mylapslogger.repository.ActivityInfoRepository
import jp.ceed.android.mylapslogger.util.Util
import kotlinx.coroutines.launch

class ActivityInfoFragmentViewModel(val _args: ActivityInfoFragmentArgs, val application: Application) : ViewModel() {

    private val sessionInfoRepository = ActivityInfoRepository(application)

    var description: MutableLiveData<String> = MutableLiveData()

    var args: MutableLiveData<ActivityInfoFragmentArgs> = MutableLiveData(_args)

    var onSaved: MutableLiveData<Event<EventState>> = MutableLiveData()

    private var isUpdate = false


    init {
    	loadActivityInfo()
    }


    private fun loadActivityInfo() {
        viewModelScope.launch {
            onLoadActivityInfo(sessionInfoRepository.findById(_args.activityId))
            Util.checkThread(application, "viewModelScope.launch")
        }
    }

    private fun onLoadActivityInfo(activityInfo: ActivityInfo?){
        activityInfo?.let {
            description.value = activityInfo.description
            isUpdate = true
        }
    }


    fun saveSessionInfo() {
        description.value?.let {
            val dto = ActivityInfo(_args.activityId, it)
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

    /**
     * [ActivityInfoFragmentViewModel]にパラメータを渡すためのFactory
     */
    class Factory(val args: ActivityInfoFragmentArgs, val application: Application): ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ActivityInfoFragmentViewModel(args, application) as T
        }
    }

}