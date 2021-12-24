package jp.ceed.android.mylapslogger.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ActivityInfo(
	@PrimaryKey @ColumnInfo(name = "activity_id") val activityId: Int,
	var description: String
) {
}