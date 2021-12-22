package jp.ceed.android.mylapslogger.repository

import android.app.Application
import jp.ceed.android.mylapslogger.database.AppDatabase
import jp.ceed.android.mylapslogger.entity.SessionInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SessionInfoRepository(application: Application) {

	private val dao = AppDatabase.getInstance(application).sessionInfoDao()


	suspend fun findBySessionId(sessionId: Int): SessionInfo? {
		var sessionInfo: SessionInfo? = null
		withContext(Dispatchers.IO){
			sessionInfo = dao.findBySessionId(sessionId)
		}
		return sessionInfo
	}


	suspend fun updateSessionInfo(sessionInfo: SessionInfo){
		withContext(Dispatchers.IO){
			dao.updateBySessionId(sessionInfo)
		}
	}


	suspend fun insertSessionInfo(sessionInfo: SessionInfo){
		withContext(Dispatchers.IO){
			dao.insert(sessionInfo)
		}
	}

}