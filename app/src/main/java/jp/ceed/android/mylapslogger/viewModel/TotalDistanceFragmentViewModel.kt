package jp.ceed.android.mylapslogger.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ceed.android.mylapslogger.entity.TotalDistance
import jp.ceed.android.mylapslogger.repository.PracticeTrackRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TotalDistanceFragmentViewModel @Inject constructor(
    var practiceTrackRepository: PracticeTrackRepository,
): ViewModel() {

    val totalDistanceList: MutableLiveData<List<TotalDistance>> = MutableLiveData()

    init{
        viewModelScope.launch {
            totalDistanceList.value = practiceTrackRepository.getTotalDistanceList()
        }
    }

}