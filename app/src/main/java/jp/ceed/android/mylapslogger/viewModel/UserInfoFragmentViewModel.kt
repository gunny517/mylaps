package jp.ceed.android.mylapslogger.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import jp.ceed.android.mylapslogger.network.response.OAuthResponse
import jp.ceed.android.mylapslogger.repository.UserAccountRepository

class UserInfoFragmentViewModel(application: Application) : AndroidViewModel(application) {

    val userInfo: MutableLiveData<OAuthResponse> = MutableLiveData()

    private val userAccountRepository = UserAccountRepository(getApplication())

    init {
        userInfo.value = userAccountRepository.getUserInfo()
    }
}