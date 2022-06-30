package jp.ceed.android.mylapslogger.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ceed.android.mylapslogger.entity.TotalDistance
import jp.ceed.android.mylapslogger.repository.PracticeTrackRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class TotalDistanceFragmentViewModel(): ViewModel() {

    @Inject lateinit var practiceTrackRepository: PracticeTrackRepository

    val totalDistanceList: MutableLiveData<List<TotalDistance>> = MutableLiveData()

    init{
        viewModelScope.launch {
            totalDistanceList.value = practiceTrackRepository.getTotalDistanceList()
        }
    }

}