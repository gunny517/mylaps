package jp.ceed.android.mylapslogger.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ceed.android.mylapslogger.entity.Event
import jp.ceed.android.mylapslogger.entity.EventState
import jp.ceed.android.mylapslogger.entity.MaintenanceItem
import jp.ceed.android.mylapslogger.repository.MaintenanceItemRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditMaintenanceItemComposeViewModel @Inject constructor(
    val repository: MaintenanceItemRepository
): ViewModel() {

    var items: MutableState<List<MaintenanceItem>> = mutableStateOf(mutableListOf())

    var event: MutableLiveData<Event<EventState>> = MutableLiveData(Event(EventState.NONE))

    init {
        loadMaintenanceItems()
    }

    private fun loadMaintenanceItems() {
        viewModelScope.launch {
            items.value = repository.findAll()
        }
    }

    fun saveItem(item: MaintenanceItem) {
        viewModelScope.launch {
            repository.save(item)
        }
        event.value = Event(EventState.SAVED)
    }

    fun deleteItem(item: MaintenanceItem) {
        viewModelScope.launch {
            repository.delete(item)
        }
        event.value = Event(EventState.SAVED)
    }

}