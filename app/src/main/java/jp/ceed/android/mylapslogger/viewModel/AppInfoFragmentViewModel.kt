package jp.ceed.android.mylapslogger.viewModel

import android.app.Application
import android.content.pm.PackageInfo
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import jp.ceed.android.mylapslogger.util.AppSettings

class AppInfoFragmentViewModel(application: Application): AndroidViewModel(application) {

    private val appSettings = AppSettings(application)

    var appVersionName: MutableLiveData<String> = MutableLiveData()

    var showPracticeResultAsSeparate: MutableLiveData<Boolean> = MutableLiveData(false)

    var showSpeedBar: MutableLiveData<Boolean> = MutableLiveData(false)


    init {
        setAppInfo()
        initAppSettings()
    }

    private fun setAppInfo(){
        val appName = getApplication<Application>().packageName
        val appInfo: PackageInfo? = getApplication<Application>().packageManager.getPackageInfo(appName, 0)
        appVersionName.value = appInfo?.versionName
    }

    private fun initAppSettings(){
        showPracticeResultAsSeparate.value = appSettings.isShowPracticeResultsAsSeparate()
        showSpeedBar.value = appSettings.isShowSpeedBar()
    }

    fun saveShowPracticeResultsAsSeparate(){
        appSettings.saveShowPracticeResultsAsSeparate(showPracticeResultAsSeparate.value ?: false)
    }

    fun saveShowSpeedBar(){
        appSettings.saveSpeedBar(showSpeedBar.value ?: false)
    }

}