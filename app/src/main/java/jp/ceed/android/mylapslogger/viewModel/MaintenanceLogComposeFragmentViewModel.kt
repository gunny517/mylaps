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
import jp.ceed.android.mylapslogger.model.MaintenanceLogList
import jp.ceed.android.mylapslogger.usecase.MaintenanceLogUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MaintenanceLogComposeFragmentViewModel @Inject constructor (
    state: SavedStateHandle,
    private val useCase: MaintenanceLogUseCase,
): ViewModel() {

    val event: MutableLiveData<Event<EventState>> = MutableLiveData(Event(EventState.NONE))

    val maintenanceLogs: MutableState<List<MaintenanceLogList>> = mutableStateOf(mutableListOf())

    var selectedLogId: Int = 0


    fun loadMaintenanceLogs() {
        viewModelScope.launch {
            maintenanceLogs.value = useCase.loadMaintenanceLogList()
        }
    }

    fun onClickFab() {
        event.value = Event(EventState.ACTION_ADD)
    }

    fun onClickLogItem(logId: Int) {
        selectedLogId = logId
        event.value = Event(EventState.ITEM_SELECTED)
    }
}