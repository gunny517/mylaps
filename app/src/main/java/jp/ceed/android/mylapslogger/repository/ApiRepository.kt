package jp.ceed.android.mylapslogger.repository

import android.content.Context
import android.widget.Toast
import jp.ceed.android.mylapslogger.R
import jp.ceed.android.mylapslogger.dao.PreferenceDao
import jp.ceed.android.mylapslogger.dto.LapDto
import jp.ceed.android.mylapslogger.model.ActivitiesItem
import jp.ceed.android.mylapslogger.model.PracticeResult
import jp.ceed.android.mylapslogger.network.request.ActivitiesRequest
import jp.ceed.android.mylapslogger.network.request.SessionRequest
import jp.ceed.android.mylapslogger.network.response.ActivitiesResponse
import jp.ceed.android.mylapslogger.network.response.SessionsResponse
import jp.ceed.android.mylapslogger.network.response.SessionsResponse.Sessions
import jp.ceed.android.mylapslogger.network.response.SessionsResponse.Sessions.Laps
import jp.ceed.android.mylapslogger.util.Util
import retrofit.Callback
import retrofit.RetrofitError
import retrofit.client.Response
import java.io.IOException
import java.util.ArrayList

class ApiRepository(val context: Context) {

	private val preferenceDao = PreferenceDao(context)

	private val userAccountRepository = UserAccountRepository(context)

	fun sessionRequest(sessionId: Int, callback: GetPracticeResultCallback){
		val request = SessionRequest()
		request.sessionId = sessionId.toString()
		request.authorization = preferenceDao.read(PreferenceDao.PrefsKey.ACCESS_TOKEN)
		request.executeRequest(context, object : Callback<SessionsResponse>{
			override fun success(sessionsResponse: SessionsResponse?, response: Response?) {
				sessionsResponse?.let {
					val practiceResult = PracticeResult(
						createLapList(it),
						createSessionData(it)
					)
					callback.onFinish(Result.success(practiceResult))
				}
			}

			override fun failure(error: RetrofitError?) {
				callback.onFinish(Result.failure(error?: IOException("unKnown")))
			}
		})
	}

	private fun createLapList(sessionsResponse: SessionsResponse): List<LapDto> {
		val lapList = ArrayList<LapDto>()
		for (session in sessionsResponse.sessions) {
			lapList.add(LapDto(session))
			for (lap in session.laps) {
				lapList.add(LapDto(lap))
			}
		}
		return lapList
	}

	private fun createSessionData(sessionsResponse: SessionsResponse): List<LapDto> {
		val list: ArrayList<LapDto> = ArrayList<LapDto>()
		for (session in sessionsResponse.sessions) {
			list.add(LapDto(session))
			list.add(LapDto(session.bestLap))
		}
		return list
	}


	interface GetPracticeResultCallback{
		fun onFinish(result: Result<PracticeResult>)
	}


	fun getActivities(callback: GetActivitiesCallback){
		val request = ActivitiesRequest()
		request.userId = userAccountRepository.getUserId()
		request.executeRequest(context, object : Callback<ActivitiesResponse> {
			override fun success(activitiesResponse: ActivitiesResponse?, response: Response?) {
				activitiesResponse?.let {
					val list = Util.convertToActivitiesItem(activitiesResponse.activities)
					callback.onFinish(Result.success(list))
				}
			}
			override fun failure(error: RetrofitError?) {
				callback.onFinish(Result.failure(error?: IOException("unKnown")))
			}
		})
	}


	interface GetActivitiesCallback{
		fun onFinish(result: Result<List<ActivitiesItem>>)
	}

}