package jp.ceed.android.mylapslogger.extensions

import jp.ceed.android.mylapslogger.network.response.Sessions

const val COMMA = ":"

fun Sessions.Sessions.Laps.durationAsLong(): Long {
    return if(duration.contains(COMMA)) {
        val ar = duration.split(COMMA)
        val minute: Long = ar[0].toLong()
        val second: Long = ar[1].toFloat().toLong()
        (minute * 60L) + second
    } else {
        duration.toFloat().toLong()
    }
}