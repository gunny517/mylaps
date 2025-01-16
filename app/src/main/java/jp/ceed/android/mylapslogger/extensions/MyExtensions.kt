package jp.ceed.android.mylapslogger.extensions

import java.util.Locale

fun Float.toDisplayValue(): String = String.format(Locale.JAPAN, "%.03f", this)