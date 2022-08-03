package jp.ceed.android.mylapslogger.repository

import jp.ceed.android.mylapslogger.datasource.SessionsApiDataSource
import jp.ceed.android.mylapslogger.model.PracticeResult
import jp.ceed.android.mylapslogger.network.response.Sessions
import jp.ceed.android.mylapslogger.util.DateUtil
import jp.ceed.android.mylapslogger.util.SessionDataCreator
import jp.ceed.android.mylapslogger.util.Util
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PracticeResultsRepository @Inject constructor (
    val sessionsApiDataSource: SessionsApiDataSource,
    private val sessionDataCreator: SessionDataCreator,
) {

    lateinit var args: Args

    val sessionFlow: Flow<PracticeResult> = flow {
        while (true) {
            val sessions = sessionsApiDataSource.getSession(
                activityId = args.activityId,
                token = args.token,
            )
            emit(createSessionData(sessions))
            val nextLoadTime = calculateNextLoadTime(sessions)
            val currentTime = System.currentTimeMillis()
            if(nextLoadTime < currentTime) {
                break
            }
            val delayTime = nextLoadTime - currentTime
            delay(delayTime)
        }
    }

    private fun calculateNextLoadTime(sessions: Sessions): Long {
        val lastLap = sessions.sessions.last().laps.last()
        val lastLapStarted: Long = DateUtil.convertToTimeMillis(lastLap.dateTimeStart)
        val duration: Long = lastLap.duration.toFloat().toLong()
        return lastLapStarted + duration + duration + 1000L
    }

    private fun createSessionData(sessions: Sessions): PracticeResult =
        PracticeResult(
            sessionData = sessionDataCreator.createLapList(sessions, args.sessionNo),
            sessionSummary =  sessionDataCreator.createSessionData(sessions),
            dateStartTime = sessions.sessions[sessions.sessions.size - 1].dateTimeStart,
            bestLap = sessions.bestLap.duration,
            totalLap = sessions.stats.lapCount.toString(),
            totalTime = sessions.stats.activeTrainingTime,
            totalDistance = Util.createTrainingTimeString(sessions.stats.lapCount, args.trackLength)
        )

    data class Args(
        var activityId: Int,
        var token: String,
        var trackLength: Int,
        var sessionNo: Int,
    )

}