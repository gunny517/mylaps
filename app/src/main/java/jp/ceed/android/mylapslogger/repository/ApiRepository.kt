package jp.ceed.android.mylapslogger.repository

import android.content.Context
import jp.ceed.android.mylapslogger.dao.PreferenceDao
import jp.ceed.android.mylapslogger.dto.LapDto
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

	fun sessionRequest(sessionId: Int, callback: (Result<PracticeResult>) -> Unit){
		val request = SessionRequest()
		request.sessionId = sessionId.toString()
		request.authorization = preferenceDao.read().accessToken
		request.executeRequest(context, object : Callback<SessionsResponse>{
			override fun success(sessionsResponse: SessionsResponse?, response: Response?) {
				sessionsResponse?.let {
					val practiceResult = PracticeResult(
						createLapList(it),
						createSessionData(it)
					)
					callback(Result.success(practiceResult))
				}
			}

			override fun failure(error: RetrofitError?) {
				callback(Result.failure(error?: IOException("unKnown")))
			}
		})
	}

	private fun createLapList(sessionsResponse: SessionsResponse): List<LapDto> {
		val lapList = ArrayList<LapDto>()
		for (session in sessionsResponse.sessions) {
			lapList.add(LapDto(session))
			val sessionBest: Float = parseBestLap(session.bestLap.duration)
			for (lap in session.laps) {
				val lapDto = LapDto(lap)
				applySpeedLevel(lapDto, sessionBest)
				lapList.add(lapDto)
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


	fun getActivities(callback: (Result<List<ActivitiesItem>>) -> Unit){
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
				callback(Result.failure(error?: IOException("unKnown")))
			}
		})
	}

	private fun applySpeedLevel(lapDto: LapDto, sessionBest: Float){
		try{
			val duration = lapDto.duration.toFloat()
			lapDto.speedLevel = (duration - (sessionBest - BEST_LAP_OFFSET)) * 0.1f
		}catch (e: NumberFormatException){
			lapDto.speedLevel = 1f
		}
	}

	private fun parseBestLap(bestLap: String): Float{
		return try{
			bestLap.toFloat()
		}catch (e: NumberFormatException){
			DEFAULT_BEST_LAP_TIME
		}
	}

	companion object{
		const val DEFAULT_BEST_LAP_TIME = 30.0f
		const val BEST_LAP_OFFSET = 2f
	}


}