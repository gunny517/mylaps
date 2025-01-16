package jp.ceed.android.mylapslogger.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MaintenanceItem(
    @PrimaryKey val id: Int,
    var name: String,
)
