package jp.ceed.android.mylapslogger.model

import jp.ceed.android.mylapslogger.R
import jp.ceed.android.mylapslogger.network.response.Sessions
import jp.ceed.android.mylapslogger.util.DateUtil
import java.io.Serializable

data class SessionListItem(
    val no: String,
    val startTime: String,
    val bestLap: String,
    val lapCount: String,
    val bestLapTextColor: Int
): Serializable {
    constructor(sessions: Sessions.Sessions, totalBest: String): this(
        no = sessions.id.toString(),
        startTime = DateUtil.toHmFromDateTimeWithMilliSec(sessions.dateTimeStart),
        bestLap = sessions.bestLap?.duration ?: "",
        lapCount = sessions.laps.size.toString(),
        bestLapTextColor = if(sessions.bestLap?.duration.equals(totalBest)){
            R.color.text_session_list_best_lap
        }else{
            R.color.text_default
        }
    )
}