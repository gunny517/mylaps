package jp.ceed.android.mylapslogger.usecase

import jp.ceed.android.mylapslogger.model.MaintenanceLogList
import jp.ceed.android.mylapslogger.repository.MaintenanceItemRepository
import jp.ceed.android.mylapslogger.repository.MaintenanceLogRepository
import javax.inject.Inject


class MaintenanceLogUseCase @Inject constructor(
    private val maintenanceItemRepository: MaintenanceItemRepository,
    private val maintenanceLogRepository: MaintenanceLogRepository,
) {

    suspend fun loadMaintenanceLogList(): List<MaintenanceLogList> {
        return maintenanceItemRepository.findAll().map {
            MaintenanceLogList(it.name, maintenanceLogRepository.findByItemId(it.id))
        }
    }

}