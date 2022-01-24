package jp.ceed.android.mylapslogger.util

import android.content.Context
import android.content.SharedPreferences

class AppSettings(val context: Context) {

    fun isShowPracticeResultsAsSeparate(): Boolean {
        return getPreferences().getBoolean(SHOW_PRACTICE_RESULTS_AS_SEPARATE, false)
    }

    fun saveShowPracticeResultsAsSeparate(value: Boolean){
        getPreferences().edit().putBoolean(SHOW_PRACTICE_RESULTS_AS_SEPARATE, value).apply()
    }

    private fun getPreferences(): SharedPreferences{
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    companion object{
        const val PREFS_NAME = "APP_SETTINGS"
        const val SHOW_PRACTICE_RESULTS_AS_SEPARATE = "SHOW_PRACTICE_RESULTS_AS_SEPARATE"
    }

}