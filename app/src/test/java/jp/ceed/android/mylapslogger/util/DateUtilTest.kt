package jp.ceed.android.mylapslogger.util

import com.google.common.truth.Truth.assertThat
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@RunWith(JUnitPlatform::class)
object DateUtilTest : Spek({

    describe("#toTimeFromDateTimeWithMilliSec"){
        it("正しい形式の日付が渡されたときに正しい値が返却されること"){
            val result = DateUtil.toTimeFromDateTimeWithMilliSec("2022-06-26T09:15:54.982+09:00")
            assertThat(result).isEqualTo(1656202554982L)
        }
    }
})
