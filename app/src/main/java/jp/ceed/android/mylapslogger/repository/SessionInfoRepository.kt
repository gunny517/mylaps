package jp.ceed.android.mylapslogger.repository

import android.content.Context
import jp.ceed.android.mylapslogger.database.AppDatabase
import jp.ceed.android.mylapslogger.entity.SessionInfo
import jp.ceed.android.mylapslogger.model.WeatherDataDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SessionInfoRepository(context: Context) {

    private val dao = AppDatabase.getInstance(context).sessionInfoDao()


    suspend fun findBySessionId(sessionId: Long): SessionInfo? {
        var sessionInfo: SessionInfo? = null
        withContext(Dispatchers.IO) {
            sessionInfo = dao.findById(sessionId)
        }
        return sessionInfo
    }

    suspend fun insert(sessionInfo: SessionInfo) {
        withContext(Dispatchers.IO) {
            dao.insert(sessionInfo)
        }
    }

    suspend fun update(sessionInfo: SessionInfo) {
        withContext(Dispatchers.IO) {
            dao.update(sessionInfo)
        }
    }

    suspend fun loadWeatherAndSave(sessionId: Long){
        withContext(Dispatchers.IO){

        }
    }

    suspend fun saveSessionInfo(sessionInfo: SessionInfo){
        withContext(Dispatchers.IO){
            insert(sessionInfo)
        }
    }

}