package jp.ceed.android.mylapslogger.util

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppSettings @Inject constructor (
    @ApplicationContext val context: Context
) {

    fun isShowPracticeResultsAsSeparate(): Boolean {
        return getPreferences().getBoolean(SHOW_PRACTICE_RESULTS_AS_SEPARATE, false)
    }

    fun saveShowPracticeResultsAsSeparate(value: Boolean){
        getPreferences().edit().putBoolean(SHOW_PRACTICE_RESULTS_AS_SEPARATE, value).apply()
    }

    fun isShowSpeedBar(): Boolean {
        return getPreferences().getBoolean(SHOW_SPEED_BAR, false)
    }

    fun saveSpeedBar(value: Boolean){
        getPreferences().edit().putBoolean(SHOW_SPEED_BAR, value).apply()
    }

    private fun getPreferences(): SharedPreferences{
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    companion object{
        const val PREFS_NAME = "APP_SETTINGS"
        const val SHOW_PRACTICE_RESULTS_AS_SEPARATE = "SHOW_PRACTICE_RESULTS_AS_SEPARATE"
        const val SHOW_SPEED_BAR = "SHOW_SPEED_BAR"
    }

}