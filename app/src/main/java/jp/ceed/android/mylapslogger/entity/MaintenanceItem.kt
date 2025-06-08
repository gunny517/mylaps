package jp.ceed.android.mylapslogger.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MaintenanceItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var name: String = "",
)
