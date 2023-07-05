package jp.ceed.android.mylapslogger.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MixtureCalculatorFragmentViewModel @Inject constructor() : ViewModel() {

    var currentTotalFuel: MutableLiveData<String> = MutableLiveData()

    var currentMixtureRatio: MutableLiveData<String> = MutableLiveData()

    var addedFuelNet: MutableLiveData<String> = MutableLiveData("0")

    var destMixtureRatio: MutableLiveData<String> = MutableLiveData()

    var addedOilNet: MutableLiveData<String> = MutableLiveData()

    init {
        setObservers()
    }

    private fun setObservers() {
        currentTotalFuel.observeForever {
            calculate()
        }
        currentMixtureRatio.observeForever {
            calculate()
        }
        addedFuelNet.observeForever {
            calculate()
        }
        destMixtureRatio.observeForever {
            calculate()
        }
    }

    private fun calculate() {
        val curTotal: Float = floatValue(currentTotalFuel) ?: return
        val curRatio: Float = floatValue(currentMixtureRatio) ?: return
        val addFuel: Float = floatValue(addedFuelNet) ?: 0f
        val destMixtureRatio: Float = floatValue(destMixtureRatio) ?: return
        val actualFuel: Float = curTotal / (1 + (1 / curRatio))
        val actualOil: Float = curTotal - actualFuel
        val neededTotalOil: Float = (curTotal + addFuel) / destMixtureRatio
        addedOilNet.value = (neededTotalOil - actualOil).toString()
    }

    private fun floatValue(target: MutableLiveData<String>): Float? {
        return if(target.value.isNullOrEmpty()) {
            null
        } else {
            target.value?.toFloat()
        }
    }

}