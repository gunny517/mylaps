package jp.ceed.android.mylapslogger.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import jp.ceed.android.mylapslogger.dao.PreferenceDao
import jp.ceed.android.mylapslogger.network.request.OAuthRequest
import jp.ceed.android.mylapslogger.network.response.OAuthResponse
import retrofit.Callback
import retrofit.RetrofitError
import retrofit.client.Response
import java.io.IOException
import javax.inject.Inject

class UserAccountRepository @Inject constructor (
    @ApplicationContext val context: Context
) {

    private val preferenceDao: PreferenceDao = PreferenceDao(context)


    fun getAccessToken(): String? {
        return preferenceDao.read().accessToken
    }

    fun getUserId(): String? {
        return preferenceDao.read().userId
    }

    fun getUserInfo(): OAuthResponse {
        return preferenceDao.read()
    }


    fun requestLogin(userName: String, password: String, callback: (Result<OAuthResponse>) -> Unit) {
        val request = OAuthRequest()
        request.userName = userName
        request.password = password
        request.executeRequest(context, object : Callback<OAuthResponse> {
            override fun success(oAuthResponse: OAuthResponse?, p1: Response?) {
                oAuthResponse?.let {
                    preferenceDao.save(oAuthResponse)
                    callback(Result.success(oAuthResponse))
                }
            }
            override fun failure(error: RetrofitError?) {
                callback(Result.failure(error ?: IOException("unKnown")))
            }
        })
    }

}