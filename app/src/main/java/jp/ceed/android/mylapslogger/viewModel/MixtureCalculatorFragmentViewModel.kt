package jp.ceed.android.mylapslogger.viewModel

import androidx.lifecycle.MutableLiveData
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
        val result: Float = mixtureRepository.calculate(
            currentTotalFuel,
            currentMixtureRatio,
            addedFuelNet,
            destMixtureRatio
        )
        addedOilNet.value = String.format(Locale.JAPAN, "%.1f", result)
    }
}