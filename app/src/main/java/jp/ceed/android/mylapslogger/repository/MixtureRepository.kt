package jp.ceed.android.mylapslogger.repository

import javax.inject.Inject

class MixtureRepository @Inject constructor() {

    /**
     * 混合比を計算する
     *
     * @param currentTotalFuel 今ある混合燃料の総量
     * @param currentMixtureRatio 今ある混合燃料の混合比
     * @param addedFuelNet 追加するガソリンの量
     * @param destMixtureRatio 最終的になってほしい混合比
     * @return 追加すべきオイルの量
     */
    fun calculate(currentTotalFuel: Float, currentMixtureRatio: Float, addedFuelNet: Float, destMixtureRatio: Float): Float {
        val divider: Float = 1 + (1 / currentMixtureRatio) // 今ある混合燃料の中のガソリンの量を求めるための分母
        val actualFuel: Float = currentTotalFuel / divider // 今ある混合燃料の中のガソリンの量
        val actualOil: Float = currentTotalFuel - actualFuel // 今ある混合燃料の中のオイルの量
        val neededTotalOil: Float = (actualFuel + addedFuelNet) / destMixtureRatio // 最終的に必要になるオイルの量
        return neededTotalOil - actualOil
    }

}