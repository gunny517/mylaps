package jp.ceed.android.mylapslogger.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import jp.ceed.android.mylapslogger.network.response.OAuthResponse;

/**
 * Created by ARAKI on 2017/04/28.
 */

public class PreferenceDao {

    private SharedPreferences sharedPreferences;

    public enum PrefsKey {
        ACCESS_TOKEN,
        TOKEN_TYPE,
        EXPIRES_IN,
        ISSUED,
        USER_ID,
        ACCESS_TOKEN_EXPIRE;
    }

    public void save(final OAuthResponse oAuthResponse) {
        sharedPreferences.edit()
                .putString(PrefsKey.ACCESS_TOKEN.name(), oAuthResponse.accessToken)
                .putString(PrefsKey.TOKEN_TYPE.name(), oAuthResponse.tokenType)
                .putInt(PrefsKey.EXPIRES_IN.name(), oAuthResponse.expiresIn)
                .putString(PrefsKey.ISSUED.name(), oAuthResponse.issued)
                .putString(PrefsKey.USER_ID.name(), oAuthResponse.userId)
                .putString(PrefsKey.ACCESS_TOKEN_EXPIRE.name(), oAuthResponse.expires)
                .apply();
    }

    @NonNull
    public OAuthResponse read() {
        OAuthResponse oAuthResponse = new OAuthResponse();
        oAuthResponse.accessToken = sharedPreferences.getString(PrefsKey.ACCESS_TOKEN.name(), null);
        oAuthResponse.tokenType = sharedPreferences.getString(PrefsKey.TOKEN_TYPE.name(), null);
        oAuthResponse.expiresIn = sharedPreferences.getInt(PrefsKey.EXPIRES_IN.name(), 0);
        oAuthResponse.issued = sharedPreferences.getString(PrefsKey.ISSUED.name(), null);
        oAuthResponse.userId = sharedPreferences.getString(PrefsKey.USER_ID.name(), null);
        oAuthResponse.expires = sharedPreferences.getString(PrefsKey.ACCESS_TOKEN_EXPIRE.name(), null);
        return oAuthResponse;
    }

    public PreferenceDao(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

}
