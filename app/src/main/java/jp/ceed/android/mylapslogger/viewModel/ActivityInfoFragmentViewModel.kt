package jp.ceed.android.mylapslogger.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import jp.ceed.android.mylapslogger.ActivityInfoFragmentArgs
import jp.ceed.android.mylapslogger.entity.ActivityInfo
import jp.ceed.android.mylapslogger.entity.Event
import jp.ceed.android.mylapslogger.entity.EventState
import jp.ceed.android.mylapslogger.repository.ActivityInfoRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ActivityInfoFragmentViewModel(
    private val fragmentArgs: ActivityInfoFragmentArgs
) : ViewModel() {

    @Inject lateinit var sessionInfoRepository: ActivityInfoRepository

    var description: MutableLiveData<String> = MutableLiveData()

    var args: MutableLiveData<ActivityInfoFragmentArgs> = MutableLiveData(fragmentArgs)

    var onSaved: MutableLiveData<Event<EventState>> = MutableLiveData()

    private var isUpdate = false

    init {
    	loadActivityInfo()
    }

    private fun loadActivityInfo() {
        viewModelScope.launch {
            onLoadActivityInfo(sessionInfoRepository.findById(fragmentArgs.activityId))
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
            val dto = ActivityInfo(fragmentArgs.activityId, it)
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
            return ActivityInfoFragmentViewModel(args) as T
        }
    }

}