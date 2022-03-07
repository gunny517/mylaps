package jp.ceed.android.mylapslogger.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import jp.ceed.android.mylapslogger.dto.ErrorLogItem
import jp.ceed.android.mylapslogger.repository.ErrorLogRepository
import kotlinx.coroutines.launch

class ErrorLogFragmentViewModel(application: Application) : AndroidViewModel(application) {

    var listItems: MutableLiveData<List<ErrorLogItem>> = MutableLiveData()

    private val errorLogRepository = ErrorLogRepository(application.applicationContext)

    init {
        loadItems()
    }

    private fun loadItems(){
        viewModelScope.launch {
            listItems.value = errorLogRepository.findAll()
        }
    }

}