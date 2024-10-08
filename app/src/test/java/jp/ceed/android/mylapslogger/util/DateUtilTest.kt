package jp.ceed.android.mylapslogger.util

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class DateUtilTest {

    @Test
    fun toTimeFromDateTimeWithMilliSec() {
        val result = DateUtil.toTimeFromDateTimeWithMilliSec("2022-06-26T09:15:54.982+09:00")
        assertThat(result).isEqualTo(1656202554982L)
    }
}