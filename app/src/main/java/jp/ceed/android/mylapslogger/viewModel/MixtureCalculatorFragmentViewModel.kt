package jp.ceed.android.mylapslogger.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ceed.android.mylapslogger.repository.MixtureRepository
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MixtureCalculatorFragmentViewModel @Inject constructor(
    var mixtureRepository: MixtureRepository
) : ViewModel() {

    var currentTotalFuel: MutableLiveData<String> = MutableLiveData()

    var currentMixtureRatio: MutableLiveData<String> = MutableLiveData()

    var addedFuelNet: MutableLiveData<String> = MutableLiveData()

    var destMixtureRatio: MutableLiveData<String> = MutableLiveData()

    var addedOilNet: MutableLiveData<String> = MutableLiveData()

    val inputValueObserver = object : Observer<String> {
        override fun onChanged(value: String) {
            calculate()
        }
    }

    init {
        setObservers()
    }

    override fun onCleared() {
        super.onCleared()
        currentTotalFuel.removeObserver(inputValueObserver)
        currentMixtureRatio.removeObserver(inputValueObserver)
        addedFuelNet.removeObserver(inputValueObserver)
        destMixtureRatio.removeObserver(inputValueObserver)
    }

    private fun setObservers() {
        currentTotalFuel.observeForever(inputValueObserver)
        currentMixtureRatio.observeForever(inputValueObserver)
        addedFuelNet.observeForever(inputValueObserver)
        destMixtureRatio.observeForever(inputValueObserver)
    }

    private fun calculate() {
        val result: Float = mixtureRepository.calculate(
            currentTotalFuel = floatOrZero(currentTotalFuel),
            currentMixtureRatio = floatOrZero(currentMixtureRatio),
            addedFuelNet = floatOrZero(addedFuelNet),
            destMixtureRatio =floatOrZero(destMixtureRatio)
        )
        addedOilNet.value = String.format(Locale.JAPAN, "%.1f", result)
    }

    private fun floatOrZero(target: MutableLiveData<String>): Float {
        return if (target.value.isNullOrEmpty()) {
            0F
        } else {
            target.value!!.toFloat()
        }
    }
}