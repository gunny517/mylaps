package jp.ceed.android.mylapslogger.viewModel

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import jp.ceed.android.mylapslogger.model.ActivitiesItem
import jp.ceed.android.mylapslogger.repository.ApiRepository
import jp.ceed.android.mylapslogger.service.PracticeDataService

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
            }.onSuccess { activities ->
                this.activities.postValue(activities)
                startPracticeService(activities)
            }
            progressVisibility.value = false
        }
    }

    private fun startPracticeService(activities: ArrayList<ActivitiesItem>){
        val context: Context = getApplication()
        val intent = Intent(context, PracticeDataService::class.java)
        intent.putParcelableArrayListExtra(PracticeDataService.PARAM_ACTIVITIES, activities)
        context.startService(intent)
    }

}