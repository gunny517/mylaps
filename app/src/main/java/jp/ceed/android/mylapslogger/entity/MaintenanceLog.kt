package jp.ceed.android.mylapslogger.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MaintenanceLog(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "issue_date") val issueDate: Long,
    @ColumnInfo(name = "running_time") val runningTime: Int,
    @ColumnInfo(name = "item_id") val itemId: Int,
    val description: String?,
)
