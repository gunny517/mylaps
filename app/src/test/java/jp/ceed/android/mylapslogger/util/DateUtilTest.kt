package jp.ceed.android.mylapslogger.util

import android.text.format.DateUtils
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateUtilTest {

    @Test
    fun toTimeFromDateTimeWithMilliSec() {
        val result = DateUtil.toTimeFromDateTimeWithMilliSec("2022-06-26T09:15:54.982+09:00")
        assertThat(result).isEqualTo(1656202554982L)
    }

    @Test
    fun toHmFromDateTimeWithMilliSec() {
        assertThat(DateUtil.toHmFromDateTimeWithMilliSec("2025-01-01T16:40:00+09:00"))
            .isEqualTo("")
        assertThat(DateUtil.toHmFromDateTimeWithMilliSec("2022-06-26T10:15:54.982+09:00"))
            .isEqualTo("10:15")
    }

    @Test
    fun toYmdFormatFromDateTime() {
        assertThat(DateUtil.toYmdFormatFromDateTime("2025-02-01T12:34:56+09:00"))
            .isEqualTo("2025-02-01 (Sat)")
        assertThat(DateUtil.toYmdFormatFromDateTime("2025-02-01T12:34:"))
            .isEqualTo(null)
    }

    @Test
    fun isValidForWeather() {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.JAPANESE)
        var dateTime = System.currentTimeMillis() - 20 * DateUtils.MINUTE_IN_MILLIS
        assertThat(DateUtil.isValidForWeather(format.format(dateTime)))
            .isEqualTo(true)
        dateTime -= 20 * DateUtils.MINUTE_IN_MILLIS
        assertThat(DateUtil.isValidForWeather(format.format(dateTime)))
            .isEqualTo(false)
    }

    @Test
    fun createDateTimeString() {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.JAPANESE)
        val time = System.currentTimeMillis()
        assertThat(DateUtil.createDateTimeString()).isEqualTo(format.format(Date(time)))
    }

    @Test
    fun convertToTimeMillis() {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.JAPANESE)
        val time = System.currentTimeMillis()
        val expect = format.format(time)
        assertThat(DateUtil.convertToTimeMillis(expect)).isEqualTo(time)
    }

    @Test
    fun isToday(){
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.JAPANESE)
        val expect = format.format(System.currentTimeMillis())
        assertThat(DateUtil.isToday(expect)).isEqualTo(true)
    }

}