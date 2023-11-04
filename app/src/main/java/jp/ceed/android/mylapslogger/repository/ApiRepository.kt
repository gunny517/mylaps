package jp.ceed.android.mylapslogger.repository

import android.content.Context
import androidx.annotation.VisibleForTesting
import dagger.hilt.android.qualifiers.ApplicationContext
import jp.ceed.android.mylapslogger.datasource.ActivitiesApiDataSource
import jp.ceed.android.mylapslogger.datasource.SessionsApiDataSource
import jp.ceed.android.mylapslogger.entity.Practice
import jp.ceed.android.mylapslogger.model.ActivitiesItem
import jp.ceed.android.mylapslogger.model.PracticeResult
import jp.ceed.android.mylapslogger.model.SessionListItem
import jp.ceed.android.mylapslogger.network.response.Sessions
import jp.ceed.android.mylapslogger.util.SessionDataCreator
import jp.ceed.android.mylapslogger.util.Util
import javax.inject.Inject

class ApiRepository @Inject constructor (
    @ApplicationContext val context: Context,
    private val activitiesApiDataSource: ActivitiesApiDataSource,
    private val sessionsApiDataSource: SessionsApiDataSource,
    private val sessionDataCreator: SessionDataCreator,
) {

    suspend fun loadPracticeResultForPracticeTable(token: String, activitiesItem: ActivitiesItem): Practice =
        Practice(
            activitiesItem = activitiesItem,
            sessions = sessionsApiDataSource.getSession(
                activityId = activitiesItem.id,
                token = token
            )
        )

    suspend fun loadPracticeResultsForSessionList(token: String, activityId: Long) =
        createSessionItemList(
            sessions = sessionsApiDataSource.getSession(
                activityId = activityId,
                token = token
            )
        )

    @VisibleForTesting
    fun createSessionItemList(sessions: Sessions): List<SessionListItem> =
        sessions.sessions.map {
            SessionListItem(it, sessions.bestLap.duration)
        }

    suspend fun getPracticeResult(token: String, activityId: Long, trackLength: Int, sessionNo: Int?): PracticeResult {
        val sessions = sessionsApiDataSource.getSession(
            activityId = activityId,
            token = token
        )
        return PracticeResult(
            sessionData = sessionDataCreator.createLapList(sessions, sessionNo),
            sessionSummary =  sessionDataCreator.createSessionData(sessions),
            dateStartTime = sessions.sessions[sessions.sessions.size - 1].dateTimeStart,
            bestLap = sessions.bestLap.duration,
            totalLap = sessions.stats.lapCount.toString(),
            totalTime = sessions.stats.activeTrainingTime,
            totalDistance = Util.createTrainingTimeString(sessions.stats.lapCount, trackLength)
        )
    }

    suspend fun getActivities(userId: String): List<ActivitiesItem> =
        activitiesApiDataSource.getActivities(
            userId = userId
        ).activities.map { dto ->
            ActivitiesItem(dto)
        }
}