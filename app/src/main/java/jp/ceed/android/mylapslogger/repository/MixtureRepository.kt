package jp.ceed.android.mylapslogger.repository

import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class MixtureRepository @Inject constructor() {

    /**
     * 混合比を計算する
     *
     * @param currentTotalFuel 今ある混合燃料の総量
     * @param currentMixtureRatio 今ある混合燃料の混合比
     * @param addedFuelNet 追加するガソリンの量
     * @param destMixtureRatio 最終的になってほしい混合比
     */
    fun calculate(
        currentTotalFuel: MutableLiveData<String>,
        currentMixtureRatio: MutableLiveData<String>,
        addedFuelNet: MutableLiveData<String>,
        destMixtureRatio: MutableLiveData<String>
        ): Float {
        val curTotal: Float = floatValue(currentTotalFuel) ?: return 0F
        val curRatio: Float = floatValue(currentMixtureRatio) ?: return 0F
        val addFuel: Float = floatValue(addedFuelNet) ?: 0f
        val destMixture: Float = floatValue(destMixtureRatio) ?: return 0F
        val divider: Float = 1 + (1 / curRatio) // 今ある混合燃料の中のガソリンの量を求めるための分母
        val actualFuel: Float = curTotal / divider // 今ある混合燃料の中のガソリンの量
        val actualOil: Float = curTotal - actualFuel // 今ある混合燃料の中のオイルの量
        val neededTotalOil: Float = (actualFuel + addFuel) / destMixture
        return neededTotalOil - actualOil
    }

    private fun floatValue(target: MutableLiveData<String>): Float? {
        return if(target.value.isNullOrEmpty()) {
            null
        } else {
            target.value?.toFloat()
        }
    }
}