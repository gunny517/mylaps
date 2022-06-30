package jp.ceed.android.mylapslogger.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ceed.android.mylapslogger.network.response.OAuthResponse
import jp.ceed.android.mylapslogger.repository.UserAccountRepository
import javax.inject.Inject

@HiltViewModel
class UserInfoFragmentViewModel @Inject constructor (
    userAccountRepository: UserAccountRepository,
) : ViewModel() {

    val userInfo: MutableLiveData<OAuthResponse> = MutableLiveData()

    init {
        userInfo.value = userAccountRepository.getUserInfo()
    }
}