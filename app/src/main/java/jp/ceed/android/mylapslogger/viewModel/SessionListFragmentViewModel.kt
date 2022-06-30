package jp.ceed.android.mylapslogger.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import jp.ceed.android.mylapslogger.model.SessionListItem
import jp.ceed.android.mylapslogger.repository.ApiRepository
import jp.ceed.android.mylapslogger.util.ExceptionUtil
import javax.inject.Inject

class SessionListFragmentViewModel(
    val activityId: Int
): ViewModel() {

    @Inject lateinit var apiRepository: ApiRepository

    @Inject lateinit var exceptionUtil: ExceptionUtil

    var sessionItemList: MutableLiveData<List<SessionListItem>> = MutableLiveData()

    var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)


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

    class Factory(val activityId: Int, val context: Context): ViewModelProvider.Factory{
        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SessionListFragmentViewModel(activityId) as T
        }

    }
}