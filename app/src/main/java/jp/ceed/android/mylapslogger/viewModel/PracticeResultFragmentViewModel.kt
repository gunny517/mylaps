package jp.ceed.android.mylapslogger.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import jp.ceed.android.mylapslogger.model.PracticeResult
import jp.ceed.android.mylapslogger.repository.ApiRepository

class PracticeResultFragmentViewModel(val id: Int, val application: Application) : ViewModel() {

	private val apiRepository = ApiRepository(application)

	val lapList: MutableLiveData<PracticeResult> = MutableLiveData()

	val progressVisibility: MutableLiveData<Boolean> = MutableLiveData(false)


	init {
		getPracticeResult()
	}

	fun getPracticeResult() {
		if(lapList.value?.sessionData?.isNotEmpty() == true){
			return
		}
		progressVisibility.value = true
		apiRepository.sessionRequest(id){ it2 ->
			it2.onSuccess { it3 ->
				lapList.postValue(it3)
			}.onFailure {
				// Nothing to do.
			}
			progressVisibility.value = false
		}
	}

	class Factory(val id: Int, val application: Application): ViewModelProvider.Factory{
		@Suppress("unchecked_cast")
		override fun <T : ViewModel?> create(modelClass: Class<T>): T {
			return PracticeResultFragmentViewModel(id ,application) as T
		}
	}

}