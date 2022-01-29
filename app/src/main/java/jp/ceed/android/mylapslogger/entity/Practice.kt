package jp.ceed.android.mylapslogger.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import jp.ceed.android.mylapslogger.model.ActivitiesItem
import jp.ceed.android.mylapslogger.network.response.SessionsResponse
import jp.ceed.android.mylapslogger.util.DateUtil

@Entity
data class Practice(
    @ColumnInfo(name = "id") @PrimaryKey var id: Int,
    @ColumnInfo(name = "track_id") var trackId: Int,
    @ColumnInfo(name = "lap_count") var lapCount: Int,
    @ColumnInfo(name = "best_lap") var bestLap: String,
    @ColumnInfo(name = "start_time") var startTime: String,
    @ColumnInfo(name = "end_time") var endTime: String,
    @ColumnInfo(name = "total_training_time") var totalTrainingTime: String
){
    constructor(activitiesItem: ActivitiesItem, sessionsResponse: SessionsResponse): this(
        id = activitiesItem.id,
        trackId = activitiesItem.locationId,
        lapCount = sessionsResponse.stats.lapCount,
        bestLap = sessionsResponse.bestLap.duration,
        startTime = activitiesItem.startTimeRaw,
        endTime = activitiesItem.endTimeRaw,
        totalTrainingTime = sessionsResponse.stats.totalTrainingTime
    )
}