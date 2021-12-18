package jp.ceed.android.mylapslogger.viewModel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import jp.ceed.android.mylapslogger.model.LoginResult
import jp.ceed.android.mylapslogger.network.response.OAuthResponse
import jp.ceed.android.mylapslogger.repository.UserAccountRepository

class LoginFragmentViewModel(application: Application) : AndroidViewModel(application) {

	var userAccountRepository: UserAccountRepository = UserAccountRepository(getApplication())

	var userName: MutableLiveData<String> = MutableLiveData()

	var password: MutableLiveData<String> = MutableLiveData()

	var loginButtonEnabled: MutableLiveData<Boolean> = MutableLiveData()

	var loginResult: MutableLiveData<LoginResult> = MutableLiveData()


	fun callLogin(view: View){
		userAccountRepository.requestLogin(
			userName.value.toString(),
			password.value.toString(),
			object : UserAccountRepository.LoginCallback{
				override fun onFinish(result: Result<OAuthResponse>) {
					result.onSuccess {
						loginResult.postValue(LoginResult.Success)
					}.onFailure {
						loginResult.postValue(LoginResult.Failed)
					}
				}
			})
	}

}