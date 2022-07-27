package jp.ceed.android.mylapslogger.viewModel

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ceed.android.mylapslogger.model.LoginResult
import jp.ceed.android.mylapslogger.repository.UserAccountRepository
import jp.ceed.android.mylapslogger.util.ExceptionUtil
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModel @Inject constructor(
    var userAccountRepository: UserAccountRepository,
    var exceptionUtil: ExceptionUtil,
) : ViewModel() {

    var userName: MutableLiveData<String> = MutableLiveData()

    var password: MutableLiveData<String> = MutableLiveData()

    var loginResult: MutableLiveData<LoginResult> = MutableLiveData()

    var loginButtonEnabled: MutableLiveData<Boolean> = MutableLiveData()

    fun callLogin() {
        viewModelScope.launch {
            loginResult.value = if (
                userAccountRepository.requestLogin(
                    userName.value.toString(),
                    password.value.toString(),
                )
            ) {
                LoginResult.Success
            } else {
                LoginResult.Failed
            }
        }
    }

    fun updateLoginButtonEnabled() {
        loginButtonEnabled.value =
            !TextUtils.isEmpty(userName.value) && !TextUtils.isEmpty(password.value)
    }
}