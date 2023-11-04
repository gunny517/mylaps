package jp.ceed.android.mylapslogger.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import jp.ceed.android.mylapslogger.model.ActivitiesItem
import jp.ceed.android.mylapslogger.network.response.Sessions

@Entity
data class Practice(
    @ColumnInfo(name = "activity_id") @PrimaryKey var activityId: Long,
    @ColumnInfo(name = "track_id") var trackId: Int,
    @ColumnInfo(name = "lap_count") var lapCount: Int,
    @ColumnInfo(name = "best_lap") var bestLap: String,
    @ColumnInfo(name = "start_time") val startTime: String,
    @ColumnInfo(name = "end_time") var endTime: String,
    @ColumnInfo(name = "display_time") val displayTime: String?,
    @ColumnInfo(name = "total_training_time") var totalTrainingTime: String,
    @ColumnInfo(name = "active_training_time") var activeTrainingTime: String
){
    constructor(activitiesItem: ActivitiesItem, sessions: Sessions): this(
        activityId = activitiesItem.id,
        trackId = activitiesItem.locationId,
        lapCount = sessions.stats.lapCount,
        bestLap = sessions.bestLap.duration,
        startTime = activitiesItem.startTime,
        endTime = activitiesItem.endTimeRaw,
        displayTime = activitiesItem.displayTime,
        totalTrainingTime = sessions.stats.totalTrainingTime,
        activeTrainingTime = sessions.stats.activeTrainingTime
    )
}