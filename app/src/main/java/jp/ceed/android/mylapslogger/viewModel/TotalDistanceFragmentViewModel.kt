package jp.ceed.android.mylapslogger.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import jp.ceed.android.mylapslogger.entity.TotalDistance
import jp.ceed.android.mylapslogger.repository.PracticeTrackRepository
import kotlinx.coroutines.launch

class TotalDistanceFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val practiceTrackRepository = PracticeTrackRepository(application.applicationContext)

    val totalDistanceList: MutableLiveData<List<TotalDistance>> = MutableLiveData()

    init{
        viewModelScope.launch {
            totalDistanceList.value = practiceTrackRepository.getTotalDistanceList()
        }
    }

}