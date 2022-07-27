package jp.ceed.android.mylapslogger.repository

import android.content.Context
import androidx.annotation.VisibleForTesting
import dagger.hilt.android.qualifiers.ApplicationContext
import jp.ceed.android.mylapslogger.datasource.ActivitiesApiDataSource
import jp.ceed.android.mylapslogger.datasource.SessionsApiDataSource
import jp.ceed.android.mylapslogger.dto.PracticeResultsItem
import jp.ceed.android.mylapslogger.entity.Practice
import jp.ceed.android.mylapslogger.model.ActivitiesItem
import jp.ceed.android.mylapslogger.model.PracticeResult
import jp.ceed.android.mylapslogger.model.SessionListItem
import jp.ceed.android.mylapslogger.network.response.Sessions
import jp.ceed.android.mylapslogger.util.AppSettings
import jp.ceed.android.mylapslogger.util.Util
import javax.inject.Inject

class ApiRepository @Inject constructor (
    @ApplicationContext val context: Context,
    private val activitiesApiDataSource: ActivitiesApiDataSource,
    private val sessionsApiDataSource: SessionsApiDataSource,
) {

    suspend fun loadPracticeResultForPracticeTable(token: String, activitiesItem: ActivitiesItem): Practice =
        Practice(
            activitiesItem = activitiesItem,
            sessions = sessionsApiDataSource.getSession(
                activityId = activitiesItem.id,
                token = token
            )
        )

    suspend fun loadPracticeResultsForSessionList(token: String, activityId: Int) =
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

    suspend fun getPracticeResult(token: String, activityId: Int, trackLength: Int, sessionNo: Int?): PracticeResult {
        val sessions = sessionsApiDataSource.getSession(
            activityId = activityId,
            token = token
        )
        return PracticeResult(
            sessionData = createLapList(sessions, sessionNo),
            sessionSummary =  createSessionData(sessions),
            dateStartTime = sessions.sessions[sessions.sessions.size - 1].dateTimeStart,
            bestLap = sessions.bestLap.duration,
            totalLap = sessions.stats.lapCount.toString(),
            totalTime = sessions.stats.activeTrainingTime,
            totalDistance = Util.createTrainingTimeString(sessions.stats.lapCount, trackLength)
        )
    }

    @VisibleForTesting
    fun createLapList(sessions: Sessions, sessionNo: Int?): List<PracticeResultsItem> {
        val showSpeedBar = AppSettings(context).isShowSpeedBar()
        val lapList = ArrayList<PracticeResultsItem>()
        for (session in sessions.sessions) {
            if(sessionNo != 0 && sessionNo != session.id){
                continue
            }
            lapList.add(PracticeResultsItem.Section(session))
            val sessionBest: Float = parseBestLap(session.bestLap.duration)
            for (lap in session.laps) {
                val item = PracticeResultsItem.Lap(lap, session)
                if(showSpeedBar){
                    applySpeedLevel(item, sessionBest)
                }
                lapList.add(item)
            }
        }
        return lapList
    }

    @VisibleForTesting
    fun createSessionData(sessions: Sessions): List<PracticeResultsItem> {
        val list: ArrayList<PracticeResultsItem> = ArrayList()
        for (session in sessions.sessions) {
            list.add(PracticeResultsItem.Section(session))
            list.add(PracticeResultsItem.Summary(session))
        }
        return list
    }

    suspend fun getActivities(userId: String): List<ActivitiesItem> =
        activitiesApiDataSource.getActivities(
            userId = userId
        ).activities.map { dto ->
            ActivitiesItem(dto)
        }

    private fun applySpeedLevel(item: PracticeResultsItem.Lap, sessionBest: Float) {
        try {
            val duration = item.duration.toFloat()
            item.speedLevel = (duration - (sessionBest - BEST_LAP_OFFSET)) * 0.1f
        } catch (e: NumberFormatException) {
            item.speedLevel = 0f
        }
    }

    private fun parseBestLap(bestLap: String): Float {
        return try {
            bestLap.toFloat()
        } catch (e: NumberFormatException) {
            DEFAULT_BEST_LAP_TIME
        }
    }


    companion object {

        const val DEFAULT_BEST_LAP_TIME = 30.0f
        const val BEST_LAP_OFFSET = 2f
    }

}