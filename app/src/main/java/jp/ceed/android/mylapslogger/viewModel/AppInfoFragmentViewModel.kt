package jp.ceed.android.mylapslogger.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ceed.android.mylapslogger.entity.Event
import jp.ceed.android.mylapslogger.entity.EventState
import jp.ceed.android.mylapslogger.repository.AppInfoRepository
import jp.ceed.android.mylapslogger.util.AppSettings
import javax.inject.Inject

@HiltViewModel
class AppInfoFragmentViewModel @Inject constructor (
    var appSettings: AppSettings,
    var appInfoRepository: AppInfoRepository,
): ViewModel() {

    var appVersionName: MutableLiveData<String> = MutableLiveData()

    var showPracticeResultAsSeparate: MutableLiveData<Boolean> = MutableLiveData(false)

    var showSpeedBar: MutableLiveData<Boolean> = MutableLiveData(false)

    val clickShowErrorLogEvent: MutableLiveData<Event<EventState>> = MutableLiveData()

    init {
        setAppInfo()
        initAppSettings()
    }

    private fun setAppInfo(){
        appVersionName.value = appInfoRepository.getVersionName()
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

    fun onClickShowErrorLog(){
        clickShowErrorLogEvent.value = Event(EventState.CLICKED)
    }

}