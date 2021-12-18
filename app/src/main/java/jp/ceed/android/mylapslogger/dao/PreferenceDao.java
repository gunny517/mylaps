package jp.ceed.android.mylapslogger.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by ARAKI on 2017/04/28.
 *
 */

public class PreferenceDao {

	private SharedPreferences sharedPreferences;

	public enum PrefsKey{
		ACCESS_TOKEN,
		USER_ID,
		ACCESS_TOKEN_EXPIRE;
	}

	public void save(PrefsKey key, String value){
		sharedPreferences.edit().putString(key.name(), value).apply();
	}


	public String read(PrefsKey key){
		return sharedPreferences.getString(key.name(), null);
	}


	public PreferenceDao(Context context){
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
	}

}
