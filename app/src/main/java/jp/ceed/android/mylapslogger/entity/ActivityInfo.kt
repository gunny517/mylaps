package jp.ceed.android.mylapslogger.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ActivityInfo(

	@PrimaryKey @ColumnInfo(name = "activity_id")
	val activityId: Int,

	var description: String,

	@ColumnInfo(name = "fuel_consumption")
	var fuelConsumption: Float?,

	@ColumnInfo(name = "track_id")
	var trackId: Int,

	@ColumnInfo(name = "date_time")
	var dateTime: String,
)