package jp.ceed.android.mylapslogger.viewModel

import android.app.Application
import android.content.pm.PackageInfo
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class AppInfoFragmentViewModel(application: Application): AndroidViewModel(application) {

    var appVersionName: MutableLiveData<String> = MutableLiveData()


    init {
        setAppInfo()
    }

    private fun setAppInfo(){
        val appName = getApplication<Application>().packageName
        val appInfo: PackageInfo? = getApplication<Application>().packageManager.getPackageInfo(appName, 0)
        appVersionName.value = appInfo?.versionName
    }

}