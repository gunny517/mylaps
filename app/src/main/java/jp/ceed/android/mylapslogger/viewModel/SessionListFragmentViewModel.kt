package jp.ceed.android.mylapslogger.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ceed.android.mylapslogger.model.SessionListItem
import jp.ceed.android.mylapslogger.repository.ApiRepository
import jp.ceed.android.mylapslogger.util.ExceptionUtil
import javax.inject.Inject

@HiltViewModel
class SessionListFragmentViewModel @Inject constructor (
    var savedStateHandle: SavedStateHandle,
    var apiRepository: ApiRepository,
    var exceptionUtil: ExceptionUtil,
): ViewModel() {

    var sessionItemList: MutableLiveData<List<SessionListItem>> = MutableLiveData()

    var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    val activityId: Int = savedStateHandle.get<Int>("activityId") ?: throw IllegalStateException("Should have activityId")

    init {
        loadSessionInfo()
    }

    fun loadSessionInfo(){
        isLoading.value = true
        apiRepository.loadPracticeResultsForSessionList(activityId){
            it.onSuccess { result ->
                sessionItemList.value = result
            }.onFailure { t ->
                exceptionUtil.save(t, viewModelScope)
            }
            isLoading.value = false
        }
    }
}