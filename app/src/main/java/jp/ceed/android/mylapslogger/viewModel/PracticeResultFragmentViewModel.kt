package jp.ceed.android.mylapslogger.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import jp.ceed.android.mylapslogger.model.PracticeResult
import jp.ceed.android.mylapslogger.repository.ApiRepository

class PracticeResultFragmentViewModel(application: Application) : AndroidViewModel(application) {

	private val apiRepository = ApiRepository(getApplication())

	val lapList: MutableLiveData<PracticeResult> = MutableLiveData()

	val progressVisibility: MutableLiveData<Boolean> = MutableLiveData(false)

	val sessionId: MutableLiveData<Int> = MutableLiveData()


	fun getPracticeResult() {
		sessionId.value?.let {
			progressVisibility.value = true
			apiRepository.sessionRequest(it, object : ApiRepository.GetPracticeResultCallback{
				override fun onFinish(result: Result<PracticeResult>) {
					result.onSuccess {
						lapList.postValue(it)
					}.onFailure {
						// Nothing to do.
					}
					progressVisibility.value = false
				}
			})
		}
	}

}