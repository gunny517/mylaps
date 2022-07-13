package jp.ceed.android.mylapslogger.repository

import jp.ceed.android.mylapslogger.dao.ActivityInfoDao
import jp.ceed.android.mylapslogger.di.IoDispatcher
import jp.ceed.android.mylapslogger.entity.ActivityInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ActivityInfoRepository @Inject constructor (
    private val dao: ActivityInfoDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun findById(sessionId: Int): ActivityInfo? {
        var sessionInfo: ActivityInfo?
        withContext(dispatcher) {
            sessionInfo = dao.findById(sessionId)
        }
        return sessionInfo
    }

    suspend fun update(sessionInfo: ActivityInfo) {
        withContext(dispatcher) {
            dao.updateId(sessionInfo)
        }
    }

    suspend fun insert(sessionInfo: ActivityInfo) {
        withContext(dispatcher) {
            dao.insert(sessionInfo)
        }
    }
}