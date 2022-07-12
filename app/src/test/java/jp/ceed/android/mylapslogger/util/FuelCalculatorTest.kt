package jp.ceed.android.mylapslogger.util

import com.google.common.truth.Truth.assertThat
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@RunWith(JUnitPlatform::class)
object FuelCalculatorTest : Spek ({

    describe("#calculateConsumption"){
        context("燃料使用量・周回数が正の値であるとき"){
            it("正しい結果が返却される"){
                val result = FuelCalculator.calculateConsumption(5000f, 50)
                assertThat(result).isEqualTo(100.0)
            }
        }
    }
})
