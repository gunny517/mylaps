package jp.ceed.android.mylapslogger.viewModel

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ceed.android.mylapslogger.model.ActivitiesItem
import jp.ceed.android.mylapslogger.repository.ApiRepository
import jp.ceed.android.mylapslogger.service.PracticeDataService
import jp.ceed.android.mylapslogger.util.ExceptionUtil
import javax.inject.Inject

class ActivitiesFragmentViewModel(application: Application) : AndroidViewModel(application) {

    @Inject lateinit var apiRepository: ApiRepository

    @Inject lateinit var exceptionUtil: ExceptionUtil

    val activities: MutableLiveData<List<ActivitiesItem>> = MutableLiveData()

    val progressVisibility: MutableLiveData<Boolean> = MutableLiveData(false)


    fun callActivitiesRequest() {
        if (activities.value?.isNotEmpty() == true) {
            return
        }
        progressVisibility.value = true
        apiRepository.getActivities {
            it.onFailure { t ->
                exceptionUtil.save(t, viewModelScope)
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