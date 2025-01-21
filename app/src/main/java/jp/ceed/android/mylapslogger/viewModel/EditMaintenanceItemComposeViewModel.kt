package jp.ceed.android.mylapslogger.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
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
    state: SavedStateHandle,
    val repository: MaintenanceItemRepository
): ViewModel() {
    private val itemId: Int? = state.get<Int>("id")

    lateinit var entity: MutableState<MaintenanceItem>

    var event: MutableLiveData<Event<EventState>> = MutableLiveData(Event(EventState.NONE))

    init {
        if (itemId == null ||itemId == 0) {
            entity = mutableStateOf(MaintenanceItem(0, ""))
        } else {
            loadMaintenanceItem(itemId)
        }
    }

    private fun loadMaintenanceItem(id: Int) {
        viewModelScope.launch {
            entity.value = repository.findById(id)
        }
    }

    fun saveItem(inputValue: String) {
        entity.value.name = inputValue
        if (itemId == null) {
            viewModelScope.launch {
                repository.insert(entity.value)
            }
        } else {
            viewModelScope.launch {
                repository.update(entity.value)
            }
        }
        event.value = Event(EventState.SAVED)
    }

}