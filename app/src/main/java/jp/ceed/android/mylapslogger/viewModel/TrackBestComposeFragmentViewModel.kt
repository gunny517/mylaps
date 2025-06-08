package jp.ceed.android.mylapslogger.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ceed.android.mylapslogger.entity.Event
import jp.ceed.android.mylapslogger.entity.PracticeTrack
import jp.ceed.android.mylapslogger.repository.PracticeTrackRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackBestComposeFragmentViewModel @Inject constructor (
    var practiceTrackRepository: PracticeTrackRepository,
) : ViewModel() {

    val trackBestList: MutableState<List<PracticeTrack>> = mutableStateOf(mutableListOf())

    var event: MutableLiveData<Event<PracticeTrack>> = MutableLiveData()

    var isRefreshing: MutableState<Boolean> = mutableStateOf(false)

    init{
        loadTrackBest()
    }

    fun onClickItem(item: PracticeTrack) {
        event.value = Event(item)
    }

    fun loadTrackBest(){
        viewModelScope.launch {
            isRefreshing.value = true
            trackBestList.value = practiceTrackRepository.findBestLapList()
            isRefreshing.value = false
        }
    }
}