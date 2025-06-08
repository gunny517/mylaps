package jp.ceed.android.mylapslogger.usecase

import jp.ceed.android.mylapslogger.entity.MaintenanceLog
import jp.ceed.android.mylapslogger.model.EditMaintenanceLog
import jp.ceed.android.mylapslogger.repository.MaintenanceItemRepository
import jp.ceed.android.mylapslogger.repository.MaintenanceLogRepository
import javax.inject.Inject


class EditMaintenanceLogUseCase @Inject constructor (
    private val maintenanceLogItemRepository: MaintenanceItemRepository,
    private val maintenanceLogRepository: MaintenanceLogRepository,
) {

    suspend fun loadEditMaintenanceLog(logId: Int): EditMaintenanceLog {
        val maintenanceLog = if (logId == 0) {
            MaintenanceLog()
        } else {
            maintenanceLogRepository.findById(logId)
        }
        return EditMaintenanceLog(
            logItems = maintenanceLogItemRepository.findAll(),
            log = maintenanceLog
        )
    }

    suspend fun saveMaintenanceLog(maintenanceLog: MaintenanceLog) {
        maintenanceLogRepository.save(maintenanceLog)
    }

    suspend fun deleteMaintenanceLog(maintenanceLog: MaintenanceLog) {
        maintenanceLogRepository.delete(maintenanceLog)
    }

}