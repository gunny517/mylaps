package jp.ceed.android.mylapslogger.viewModel

import android.app.Application
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import jp.ceed.android.mylapslogger.model.LoginResult
import jp.ceed.android.mylapslogger.repository.UserAccountRepository
import jp.ceed.android.mylapslogger.util.ExceptionUtil
import javax.inject.Inject

class LoginFragmentViewModel(application: Application) : AndroidViewModel(application) {

    @Inject lateinit var userAccountRepository: UserAccountRepository

    @Inject lateinit var exceptionUtil: ExceptionUtil

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
            }.onFailure { t ->
                exceptionUtil.save(t, viewModelScope)
                loginResult.postValue(LoginResult.Failed)
            }
        }
    }

    fun updateLoginButtonEnabled() {
        loginButtonEnabled.value = !TextUtils.isEmpty(userName.value) && !TextUtils.isEmpty(password.value)
    }

}