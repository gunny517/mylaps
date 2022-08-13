package jp.ceed.android.mylapslogger.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ceed.android.mylapslogger.entity.Event
import jp.ceed.android.mylapslogger.model.ActivitiesItem
import jp.ceed.android.mylapslogger.repository.ApiRepository
import jp.ceed.android.mylapslogger.repository.UserAccountRepository
import jp.ceed.android.mylapslogger.util.ExceptionUtil
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivitiesFragmentViewModel @Inject constructor (
    var apiRepository: ApiRepository,
    var userAccountRepository: UserAccountRepository,
    var exceptionUtil: ExceptionUtil,
) : ViewModel() {

    val activities: MutableLiveData<List<ActivitiesItem>> = MutableLiveData()

    val showProgress: MutableLiveData<Boolean> = MutableLiveData(false)

    var event: MutableLiveData<Event<EventState>> = MutableLiveData()

    var userId: String? = null

    enum class EventState{
        GO_TO_LOGIN,
        START_PRACTICE_SERVICE,
        NONE,
    }

    init {
        checkAccount()
    }

    fun checkAccount() {
        userId = userAccountRepository.getUserId()
        if (userId == null) {
            event.value = Event(EventState.GO_TO_LOGIN)
        } else {
            event.value = Event(EventState.NONE)
            callActivitiesRequest()
        }
    }

    private fun callActivitiesRequest() {
        userId?.let {
            viewModelScope.launch {
                showProgress.value = true
                activities.value = emptyList()
                try {
                    activities.value = apiRepository.getActivities(it)
                    event.value = Event(EventState.START_PRACTICE_SERVICE)
                } catch (e: Exception) {
                    exceptionUtil.save(e, viewModelScope)
                }
                showProgress.value = false
            }
        }
    }

}