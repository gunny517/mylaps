package jp.ceed.android.mylapslogger.entity

import androidx.room.ColumnInfo

data class PracticeTrack(
    @ColumnInfo(name = "activity_id") val activityId: Long,
    @ColumnInfo(name = "track_id") val trackId: Int,
    @ColumnInfo(name = "name") val trackName: String,
    @ColumnInfo(name = "lap_count") val lapCount: Int,
    @ColumnInfo(name = "best_lap") val bestLap: String,
    @ColumnInfo(name = "start_time") val startTime: String,
    @ColumnInfo(name = "display_time") var displayTime: String?,
    @ColumnInfo(name = "end_time") val endTime: String,
    @ColumnInfo(name = "total_training_time") val totalTrainingTime: String,
    @ColumnInfo(name = "track_length") val trackLength: Int
)