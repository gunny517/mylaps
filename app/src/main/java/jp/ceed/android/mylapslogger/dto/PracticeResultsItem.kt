package jp.ceed.android.mylapslogger.dto

import jp.ceed.android.mylapslogger.R
import jp.ceed.android.mylapslogger.network.response.SessionsResponse
import jp.ceed.android.mylapslogger.util.DateUtil

sealed class PracticeResultsItem {

    data class Lap(
        var sessionId: Long,
        var number: String,
        var duration: String,
        var status: Status,
        var diffPrevLap: String? = null,
        var cellBgColor: Int,
        var diffTextColor: Int,
        var speedLevel: Float = 0F
    ): PracticeResultsItem(){
        constructor(laps: SessionsResponse.Sessions.Laps, sessions: SessionsResponse.Sessions): this(
            sessionId = DateUtil.toTimeFromDateTimeWithMilliSec(sessions.dateTimeStart),
            number = laps.nr.toString(),
            duration = laps . duration,
            status = Status.statusOf(laps.status),
            cellBgColor = getCellBgColor(laps.status),
            diffTextColor = getDiffTextColor(laps.status),
            diffPrevLap = laps.diffPrevLap
        )
    }

    data class Section(
        var sessionId: Long,
        var sectionTitle: String,
        var sessionTime: String,
        var sessionInfoLabel: String? = null,
        var averageDuration: String,
        var medianDuration: String
    ): PracticeResultsItem(){
        constructor(sessions: SessionsResponse.Sessions): this(
            sessionId = DateUtil.toTimeFromDateTimeWithMilliSec(sessions.dateTimeStart),
            sectionTitle = sessions.id.toString (),
            averageDuration = sessions.aveLapDuration,
            medianDuration = sessions.medianLapDuration,
            sessionTime = DateUtil.toHmsFromDateTimeWithMilliSec(sessions.dateTimeStart)
        )
    }

    data class Summary(
        var sessionId: Long,
        var number: String,
        var duration: String,
        var medianDuration: String
    ): PracticeResultsItem(){
        constructor(sessions: SessionsResponse.Sessions):this(
            sessionId = DateUtil.toTimeFromDateTimeWithMilliSec(sessions.dateTimeStart),
            number = sessions.bestLap.nr.toString(),
            duration = sessions.bestLap.duration,
            medianDuration = sessions.medianLapDuration
        )
    }

    companion object {

        fun getCellBgColor(status: String?): Int {
            return when {
                Status.REPORTBEST.name == status -> {
                    R.color.bg_lap_list_report_best
                }
                Status.SESSIONBEST.name == status -> {
                    R.color.bg_lap_list_session_best
                }
                else -> {
                    R.color.window_back_ground
                }
            }
        }

        fun getDiffTextColor(status: String?): Int {
            return if (Status.REPORTBEST.name == status
                || Status.SESSIONBEST.name == status
                || Status.FASTER.name == status
            ) {
                R.color.text_faster
            } else if (Status.SLOWER.name == status) {
                R.color.text_slower
            } else {
                R.color.text_default
            }
        }
    }
}

enum class Status() {
    NONE,
    REPORTBEST,
    SESSIONBEST,
    FASTER,
    SLOWER;

    companion object {
        fun statusOf(status: String): Status{
            for(entry in Status.values()){
                if(entry.name == status){
                    return entry
                }
            }
            return Status.NONE
        }
    }

}