package jp.ceed.android.mylapslogger.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ceed.android.mylapslogger.entity.Event
import jp.ceed.android.mylapslogger.repository.FinalRatioRepository
import javax.inject.Inject

@HiltViewModel
class FinalRatioViewModel @Inject constructor(
    private var finalRatioRepository: FinalRatioRepository
) : ViewModel() {

    enum class EventState { CALCULATE }

    val finalRatioList: MutableLiveData<List<String>> = MutableLiveData()

    var driveMin: MutableLiveData<String> = MutableLiveData()

    var driveMax: MutableLiveData<String> = MutableLiveData()

    var drivenMin: MutableLiveData<String> = MutableLiveData()

    var drivenMax: MutableLiveData<String> = MutableLiveData()

    var colSize: MutableLiveData<Int> = MutableLiveData()

    var event: MutableLiveData<Event<EventState>> = MutableLiveData()


    init {
        loadInitialValues()
        loadFinalRatio()
    }

    private fun loadInitialValues(){
        val dto = finalRatioRepository.getSavedValue()
        driveMin.value = dto.driveMin.toString()
        driveMax.value = dto.driveMax.toString()
        drivenMin.value = dto.drivenMin.toString()
        drivenMax.value = dto.drivenMax.toString()
    }

    fun loadFinalRatio(){
        val result = finalRatioRepository.getFinalRatioData(
            driveMin.value,
            driveMax.value,
            drivenMin.value,
            drivenMax.value
        )
        colSize.value = result.second
        finalRatioList.value = result.first
        event.value = Event(EventState.CALCULATE)
    }
}
