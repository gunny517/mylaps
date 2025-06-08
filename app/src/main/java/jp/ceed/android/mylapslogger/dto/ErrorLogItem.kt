package jp.ceed.android.mylapslogger.dto

import jp.ceed.android.mylapslogger.entity.ErrorLog
import jp.ceed.android.mylapslogger.util.DateUtil

data class ErrorLogItem(
    val stackTrace: String,
    val dateTime: String
) {
    constructor(errorLog: ErrorLog) :this(
        stackTrace = errorLog.stackTrace,
        dateTime = DateUtil.createYmdHmsString(errorLog.created)
    )
}