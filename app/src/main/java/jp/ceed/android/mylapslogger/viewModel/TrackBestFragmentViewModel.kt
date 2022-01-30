package jp.ceed.android.mylapslogger.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import jp.ceed.android.mylapslogger.entity.PracticeTrack
import jp.ceed.android.mylapslogger.repository.PracticeTrackRepository
import jp.ceed.android.mylapslogger.util.DateUtil
import kotlinx.coroutines.launch

class TrackBestFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val practiceTrackRepository = PracticeTrackRepository(application.applicationContext)

    val trackBestList: MutableLiveData<List<PracticeTrack>> = MutableLiveData()

    init{
        loadTrackBest()
    }

    private fun loadTrackBest(){
        viewModelScope.launch {
            trackBestList.value = practiceTrackRepository.findBestLapList()
        }
    }
}