package jp.ceed.android.mylapslogger.viewModel

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ceed.android.mylapslogger.entity.Event
import jp.ceed.android.mylapslogger.entity.EventState
import jp.ceed.android.mylapslogger.model.ActivitiesItem
import jp.ceed.android.mylapslogger.repository.ApiRepository
import jp.ceed.android.mylapslogger.repository.UserAccountRepository
import jp.ceed.android.mylapslogger.service.PracticeDataService
import jp.ceed.android.mylapslogger.util.ExceptionUtil
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Inject

@DelicateCoroutinesApi
@HiltViewModel
class ActivitiesFragmentViewModel @Inject constructor (
    var apiRepository: ApiRepository,
    var userAccountRepository: UserAccountRepository,
    var exceptionUtil: ExceptionUtil,
    application: Application,
) : AndroidViewModel(application) {

    val activities: MutableLiveData<List<ActivitiesItem>> = MutableLiveData()

    val progressVisibility: MutableLiveData<Boolean> = MutableLiveData(false)

    var event: MutableLiveData<Event<EventState>> = MutableLiveData()

    enum class EventState{
        GO_TO_LOGIN,
        NONE,
    }

    fun checkAccount() {
        val token: String? = userAccountRepository.getAccessToken();
        val userId: String? = userAccountRepository.getUserId()
        if (token == null || userId == null) {
            event.value = Event(EventState.GO_TO_LOGIN)
        } else {
            event.value = Event(EventState.NONE)
            callActivitiesRequest()
        }
    }

    private fun callActivitiesRequest() {
        if (activities.value?.isNotEmpty() == true) {
            return
        }
        progressVisibility.value = true
        apiRepository.getActivities {
            it.onFailure { t ->
                exceptionUtil.save(t, viewModelScope)
            }.onSuccess { activities ->
                this.activities.postValue(activities)
                startPracticeService(activities)
            }
            progressVisibility.value = false
        }
    }

    private fun startPracticeService(activities: ArrayList<ActivitiesItem>){
        val context: Context = getApplication()
        val intent = Intent(context, PracticeDataService::class.java)
        intent.putParcelableArrayListExtra(PracticeDataService.PARAM_ACTIVITIES, activities)
        context.startService(intent)
    }
}