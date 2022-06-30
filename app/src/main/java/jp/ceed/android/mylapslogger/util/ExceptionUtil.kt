package jp.ceed.android.mylapslogger.util

import android.content.Context
import jp.ceed.android.mylapslogger.entity.ErrorLog
import jp.ceed.android.mylapslogger.repository.ErrorLogRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class ExceptionUtil(context: Context) {

    @Inject lateinit var errorLogRepository: ErrorLogRepository

    fun save(t: Throwable?, scope: CoroutineScope){
        val errorLog = ErrorLog(t?.stackTraceToString())
        scope.launch {
            errorLogRepository.insert(errorLog)
        }
    }

}