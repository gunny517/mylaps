package jp.ceed.android.mylapslogger.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DateUtil {

    companion object {

        private const val DELTA_FOR_WEATHER_DATA = 30 * 60 * 1000L

        private const val API_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssX"

        private const val API_TIME_FORMAT_WITH_MILLI_SEC = "yyyy-MM-dd'T'HH:mm:ss.SSSX"

        private const val HM_FORMAT = "HH:mm"

        private const val YMD_FORMAT = "yyyy-MM-dd (EEE)"

        private const val YMD_HMS_FORMAT = "yyyy-MM-dd HH:mm:ss"

        private val YMD_SIMPLE_DATE_FORMAT = SimpleDateFormat(YMD_FORMAT, Locale.US)

        private val HM_SIMPLE_DATE_FORMAT = SimpleDateFormat(HM_FORMAT, Locale.JAPAN)

        private val API_SIMPLE_DATE_FORMAT = SimpleDateFormat(API_TIME_FORMAT, Locale.JAPAN)

        private val API_SIMPLE_DATE_FORMAT_W_MILLI_SEC = SimpleDateFormat(API_TIME_FORMAT_WITH_MILLI_SEC, Locale.JAPAN)

        private val YMD_HMS_SIMPLE_DATE_FORMAT = SimpleDateFormat(YMD_HMS_FORMAT, Locale.JAPAN)


        /**
         * API レスポンスの日付文字列を UNIX タイムに変換して返す
         *
         * @param subject 対象の日付文字列
         * @return UNIX タイム
         */
        fun toTimeFromDateTimeWithMilliSec(subject: String?): Long {
            return try {
                API_SIMPLE_DATE_FORMAT_W_MILLI_SEC.parse(subject ?: "")?.time ?: 0L
            } catch (e: ParseException) {
                LogUtil.e(e)
                0L
            }
        }

        /**
         * API レスポンスの日付文字列を HH:mm 形式の時間文字列に変換して返す
         *
         * @param subject 対象の日付文字列
         * @return HH:mm 形式の時間文字列
         */
        fun toHmFromDateTimeWithMilliSec(subject: String?): String {
            subject ?: return ""
            try {
                API_SIMPLE_DATE_FORMAT_W_MILLI_SEC.parse(subject)?.let {
                    return HM_SIMPLE_DATE_FORMAT.format(it.time)
                }
                return ""
            } catch (e: ParseException) {
                LogUtil.e(e)
                return ""
            }
        }

        /**
         * API レスポンスの日付文字列を yyyy-MM-dd(EEE) 形式の日付文字列に変換して返す
         *
         * @param subject 対象の日付文字列
         * @return yyyy-MM-dd(EEE) 形式の日付文字列
         */
        fun toYmdFormatFromDateTime(subject: String): String? {
            try {
                API_SIMPLE_DATE_FORMAT.parse(subject)?.let {
                    return YMD_SIMPLE_DATE_FORMAT.format(it.time)
                }
                return null
            } catch (e: ParseException) {
                LogUtil.e(e)
                return null
            }
        }

        /**
         * 気象データを取得するのに妥当な期間内にあるかどうか判定する
         *
         * @param subject 対象の日付
         * @return 気象データを取得するのに妥当な期間内にあるかどうか
         */
        fun isValidForWeather(subject: String?): Boolean{
            subject ?: return false
            val sessionStartTime = API_SIMPLE_DATE_FORMAT_W_MILLI_SEC.parse(subject)?.time ?: 0
            val now = System.currentTimeMillis()
            return now - sessionStartTime < DELTA_FOR_WEATHER_DATA
        }

        /**
         * 現在時刻を 2025-01-01T12:30:00.000+09:00 形式の日付文字列に変換して返す
         *
         * @return 2025-01-01T12:30:00.000+09:00 形式の日付文字列
         */
        fun createDateTimeString(): String {
            return API_SIMPLE_DATE_FORMAT.format(System.currentTimeMillis())
        }

        /**
         * 引数で指定された 2025-01-01T12:30:00.000+09:00 形式の日付文字列を UNIX タイムに変換して返す
         *
         * @param subject 対象の日付文字列
         * @return UNIX タイム
         */
        fun convertToTimeMillis(subject: String): Long {
            return API_SIMPLE_DATE_FORMAT_W_MILLI_SEC.parse(subject)?.time ?: 0L
        }

        /**
         * 引数で指定された UNIX タイムを 2025-01-01 12:30:00 形式の日付文字列に変換して返す
         *
         * @param subject 対象の UNIX タイム
         * @return 2025-01-01T12:30:00.000+09:00 形式の日付文字列
         */
        fun createYmdHmsString(subject: Long): String{
            return YMD_HMS_SIMPLE_DATE_FORMAT.format(Date(subject))
        }

        /**
         * 引数で指定された日付文字列が本日であるかどうか判定する
         *
         * @param target 対象の日付文字列
         * @return 本日である場合は true.
         */
        fun isToday(target: String): Boolean{
            val startDate = API_SIMPLE_DATE_FORMAT.parse(target) ?: return false
            val subject = Calendar.getInstance()
            subject.time = startDate
            val today = Calendar.getInstance()
            return try {
                today.get(Calendar.YEAR) == subject.get(Calendar.YEAR)
                        && today.get(Calendar.DAY_OF_YEAR) == subject.get(Calendar.DAY_OF_YEAR)
            } catch (e: ParseException){
                false
            }
        }

    }

}