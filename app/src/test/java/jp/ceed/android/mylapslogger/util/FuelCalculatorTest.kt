package jp.ceed.android.mylapslogger.util

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class FuelCalculatorTest {

    @Test
    fun calculateConsumption() {
        val result = FuelCalculator.calculateConsumption(5000f, 50)
        assertThat(result).isEqualTo(100.0)
    }
}