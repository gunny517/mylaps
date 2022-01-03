package jp.ceed.android.mylapslogger.util

import jp.ceed.android.mylapslogger.dto.LapDto
import jp.ceed.android.mylapslogger.model.Sys
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateUtil() {

    companion object {

        private const val API_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"

        private const val API_TIME_FORMAT_WITH_MILLI_SEC = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"

        private const val HMS_FORMAT = "HH:mm:ss"

        private const val YMD_FORMAT = "yyyy/MM/dd"

        private val YMD_SIMPLE_DATE_FORMAT = SimpleDateFormat(YMD_FORMAT, Locale.JAPAN)

        private val HMS_SIMPLE_DATE_FORMAT = SimpleDateFormat(HMS_FORMAT, Locale.JAPAN)

        private val API_SIMPLE_DATE_FORMAT = SimpleDateFormat(API_TIME_FORMAT, Locale.JAPAN)

        private val API_SIMPLE_DATE_FORMAT_W_MILLI_SEC = SimpleDateFormat(API_TIME_FORMAT_WITH_MILLI_SEC, Locale.JAPAN)


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

        fun toYmdFormatFromDateTime(dateTimeStr: String): String? {
            return try {
                YMD_SIMPLE_DATE_FORMAT.format(Objects.requireNonNull(API_SIMPLE_DATE_FORMAT.parse(dateTimeStr)).time)
            } catch (e: ParseException) {
                LogUtil.e(e)
                null
            }
        }

        fun isToday(dateStr: String?): Boolean{
            if(dateStr == null){
                return false
            }
            val ymd = dateStr.split("T")[0].split("-")
            val cal: Calendar = Calendar.getInstance()
            return cal.get(Calendar.YEAR) == ymd[0].toInt()
                    && cal.get(Calendar.MONTH) == ymd[1].toInt() -1
                    && cal.get(Calendar.DATE) == ymd[2].toInt()
        }


        fun createDateTimeString(): String {
            return API_SIMPLE_DATE_FORMAT.format(System.currentTimeMillis())
        }

    }

}