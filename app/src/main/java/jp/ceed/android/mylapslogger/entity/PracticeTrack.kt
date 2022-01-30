package jp.ceed.android.mylapslogger.entity

import androidx.room.ColumnInfo
import androidx.room.Ignore

data class PracticeTrack(
    val id: Int,
    @ColumnInfo(name = "track_id") val trackId: Int,
    @ColumnInfo(name = "name") val trackName: String,
    @ColumnInfo(name = "lap_count") val lapCount: Int,
    @ColumnInfo(name = "best_lap") val bestLap: String,
    @ColumnInfo(name = "start_time") var startTime: String?,
    @ColumnInfo(name = "end_time") val endTime: String,
    @ColumnInfo(name = "total_training_time") val totalTrainingTime: String
)