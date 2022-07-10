package jp.ceed.android.mylapslogger.util

import kotlin.math.roundToInt

class FuelCalculator {

    companion object {

        fun calculateConsumption(spendFuel: Float, lapCount: Int): Double {
            val actual = spendFuel / lapCount.toFloat()
            return (actual * 100.0).roundToInt() / 100.0
        }
    }
}