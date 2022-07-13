package jp.ceed.android.mylapslogger.repository

import jp.ceed.android.mylapslogger.dao.ErrorLogDao
import jp.ceed.android.mylapslogger.di.IoDispatcher
import jp.ceed.android.mylapslogger.dto.ErrorLogItem
import jp.ceed.android.mylapslogger.entity.ErrorLog
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ErrorLogRepository @Inject constructor (
    private val dao: ErrorLogDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    suspend fun findAll(): List<ErrorLogItem> {
        val results: ArrayList<ErrorLogItem> = ArrayList()
        withContext(dispatcher){
            val list = dao.findAll()
            for(entry in list){
                results.add(ErrorLogItem(entry.stackTrace))
            }
        }
        return results
    }

    suspend fun insert(errorLog: ErrorLog){
        withContext(dispatcher){
            dao.insert(errorLog)
        }
    }

}