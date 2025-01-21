package jp.ceed.android.mylapslogger.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ceed.android.mylapslogger.entity.Event
import jp.ceed.android.mylapslogger.entity.EventState
import jp.ceed.android.mylapslogger.entity.MaintenanceItem
import jp.ceed.android.mylapslogger.entity.MaintenanceLog
import jp.ceed.android.mylapslogger.extensions.findById
import jp.ceed.android.mylapslogger.usecase.EditMaintenanceLogUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditMaintenanceLogComposeFragmentViewModel @Inject constructor(
    state: SavedStateHandle,
    private val useCase: EditMaintenanceLogUseCase,
): ViewModel() {

    companion object {
        const val MAINTENANCE_LOG_ID = "maintenance_log_id"
    }

    val issueDate: MutableState<Long> = mutableLongStateOf(0L)

    val runningTime: MutableState<String> = mutableStateOf("")

    val itemId: MutableState<Int> = mutableIntStateOf(0)

    val itemName: MutableState<String> = mutableStateOf("")

    val description: MutableState<String> = mutableStateOf("")

    val logItems: MutableState<List<MaintenanceItem>> = mutableStateOf(listOf())

    private val logId: Int = state.get<Int>(MAINTENANCE_LOG_ID) ?: 0

    val event: MutableLiveData<Event<EventState>> = MutableLiveData(Event(EventState.NONE))

    init {
        loadMaintenanceLog()
    }

    fun onClickSave() {
        viewModelScope.launch {
            useCase.saveMaintenanceLog(
                MaintenanceLog(
                    id = logId,
                    issueDate = issueDate.value,
                    runningTime = runningTime.value.toFloat(),
                    itemId = itemId.value,
                    description = description.value
                )
            )
        }
        event.value = Event(EventState.SAVED)
    }

    private fun loadMaintenanceLog() {
        viewModelScope.launch {
            val editMaintenanceLog = useCase.loadEditMaintenanceLog(logId)
            issueDate.value = editMaintenanceLog.log.issueDate
            runningTime.value = editMaintenanceLog.log.runningTime.toString()
            itemId.value = editMaintenanceLog.log.itemId
            itemName.value = editMaintenanceLog.logItems.findById(editMaintenanceLog.log.itemId)?.name ?: ""
            description.value = editMaintenanceLog.log.descriptionOrEmptyString()
            logItems.value = editMaintenanceLog.logItems
        }
    }
}