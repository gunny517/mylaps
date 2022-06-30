package jp.ceed.android.mylapslogger.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.ceed.android.mylapslogger.entity.PracticeTrack
import jp.ceed.android.mylapslogger.repository.PracticeTrackRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class TrackBestFragmentViewModel() : ViewModel() {

    @Inject lateinit var practiceTrackRepository: PracticeTrackRepository

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