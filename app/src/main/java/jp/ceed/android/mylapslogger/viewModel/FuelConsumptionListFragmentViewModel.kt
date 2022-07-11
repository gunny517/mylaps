package jp.ceed.android.mylapslogger.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ceed.android.mylapslogger.model.FuelConsumptionListItem
import jp.ceed.android.mylapslogger.repository.ActivityInfoTrackRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FuelConsumptionListFragmentViewModel @Inject constructor(
    private val activityInfoTrackRepository: ActivityInfoTrackRepository
) : ViewModel() {

    var listItem: MutableLiveData<List<FuelConsumptionListItem>> = MutableLiveData()

    init{
        loadFuelConsumptionList()
    }

    private fun loadFuelConsumptionList() {
        viewModelScope.launch {
            listItem.value = activityInfoTrackRepository.getFuelConsumptionList()
        }
    }
}