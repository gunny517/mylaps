package jp.ceed.android.mylapslogger.dto

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import jp.ceed.android.mylapslogger.R
import jp.ceed.android.mylapslogger.extensions.durationToFloat
import jp.ceed.android.mylapslogger.network.response.Sessions
import jp.ceed.android.mylapslogger.util.DateUtil
import java.util.Locale
import kotlin.math.roundToInt

sealed class PracticeResultsItem {

    data class Lap(
        var sessionId: Long = 0L,
        var number: String = "",
        var duration: String = "",
        var status: Status = Status.NONE,
        var diffPrevLap: String? = null,
        var cellBgColor: Int = 0,
        var diffTextColor: Int = 0,
        var speedLevel: Float = 0F,
        var rawSectorValues: List<String> = listOf(),
        var sectorDataList: List<SectorData> = listOf(),
    ): PracticeResultsItem(){

        /**
         * セカンダリーコンストラクター
         */
        constructor(laps: Sessions.Sessions.Laps, sessions: Sessions.Sessions): this(
            sessionId = DateUtil.toTimeFromDateTimeWithMilliSec(sessions.dateTimeStart),
            number = laps.nr.toString(),
            duration = laps.duration,
            status = Status.statusOf(laps.status),
            diffPrevLap = laps.diffPrevLap,
            cellBgColor = getCellBgColor(laps.status),
            diffTextColor = getDiffTextColor(laps.status),
            rawSectorValues = laps.sections.map { it.duration }
        )

        /**
         * 全てのセクタータイムが正常な値であるかどうかの真偽値を返す。
         * （全てのセクタータイム合計がラップタイムと同じであるかどうかで判定）
         *
         * @return 全てのセクタータイムが正常な値であるかどうかの真偽値
         */
        fun isValidSectorTime(): Boolean {
            var sectorTotal = rawSectorValues.map{ it.durationToFloat() }.sum()
            sectorTotal = (sectorTotal * 1000.0F).roundToInt() / 1000.0F
            return sectorTotal == duration.durationToFloat()
        }

        /**
         * 各セクターのタイム値を連結して State 値に応じた文字色を付与して返す
         *
         * @param context コンテキスト
         * @return 各セクターのタイム値を連結して State 値に応じた文字色を付与した SpannableStringBuilder
         */
        fun concatSectionText(context: Context): CharSequence? {
            if (sectorDataList.size < 2) {
                return null
            }
            val builder = SpannableStringBuilder()
            var length = builder.length
            sectorDataList.forEach {
                builder.append(String.format(Locale.JAPAN, "%.3f", it.duration.durationToFloat()))
                builder.setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(context, getTextColor(it.status))),
                    length,
                    builder.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                length = builder.length
                builder.append("   ")
            }
            return builder
        }
    }

    /**
     * SectorData
     *
     * @property status [Status]
     * @property duration duration 値
     */
    data class SectorData(
        val status: Status,
        val duration: String
    )

    data class Section(
        var sessionId: Long,
        var sectionTitle: String,
        var sessionTime: String,
        var sessionInfoLabelColor: Int? = null,
        var averageDuration: String,
        var medianDuration: String
    ): PracticeResultsItem(){
        constructor(sessions: Sessions.Sessions): this(
            sessionId = DateUtil.toTimeFromDateTimeWithMilliSec(sessions.dateTimeStart),
            sectionTitle = sessions.id.toString (),
            averageDuration = sessions.aveLapDuration ?: "",
            medianDuration = sessions.medianLapDuration ?: "",
            sessionTime = DateUtil.toHmFromDateTimeWithMilliSec(sessions.dateTimeStart)
        )
    }

    data class Summary(
        var sessionId: Long,
        var number: String,
        var duration: String,
        var lapCount: Int,
    ): PracticeResultsItem(){
        constructor(sessions: Sessions.Sessions):this(
            sessionId = DateUtil.toTimeFromDateTimeWithMilliSec(sessions.dateTimeStart),
            number = sessions.bestLap?.nr.toString(),
            duration = sessions.bestLap?.duration ?: "",
            lapCount = sessions.laps.size
        )
    }

    companion object {

        fun getTextColor(status: Status): Int {
            return when(status) {
                Status.REPORTBEST ->
                    R.color.text_best
                else ->
                    R.color.text_default
            }
        }

        fun getCellBgColor(status: String?): Int {
            return when(status) {
                Status.REPORTBEST.name ->
                    R.color.bg_lap_list_report_best
                Status.SESSIONBEST.name ->
                    R.color.bg_lap_list_session_best
                else ->
                    R.color.window_back_ground
            }
        }

        fun getDiffTextColor(status: String?): Int {
            return when(status) {
                Status.REPORTBEST.name,
                Status.SESSIONBEST.name,
                Status.FASTER.name ->
                    R.color.text_faster
                Status.SLOWER.name ->
                    R.color.text_slower
                else ->
                    R.color.text_default
            }
        }
    }
}

enum class Status {
    NONE,
    REPORTBEST,
    SESSIONBEST,
    FASTER,
    SLOWER;

    companion object {
        fun statusOf(status: String): Status{
            for(entry in entries){
                if(entry.name == status){
                    return entry
                }
            }
            return NONE
        }
    }

}