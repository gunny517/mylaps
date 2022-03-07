package jp.ceed.android.mylapslogger.dto

import jp.ceed.android.mylapslogger.util.DateUtil

data class ErrorLogItem(
    val stackTrace: String,
    val dateTime: String
) {
    constructor(_stackTrace: String) :this(
        stackTrace = _stackTrace,
        dateTime = DateUtil.createYmdHmsString(System.currentTimeMillis())
    )
}