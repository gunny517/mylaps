package jp.ceed.android.mylapslogger.dao

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import jp.ceed.android.mylapslogger.network.response.LoginResponse
import javax.inject.Inject


class PreferenceDao @Inject constructor(
    @ApplicationContext val context: Context
) {

    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    enum class PrefsKey {
        ACCESS_TOKEN, TOKEN_TYPE, EXPIRES_IN, ISSUED, USER_ID, ACCESS_TOKEN_EXPIRE
    }

    fun save(loginResponse: LoginResponse) {
        sharedPreferences.edit()
            .putString(PrefsKey.ACCESS_TOKEN.name, loginResponse.accessToken)
            .putString(PrefsKey.TOKEN_TYPE.name, loginResponse.tokenType)
            .putInt(PrefsKey.EXPIRES_IN.name, loginResponse.expiresIn)
            .putString(PrefsKey.ISSUED.name, loginResponse.issued)
            .putString(PrefsKey.USER_ID.name, loginResponse.userId)
            .putString(PrefsKey.ACCESS_TOKEN_EXPIRE.name, loginResponse.expires)
            .apply()
    }

    fun read(): LoginResponse {
        return LoginResponse(
            accessToken = sharedPreferences.getString(PrefsKey.ACCESS_TOKEN.name, null),
            tokenType = sharedPreferences.getString(PrefsKey.TOKEN_TYPE.name, null),
            expiresIn = sharedPreferences.getInt(PrefsKey.EXPIRES_IN.name, 0),
            issued = sharedPreferences.getString(PrefsKey.ISSUED.name, null),
            userId = sharedPreferences.getString(PrefsKey.USER_ID.name, null),
            expires = sharedPreferences.getString(PrefsKey.ACCESS_TOKEN_EXPIRE.name, null),
        )
    }

}