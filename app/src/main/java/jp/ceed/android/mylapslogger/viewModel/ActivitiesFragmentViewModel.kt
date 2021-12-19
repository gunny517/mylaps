package jp.ceed.android.mylapslogger.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import jp.ceed.android.mylapslogger.model.ActivitiesItem
import jp.ceed.android.mylapslogger.repository.ApiRepository

class ActivitiesFragmentViewModel(application: Application) : AndroidViewModel(application) {

	private val apiRepository = ApiRepository(getApplication())

	val activities: MutableLiveData<List<ActivitiesItem>> = MutableLiveData()

	fun callActivitiesRequest(){
		apiRepository.getActivities(object : ApiRepository.GetActivitiesCallback{
			override fun onFinish(result: Result<List<ActivitiesItem>>) {
				result.onFailure {
					// TODO
				}.onSuccess {
					activities.postValue(it)
				}
			}
		})
	}

}