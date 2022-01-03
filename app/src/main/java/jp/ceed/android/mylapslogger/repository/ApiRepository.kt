package jp.ceed.android.mylapslogger.repository

import android.content.Context
import jp.ceed.android.mylapslogger.dao.PreferenceDao
import jp.ceed.android.mylapslogger.dto.LapDto
import jp.ceed.android.mylapslogger.dto.PracticeResultsItem
import jp.ceed.android.mylapslogger.model.ActivitiesItem
import jp.ceed.android.mylapslogger.model.PracticeResult
import jp.ceed.android.mylapslogger.network.request.ActivitiesRequest
import jp.ceed.android.mylapslogger.network.request.SessionRequest
import jp.ceed.android.mylapslogger.network.response.ActivitiesResponse
import jp.ceed.android.mylapslogger.network.response.SessionsResponse
import jp.ceed.android.mylapslogger.util.Util
import retrofit.Callback
import retrofit.RetrofitError
import retrofit.client.Response
import java.io.IOException
import java.lang.NumberFormatException
import java.util.ArrayList

class ApiRepository(val context: Context) {

    private val preferenceDao = PreferenceDao(context)

    private val userAccountRepository = UserAccountRepository(context)

    fun sessionRequest(sessionId: Int, callback: (Result<PracticeResult>) -> Unit) {
        val request = SessionRequest()
        request.sessionId = sessionId.toString()
        request.authorization = preferenceDao.read().accessToken
        request.executeRequest(context, object : Callback<SessionsResponse> {
            override fun success(sessionsResponse: SessionsResponse?, response: Response?) {
                sessionsResponse?.let {
                    val practiceResult = PracticeResult(
                        createLapList(it),
                        createSessionData(it),
                        it.sessions.get(it.sessions.size - 1).dateTimeStart
                    )
                    callback(Result.success(practiceResult))
                }
            }

            override fun failure(error: RetrofitError?) {
                callback(Result.failure(error ?: IOException("unKnown")))
            }
        })
    }

    private fun createLapList(sessionsResponse: SessionsResponse): List<PracticeResultsItem> {
        val lapList = ArrayList<PracticeResultsItem>()
        for (session in sessionsResponse.sessions) {
            lapList.add(PracticeResultsItem.Section(session))
            val sessionBest: Float = parseBestLap(session.bestLap.duration)
            for (lap in session.laps) {
                val lapDto = PracticeResultsItem.Lap(lap, session)
                applySpeedLevel(lapDto, sessionBest)
                lapList.add(lapDto)
            }
        }
        return lapList
    }

    private fun createSessionData(sessionsResponse: SessionsResponse): List<PracticeResultsItem> {
        val list: ArrayList<PracticeResultsItem> = ArrayList<PracticeResultsItem>()
        for (session in sessionsResponse.sessions) {
            list.add(PracticeResultsItem.Section(session))
            list.add(PracticeResultsItem.Lap(session.bestLap))
        }
        return list
    }


    fun getActivities(callback: (Result<List<ActivitiesItem>>) -> Unit) {
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
            item.speedLevel = 1f
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