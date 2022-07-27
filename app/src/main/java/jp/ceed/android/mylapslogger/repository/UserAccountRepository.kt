package jp.ceed.android.mylapslogger.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import jp.ceed.android.mylapslogger.dao.PreferenceDao
import jp.ceed.android.mylapslogger.datasource.LoginDataSource
import jp.ceed.android.mylapslogger.network.response.LoginResponse
import javax.inject.Inject

class UserAccountRepository @Inject constructor (
    @ApplicationContext val context: Context,
    private val preferenceDao: PreferenceDao,
    private val loginDataSource: LoginDataSource,
) {

    fun getAccessToken(): String? {
        return preferenceDao.read().accessToken
    }

    fun getUserId(): String? {
        return preferenceDao.read().userId
    }

    fun getUserInfo(): LoginResponse {
        return preferenceDao.read()
    }

    suspend fun requestLogin(userName: String, password: String): Boolean =
        try {
            val loginResponse = loginDataSource.requestLogin(userName, password)
            preferenceDao.save(loginResponse)
            true
        } catch (e: Exception) {
            false
        }
}