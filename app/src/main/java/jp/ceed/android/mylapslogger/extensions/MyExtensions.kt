package jp.ceed.android.mylapslogger.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Float.toDisplayValue(): String = String.format(Locale.JAPAN, "%.03f", this)

fun Long.toYmdString(): String {
    return SimpleDateFormat("yyyy-MM-dd", Locale.JAPAN).format(Date(this))
}