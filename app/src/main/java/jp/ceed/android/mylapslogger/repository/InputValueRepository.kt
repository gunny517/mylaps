package jp.ceed.android.mylapslogger.repository

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class InputValueRepository @Inject constructor (
    @ApplicationContext private val context: Context
) {

    companion object {
        const val PREFS_NAME = "InputValueRepository"
        const val KEY_DRIVE = "KEY_DRIVE"
        const val KEY_DRIVEN = "KEY_DRIVEN"
        const val KEY_CIRCUMFERENCE = "KEY_CIRCUMFERENCE"
    }

    fun saveValue(key: String, value: String?) {
        getSharedPreference().edit().putString(key, value).apply()
    }

    fun readValue(key: String): String? = getSharedPreference().getString(key, null)

    private fun getSharedPreference(): SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

}