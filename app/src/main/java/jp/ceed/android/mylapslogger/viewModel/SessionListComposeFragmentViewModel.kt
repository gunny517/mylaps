package jp.ceed.android.mylapslogger.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ceed.android.mylapslogger.entity.Event
import jp.ceed.android.mylapslogger.model.SessionListItem
import jp.ceed.android.mylapslogger.repository.ApiRepository
import jp.ceed.android.mylapslogger.repository.UserAccountRepository
import jp.ceed.android.mylapslogger.util.ExceptionUtil
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionListComposeFragmentViewModel @Inject constructor (
    var savedStateHandle: SavedStateHandle,
    var apiRepository: ApiRepository,
    var userAccountRepository: UserAccountRepository,
    var exceptionUtil: ExceptionUtil,
): ViewModel() {

    var sessionItemList: MutableState<List<SessionListItem>> = mutableStateOf(mutableListOf())

    var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    val activityId: Long = savedStateHandle.get<Long>("activityId") ?: throw IllegalStateException("Should have activityId")

    var onSessionClickEvent: MutableLiveData<Event<SessionListItem>> = MutableLiveData()

    init {
        loadSessionInfo()
    }

    fun loadSessionInfo(){
        userAccountRepository.getAccessToken()?.let { token ->
            viewModelScope.launch {
                isLoading.value = true
                try {
                    sessionItemList.value = apiRepository.loadPracticeResultsForSessionList(
                        activityId = activityId,
                        token = token,
                    )
                } catch (e: Exception) {
                    exceptionUtil.save(e, viewModelScope)
                }
                isLoading.value = false
            }
        }
    }

    fun onClickSession(item: SessionListItem) {
        onSessionClickEvent.value = Event(item)
    }
}
