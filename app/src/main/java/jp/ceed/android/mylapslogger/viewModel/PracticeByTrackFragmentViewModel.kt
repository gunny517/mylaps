package jp.ceed.android.mylapslogger.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ceed.android.mylapslogger.entity.PracticeTrack
import jp.ceed.android.mylapslogger.repository.PracticeTrackRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PracticeByTrackFragmentViewModel @Inject constructor (
    savedStateHandle: SavedStateHandle,
    val practiceTrackRepository: PracticeTrackRepository,
) : ViewModel() {

    private val trackId: Int = savedStateHandle.get<Int>("trackId") ?: throw IllegalStateException("Should have trackId")

    val practiceTrackList: MutableLiveData<List<PracticeTrack>> = MutableLiveData()

    init {
        loadValues(true)
    }

    fun loadValues(sortByBestLap: Boolean) {
        viewModelScope.launch {
            practiceTrackList.value = practiceTrackRepository.getPracticeListByTrack(trackId, sortByBestLap)
        }
    }
}