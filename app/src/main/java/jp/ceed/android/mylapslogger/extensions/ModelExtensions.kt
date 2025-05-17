package jp.ceed.android.mylapslogger.extensions

import jp.ceed.android.mylapslogger.network.response.Sessions

const val COMMA = ":"

/**
 * duration の値を Long 型で返す
 *
 * @return Long に変換した値
 */
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

/**
 * duration の値が１分を超えて「:」を含んでいる場合に秒単位の値に変換して返す
 *
 * @return 秒単位に変換した値
 */
fun String.durationToFloat(): Float {
    return try {
        if (this.contains(COMMA)) {
            val array = this.split(COMMA)
            val minutes: Float = array[0].toInt() * 60F
            minutes + array[1].toFloat()
        } else {
            this.toFloat()
        }
    } catch (e: NumberFormatException) {
        Float.MAX_VALUE
    }
}