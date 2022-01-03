package jp.ceed.android.mylapslogger.viewModel

import android.app.Application
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import jp.ceed.android.mylapslogger.model.LoginResult
import jp.ceed.android.mylapslogger.repository.UserAccountRepository

class LoginFragmentViewModel(application: Application) : AndroidViewModel(application) {

    var userAccountRepository: UserAccountRepository = UserAccountRepository(getApplication())

    var userName: MutableLiveData<String> = MutableLiveData()

    var password: MutableLiveData<String> = MutableLiveData()

    var loginResult: MutableLiveData<LoginResult> = MutableLiveData()

    var loginButtonEnabled: MutableLiveData<Boolean> = MutableLiveData()


    fun callLogin() {
        userAccountRepository.requestLogin(
            userName.value.toString(),
            password.value.toString()
        ) {
            it.onSuccess {
                loginResult.postValue(LoginResult.Success)
            }.onFailure {
                loginResult.postValue(LoginResult.Failed)
            }
        }
    }

    fun updateLoginButtonEnabled() {
        loginButtonEnabled.value = !TextUtils.isEmpty(userName.value) && !TextUtils.isEmpty(password.value)
    }

}