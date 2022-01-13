package jp.ceed.android.mylapslogger.args

import java.io.Serializable

data class SessionInfoFragmentParams(
    val sessionId: Long,
    val averageDuration: String,
    val medianDuration: String
): Serializable {
}