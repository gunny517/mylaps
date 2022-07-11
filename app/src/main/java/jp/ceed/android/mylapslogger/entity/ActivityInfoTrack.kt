package jp.ceed.android.mylapslogger.entity

import androidx.room.ColumnInfo

data class ActivityInfoTrack(
    @ColumnInfo(name = "id") val trackId: Int,
    @ColumnInfo(name = "name") val trackName: String,
    @ColumnInfo(name = "data_time") val dateTime: String,
    @ColumnInfo(name = "fuel_consumption") val fuelConsumption: Float?,
    val description: String,
)
