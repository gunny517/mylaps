package jp.ceed.android.mylapslogger.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import jp.ceed.android.mylapslogger.model.ActivitiesItem
import jp.ceed.android.mylapslogger.repository.ApiRepository

class ActivitiesFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val apiRepository = ApiRepository(getApplication())

    val activities: MutableLiveData<List<ActivitiesItem>> = MutableLiveData()

    val progressVisibility: MutableLiveData<Boolean> = MutableLiveData(false)


    fun callActivitiesRequest() {
        if (activities.value?.isNotEmpty() == true) {
            return
        }
        progressVisibility.value = true
        apiRepository.getActivities {
            it.onFailure {
                // TODO
            }.onSuccess { it1 ->
                activities.postValue(it1)
            }
            progressVisibility.value = false
        }
    }

}