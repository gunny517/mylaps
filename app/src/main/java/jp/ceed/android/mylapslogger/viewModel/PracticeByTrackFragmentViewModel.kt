package jp.ceed.android.mylapslogger.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ceed.android.mylapslogger.entity.PracticeTrack
import jp.ceed.android.mylapslogger.model.PracticeByTrack
import jp.ceed.android.mylapslogger.repository.ActivityInfoRepository
import jp.ceed.android.mylapslogger.repository.PracticeTrackRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PracticeByTrackFragmentViewModel @Inject constructor (
    savedStateHandle: SavedStateHandle,
    val activityInfoRepository: ActivityInfoRepository,
    val practiceTrackRepository: PracticeTrackRepository,
) : ViewModel() {

    private val trackId: Int = savedStateHandle.get<Int>("trackId") ?: throw IllegalStateException("Should have trackId")

    val practiceTrackList: MutableLiveData<List<PracticeByTrack>> = MutableLiveData()

    init {
        loadValues(false)
    }

    fun loadValues(sortByBestLap: Boolean) {
        viewModelScope.launch {
            practiceTrackList.value = createItems(
                practiceTrackRepository.getPracticeListByTrack(trackId, sortByBestLap)
            )
        }
    }

    private suspend fun createItems(entities: List<PracticeTrack>): List<PracticeByTrack> {
        return entities.map { entity ->
            PracticeByTrack(
                activityId = entity.activityId,
                displayTime = entity.displayTime,
                totalLap = entity.lapCount,
                bestTime = entity.bestLap,
                trackId = entity.trackId,
                trackName = entity.trackName,
                trackLength = entity.trackLength,
                eventName = activityInfoRepository.findById(entity.activityId)?.eventName
            )
        }
    }
}