package jp.ceed.android.mylapslogger.util

import jp.ceed.android.mylapslogger.dto.LapDto
import jp.ceed.android.mylapslogger.model.Sys
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateUtil() {

    companion object {

        private const val DELTA_FOR_WEATHER_DATA = 30 * 60 * 1000L

        private const val API_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"

        private const val API_TIME_FORMAT_WITH_MILLI_SEC = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"

        private const val HMS_FORMAT = "HH:mm:ss"

        private const val HM_FORMAT = "HH:mm"

        private const val YMD_FORMAT = "yyyy-MM-dd (EEE)"

        private const val YMD_HMS_FORMAT = "yyyy-MM-dd HH:mm:ss"

        private val YMD_SIMPLE_DATE_FORMAT = SimpleDateFormat(YMD_FORMAT, Locale.US)

        private val HMS_SIMPLE_DATE_FORMAT = SimpleDateFormat(HMS_FORMAT, Locale.JAPAN)

        private val HM_SIMPLE_DATE_FORMAT = SimpleDateFormat(HM_FORMAT, Locale.JAPAN)

        private val API_SIMPLE_DATE_FORMAT = SimpleDateFormat(API_TIME_FORMAT, Locale.JAPAN)

        private val API_SIMPLE_DATE_FORMAT_W_MILLI_SEC = SimpleDateFormat(API_TIME_FORMAT_WITH_MILLI_SEC, Locale.JAPAN)

        private val YMD_HMS_SIMPLE_DATE_FORMAT = SimpleDateFormat(YMD_HMS_FORMAT, Locale.JAPAN)


        fun toTimeFromDateTimeWithMilliSec(dateTimeStr: String): Long {
            return try {
                Objects.requireNonNull(API_SIMPLE_DATE_FORMAT_W_MILLI_SEC.parse(dateTimeStr)).time
            } catch (e: ParseException) {
                LogUtil.e(e)
                0L
            }
        }

        fun toHmsFromDateTimeWithMilliSec(dateStr: String): String {
            return try {
                HMS_SIMPLE_DATE_FORMAT.format(Objects.requireNonNull(API_SIMPLE_DATE_FORMAT_W_MILLI_SEC.parse(dateStr)).time)
            } catch (e: ParseException) {
                LogUtil.e(e)
                ""
            }
        }

        fun toHmFromDateTimeWithMilliSec(dateStr: String): String {
            return try {
                HM_SIMPLE_DATE_FORMAT.format(Objects.requireNonNull(API_SIMPLE_DATE_FORMAT_W_MILLI_SEC.parse(dateStr)).time)
            } catch (e: ParseException) {
                LogUtil.e(e)
                ""
            }
        }

        fun toYmdFormatFromDateTime(dateTimeStr: String): String? {
            return try {
                YMD_SIMPLE_DATE_FORMAT.format(Objects.requireNonNull(API_SIMPLE_DATE_FORMAT.parse(dateTimeStr)).time)
            } catch (e: ParseException) {
                LogUtil.e(e)
                null
            }
        }

        fun isValidForWeather(dateStr: String?): Boolean{
            if(dateStr == null){
                return false
            }
            val sessionStartTime = API_SIMPLE_DATE_FORMAT_W_MILLI_SEC.parse(dateStr)?.time ?: 0
            val now = System.currentTimeMillis()
            return now - sessionStartTime < DELTA_FOR_WEATHER_DATA
        }


        fun createDateTimeString(): String {
            return API_SIMPLE_DATE_FORMAT.format(System.currentTimeMillis())
        }

        fun convertToTimeMillis(timeString: String): Long {
            return API_SIMPLE_DATE_FORMAT.parse(timeString).time
        }

        fun createYmdHmsString(time: Long): String{
            return YMD_HMS_SIMPLE_DATE_FORMAT.format(Date(time))
        }

    }

}