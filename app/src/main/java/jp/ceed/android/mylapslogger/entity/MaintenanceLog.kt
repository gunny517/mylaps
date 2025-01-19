package jp.ceed.android.mylapslogger.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MaintenanceLog(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "issue_date") var issueDate: Long = 0L,
    @ColumnInfo(name = "running_time") var runningTime: Float = 0F,
    @ColumnInfo(name = "item_id") var itemId: Int = 0,
    var description: String? = null,
) {
    fun descriptionOrDefault(): String = description ?: ""
    
    fun setRunningTime(value: String) {
        try {
            runningTime = value.toFloat()
        } catch (e: NumberFormatException) {
            // Nothing to do.
        }
    }
}
