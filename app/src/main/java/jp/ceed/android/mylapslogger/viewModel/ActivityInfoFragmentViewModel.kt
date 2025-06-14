package jp.ceed.android.mylapslogger.viewModel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ceed.android.mylapslogger.entity.ActivityInfo
import jp.ceed.android.mylapslogger.entity.Event
import jp.ceed.android.mylapslogger.entity.EventState
import jp.ceed.android.mylapslogger.repository.ActivityInfoRepository
import jp.ceed.android.mylapslogger.util.FuelCalculator
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityInfoFragmentViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    var activityInfoRepository: ActivityInfoRepository
) : ViewModel() {

    var description: MutableLiveData<String?> = MutableLiveData()

    var eventName: MutableLiveData<String?> = MutableLiveData()

    var fuelConsumption: MutableLiveData<String?> = MutableLiveData()

    var activityId: Long = savedStateHandle.get<Long>("activityId") ?: throw IllegalStateException("Should have activityId")

    var bestLap: MutableLiveData<String> = MutableLiveData(savedStateHandle.get("bestLap"))

    var totalLap: MutableLiveData<String> = MutableLiveData(savedStateHandle.get("totalLap"))

    var totalTime: MutableLiveData<String> = MutableLiveData(savedStateHandle.get("totalTime"))

    var totalDistance: MutableLiveData<String> = MutableLiveData(savedStateHandle.get("totalDistance"))

    val trackId: Int = savedStateHandle.get<Int>("trackId") ?: throw IllegalStateException("Should have trackId")

    val dateTime: String = savedStateHandle.get<String>("dateTime") ?: throw IllegalStateException("Should have dateTime")

    var spendFuel: MutableLiveData<String> = MutableLiveData()

    var onSaved: MutableLiveData<Event<EventState>> = MutableLiveData()

    @VisibleForTesting
    var isUpdate = false

    init {
    	loadActivityInfo()
    }

    @VisibleForTesting
    fun loadActivityInfo() {
        viewModelScope.launch {
            onLoadActivityInfo(activityInfoRepository.findById(activityId))
        }
    }

    private fun onLoadActivityInfo(activityInfo: ActivityInfo?){
        activityInfo?.let {
            description.value = activityInfo.description
            eventName.value = activityInfo.eventName
            fuelConsumption.value = activityInfo.fuelConsumption?.toString()
            isUpdate = true
        }
    }

    fun saveSessionInfo() {
        val dto = ActivityInfo(
            activityId = activityId,
            description = description.value ?: "",
            fuelConsumption = fuelConsumption.value?.toFloat(),
            trackId = trackId,
            dateTime = dateTime,
            eventName = eventName.value
        )
        viewModelScope.launch {
            if(isUpdate){
                activityInfoRepository.update(dto)
            }else{
                activityInfoRepository.insert(dto)
            }
            onSaved.value = Event(EventState.SAVED)
        }
    }

    fun calculateFuelConsumption(){
        val spend = spendFuel.value?.toFloat() ?: 0f
        totalLap.value?.let {
            fuelConsumption.value = FuelCalculator.calculateConsumption(spend, it.toInt()).toString()
        }
    }
}