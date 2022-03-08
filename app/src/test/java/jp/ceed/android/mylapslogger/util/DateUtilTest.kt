package jp.ceed.android.mylapslogger.util


import com.google.common.truth.Truth
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DateUtilTest {

    @Test
    fun isToday(){
        val isToday = DateUtil.isToday("2022-03-08T09:15:00+09:00")
        Truth.assertThat(isToday).isTrue()
        val isYesterday = DateUtil.isToday("2022-03-07T09:15:00+09:00")
        Truth.assertThat(isYesterday).isFalse()
    }

}