package jp.ceed.android.mylapslogger.repository

import android.content.Context
import jp.ceed.android.mylapslogger.dao.PreferenceDao
import jp.ceed.android.mylapslogger.dao.PreferenceDao.PrefsKey
import jp.ceed.android.mylapslogger.network.request.OAuthRequest
import jp.ceed.android.mylapslogger.network.response.OAuthResponse
import jp.ceed.android.mylapslogger.util.LogUtil
import retrofit.Callback
import retrofit.RetrofitError
import retrofit.client.Response
import java.io.IOException

class UserAccountRepository(val context: Context) {

	private val preferenceDao: PreferenceDao = PreferenceDao(context)


	fun getAccessToken(): String? {
		return preferenceDao.read(PreferenceDao.PrefsKey.ACCESS_TOKEN)
	}

	fun getUserId(): String? {
		return preferenceDao.read(PrefsKey.USER_ID)
	}


	fun requestLogin(userName: String, password: String, callback: LoginCallback){
		val request = OAuthRequest()
		request.userName = userName
		request.password = password
		request.executeRequest(context, object : Callback<OAuthResponse>{
			override fun success(oAuthResponse: OAuthResponse?, p1: Response?) {
				oAuthResponse?.let {
					preferenceDao.save(PrefsKey.ACCESS_TOKEN, oAuthResponse.accessToken)
					preferenceDao.save(PrefsKey.USER_ID, oAuthResponse.userId)
					preferenceDao.save(PrefsKey.ACCESS_TOKEN_EXPIRE, oAuthResponse.expires)
					callback.onFinish(Result.success(oAuthResponse))
				}
			}

			override fun failure(error: RetrofitError?) {
				LogUtil.e(error?.message)
				callback.onFinish(Result.failure(error?: IOException("unKnown")))
			}
		})
	}

	interface LoginCallback{
		fun onFinish(result: Result<OAuthResponse>)
	}
}