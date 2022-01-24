package jp.ceed.android.mylapslogger.model

import jp.ceed.android.mylapslogger.network.response.SessionsResponse
import jp.ceed.android.mylapslogger.util.DateUtil
import java.io.Serializable

data class SessionListItem(
    val no: String,
    val startTime: String,
    val bestLap: String,
    val lapCount: String
): Serializable {
    constructor(sessions: SessionsResponse.Sessions): this(
        no = sessions.id.toString(),
        startTime = DateUtil.toHmsFromDateTimeWithMilliSec(sessions.dateTimeStart),
        bestLap = sessions.bestLap.duration,
        lapCount = sessions.laps.size.toString()
    )
}