package jp.ceed.android.mylapslogger.model

import jp.ceed.android.mylapslogger.entity.MaintenanceItem
import jp.ceed.android.mylapslogger.entity.MaintenanceLog

data class EditMaintenanceLog(
    val logItems: List<MaintenanceItem> = listOf(),
    val log: MaintenanceLog = MaintenanceLog(),
)