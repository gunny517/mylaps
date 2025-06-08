package jp.ceed.android.mylapslogger.repository

import android.app.Application
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppInfoRepository @Inject constructor (@ApplicationContext val context: Context) {

    val application: Application = context.applicationContext as Application

    fun getVersionName(): String = application.packageManager.getPackageInfo(
        application.packageName, 0
    ).versionName ?: ""

}