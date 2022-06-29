package jp.ceed.android.mylapslogger.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import jp.ceed.android.mylapslogger.dao.PreferenceDao
import jp.ceed.android.mylapslogger.dto.PracticeResultsItem
import jp.ceed.android.mylapslogger.entity.Practice
import jp.ceed.android.mylapslogger.model.ActivitiesItem
import jp.ceed.android.mylapslogger.model.PracticeResult
import jp.ceed.android.mylapslogger.model.SessionListItem
import jp.ceed.android.mylapslogger.network.request.ActivitiesRequest
import jp.ceed.android.mylapslogger.network.request.SessionRequest
import jp.ceed.android.mylapslogger.network.response.ActivitiesResponse
import jp.ceed.android.mylapslogger.network.response.SessionsResponse
import jp.ceed.android.mylapslogger.util.AppSettings
import jp.ceed.android.mylapslogger.util.Util
import retrofit.Callback
import retrofit.RetrofitError
import retrofit.client.Response
import java.io.IOException
import java.util.*
import javax.inject.Inject

class ApiRepository @Inject constructor (@ApplicationContext val context: Context) {

    private val preferenceDao = PreferenceDao(context)

    private val userAccountRepository = UserAccountRepository(context)


    fun loadPracticeResultForPracticeTable(activitiesItem: ActivitiesItem, callback: (Result<Practice>) -> Unit){
        val request = SessionRequest()
        request.activityId = activitiesItem.id.toString()
        request.authorization = preferenceDao.read().accessToken
        request.executeRequest(context, object : Callback<SessionsResponse>{
            override fun success(sessionsResponse: SessionsResponse?, response: Response?) {
                sessionsResponse?.let {
                    sessionsResponse.bestLap?.let {
                        callback(Result.success(Practice(activitiesItem, sessionsResponse)))
                    }
                }
            }
            override fun failure(error: RetrofitError?) {
                callback(Result.failure(error ?: IOException("UnKnown")))
            }
        })
    }

    fun loadPracticeResultsForSessionList(activityId: Int, callback: (Result<List<SessionListItem>>) -> Unit) {
        val request = SessionRequest()
        request.activityId = activityId.toString()
        request.authorization = preferenceDao.read().accessToken
        request.executeRequest(context, object : Callback<SessionsResponse>{
            override fun success(sessionsResponse: SessionsResponse?, response: Response?) {
                sessionsResponse?.let {
                    callback(Result.success(createSessionItemList(sessionsResponse)))
                }
            }
            override fun failure(error: RetrofitError?) {
                callback(Result.failure(error ?: IOException("UnKnown")))
            }
        })
    }

    private fun createSessionItemList(sessionsResponse: SessionsResponse): List<SessionListItem>{
        val list = mutableListOf<SessionListItem>()
        for(entry in sessionsResponse.sessions){
            list.add(SessionListItem(entry, sessionsResponse.bestLap.duration))
        }
        return list
    }


    fun sessionRequest(activityId: Int, trackLength: Int, sessionNo: Int?, callback: (Result<PracticeResult>) -> Unit) {
        val request = SessionRequest()
        request.activityId = activityId.toString()
        request.authorization = preferenceDao.read().accessToken
        request.executeRequest(context, object : Callback<SessionsResponse> {
            override fun success(sessionsResponse: SessionsResponse?, response: Response?) {
                sessionsResponse?.let {
                    val practiceResult = PracticeResult(
                        sessionData = createLapList(it, sessionNo),
                        sessionSummary =  createSessionData(it),
                        dateStartTime = it.sessions.get(it.sessions.size - 1).dateTimeStart,
                        bestLap = it.bestLap.duration,
                        totalLap = it.stats.lapCount.toString(),
                        totalTime = it.stats.activeTrainingTime,
                        totalDistance = Util.createTrainingTimeString(it.stats.lapCount, trackLength)
                    )
                    callback(Result.success(practiceResult))
                }
            }

            override fun failure(error: RetrofitError?) {
                callback(Result.failure(error ?: IOException("unKnown")))
            }
        })
    }

    private fun createLapList(sessionsResponse: SessionsResponse, sessionNo: Int?): List<PracticeResultsItem> {
        val showSpeedBar = AppSettings(context).isShowSpeedBar()
        val lapList = ArrayList<PracticeResultsItem>()
        for (session in sessionsResponse.sessions) {
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

    private fun createSessionData(sessionsResponse: SessionsResponse): List<PracticeResultsItem> {
        val list: ArrayList<PracticeResultsItem> = ArrayList<PracticeResultsItem>()
        for (session in sessionsResponse.sessions) {
            list.add(PracticeResultsItem.Section(session))
            list.add(PracticeResultsItem.Summary(session))
        }
        return list
    }


    fun getActivities(callback: (Result<ArrayList<ActivitiesItem>>) -> Unit) {
        val request = ActivitiesRequest()
        request.userId = userAccountRepository.getUserId()
        request.executeRequest(context, object : Callback<ActivitiesResponse> {
            override fun success(activitiesResponse: ActivitiesResponse?, response: Response?) {
                activitiesResponse?.let {
                    val list = Util.convertToActivitiesItem(activitiesResponse.activities)
                    callback(Result.success(list))
                }
            }

            override fun failure(error: RetrofitError?) {
                callback(Result.failure(error ?: IOException("unKnown")))
            }
        })
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