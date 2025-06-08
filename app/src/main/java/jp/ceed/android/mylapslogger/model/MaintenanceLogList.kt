package jp.ceed.android.mylapslogger.model

import jp.ceed.android.mylapslogger.entity.MaintenanceLog

data class MaintenanceLogList(
    val name: String,
    val logs: List<MaintenanceLog>
) {
}