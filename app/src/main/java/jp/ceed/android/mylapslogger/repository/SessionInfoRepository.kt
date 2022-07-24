package jp.ceed.android.mylapslogger.repository

import jp.ceed.android.mylapslogger.dao.SessionInfoDao
import jp.ceed.android.mylapslogger.di.IoDispatcher
import jp.ceed.android.mylapslogger.entity.SessionInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SessionInfoRepository @Inject constructor (
    private val dao: SessionInfoDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun findBySessionId(sessionId: Long): SessionInfo? =
        withContext(dispatcher) {
            dao.findById(sessionId)
        }

    suspend fun findAll(): List<SessionInfo> =
        withContext(dispatcher){
            dao.findAll()
        }

    suspend fun insert(sessionInfo: SessionInfo) =
        withContext(dispatcher) {
            dao.insert(sessionInfo)
        }

    suspend fun update(sessionInfo: SessionInfo) =
        withContext(dispatcher) {
            dao.update(sessionInfo)
        }

}