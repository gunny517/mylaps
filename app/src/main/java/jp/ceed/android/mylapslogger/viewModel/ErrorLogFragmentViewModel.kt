package jp.ceed.android.mylapslogger.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ceed.android.mylapslogger.dto.ErrorLogItem
import jp.ceed.android.mylapslogger.repository.ErrorLogRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ErrorLogFragmentViewModel @Inject constructor(
    var errorLogRepository: ErrorLogRepository,
) : ViewModel() {

    var listItems: MutableLiveData<List<ErrorLogItem>> = MutableLiveData()

    init {
        loadItems()
    }

    private fun loadItems(){
        viewModelScope.launch {
            listItems.value = errorLogRepository.findAll()
        }
    }

}