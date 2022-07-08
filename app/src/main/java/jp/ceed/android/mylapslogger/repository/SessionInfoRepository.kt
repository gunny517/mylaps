package jp.ceed.android.mylapslogger.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import jp.ceed.android.mylapslogger.database.AppDatabase
import jp.ceed.android.mylapslogger.di.IoDispatcher
import jp.ceed.android.mylapslogger.entity.SessionInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SessionInfoRepository @Inject constructor (
    @ApplicationContext context: Context,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    private val dao = AppDatabase.getInstance(context).sessionInfoDao()


    suspend fun findBySessionId(sessionId: Long): SessionInfo? {
        var sessionInfo: SessionInfo? = null
        withContext(dispatcher) {
            sessionInfo = dao.findById(sessionId)
        }
        return sessionInfo
    }

    suspend fun findAll(): List<SessionInfo> {
        var list: List<SessionInfo> = mutableListOf<SessionInfo>()
        withContext(dispatcher){
            list = dao.findAll()
        }
        return list
    }

    suspend fun insert(sessionInfo: SessionInfo) {
        withContext(dispatcher) {
            dao.insert(sessionInfo)
        }
    }

    suspend fun update(sessionInfo: SessionInfo) {
        withContext(dispatcher) {
            dao.update(sessionInfo)
        }
    }

    suspend fun loadWeatherAndSave(sessionId: Long){
        withContext(dispatcher){

        }
    }

    suspend fun saveSessionInfo(sessionInfo: SessionInfo){
        withContext(dispatcher){
            insert(sessionInfo)
        }
    }

    suspend fun deleteAll(){
        withContext(dispatcher){
            dao.deleteAll()
        }
    }

}