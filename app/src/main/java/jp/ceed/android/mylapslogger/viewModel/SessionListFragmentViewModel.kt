package jp.ceed.android.mylapslogger.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import jp.ceed.android.mylapslogger.model.SessionListItem
import jp.ceed.android.mylapslogger.repository.ApiRepository
import jp.ceed.android.mylapslogger.util.ExceptionUtil

class SessionListFragmentViewModel(val context: Context, val activityId: Int): ViewModel() {

    private val apiRepository: ApiRepository = ApiRepository(context)

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
                ExceptionUtil(context).save(t, viewModelScope)
            }
            isLoading.value = false
        }
    }

    class Factory(val activityId: Int, val context: Context): ViewModelProvider.Factory{
        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SessionListFragmentViewModel(context, activityId) as T
        }

    }
}