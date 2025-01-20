package jp.ceed.android.mylapslogger.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ceed.android.mylapslogger.entity.MaintenanceItem
import jp.ceed.android.mylapslogger.entity.MaintenanceLog
import jp.ceed.android.mylapslogger.repository.MaintenanceItemRepository
import jp.ceed.android.mylapslogger.repository.MaintenanceLogRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditMaintenanceLogComposeFragmentViewModel @Inject constructor(
    state: SavedStateHandle,
    private val maintenanceLogRepository: MaintenanceLogRepository,
    private val maintenanceItemRepository: MaintenanceItemRepository,
): ViewModel() {

    val maintenanceLog: MutableState<MaintenanceLog> = mutableStateOf(MaintenanceLog())

    val maintenanceItems: MutableState<List<MaintenanceItem>> = mutableStateOf(listOf())

    val logItemId: Int = state.get<Int>("maintenance_log_id") ?: 0

    init {
        if (logItemId != 0) {
            loadMaintenanceLog()
        }
        loadMaintenanceItem()
    }

    fun onClickSave(maintenanceLog: MaintenanceLog) {
        viewModelScope.launch {
            maintenanceLogRepository.save(maintenanceLog)
        }
    }

    private fun loadMaintenanceLog() {
        viewModelScope.launch {
            maintenanceLog.value = maintenanceLogRepository.findById(logItemId)
        }
    }

    private fun loadMaintenanceItem() {
        viewModelScope.launch {
            maintenanceItems.value = maintenanceItemRepository.findAll()
        }
    }

}