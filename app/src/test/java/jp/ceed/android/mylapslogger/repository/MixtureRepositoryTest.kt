package jp.ceed.android.mylapslogger.repository

import androidx.lifecycle.MutableLiveData
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class MixtureRepositoryTest {

    private val repository = MixtureRepository()

    @Test
    fun calculate() {
        assertThat(calculate(3120F, 25F, 3000F, 30F)).isEqualTo(80F)
        assertThat(calculate(1040F, 25F, 2500F, 35F)).isEqualTo(60.00006F)
        assertThat(calculate(3100F, 30F, 1000F, 40F)).isEqualTo(0F)
        assertThat(calculate(2600F, 25F, 2000F, 30F)).isEqualTo(50.0F)
    }

    private fun calculate(curTotal: Float, curRatio: Float, addFuel: Float, destMixture: Float): Float {
        return repository.calculate(
            currentTotalFuel = MutableLiveData(curTotal.toString()),
            currentMixtureRatio = MutableLiveData(curRatio.toString()),
            addedFuelNet = MutableLiveData(addFuel.toString()),
            destMixtureRatio = MutableLiveData(destMixture.toString())
        )
    }

}