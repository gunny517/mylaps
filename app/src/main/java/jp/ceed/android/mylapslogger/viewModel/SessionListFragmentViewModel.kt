package jp.ceed.android.mylapslogger.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ceed.android.mylapslogger.model.SessionListItem
import jp.ceed.android.mylapslogger.repository.ApiRepository
import jp.ceed.android.mylapslogger.repository.UserAccountRepository
import jp.ceed.android.mylapslogger.util.ExceptionUtil
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionListFragmentViewModel @Inject constructor (
    var savedStateHandle: SavedStateHandle,
    var apiRepository: ApiRepository,
    var userAccountRepository: UserAccountRepository,
    var exceptionUtil: ExceptionUtil,
): ViewModel() {

    var sessionItemList: MutableLiveData<List<SessionListItem>> = MutableLiveData()

    var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    val activityId: Int = savedStateHandle.get<Int>("activityId") ?: throw IllegalStateException("Should have activityId")

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
}