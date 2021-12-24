package jp.ceed.android.mylapslogger.repository

import android.app.Application
import jp.ceed.android.mylapslogger.database.AppDatabase
import jp.ceed.android.mylapslogger.entity.ActivityInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ActivityInfoRepository(application: Application) {

	private val dao = AppDatabase.getInstance(application).activityInfoDao()


	suspend fun findBySessionId(sessionId: Int): ActivityInfo? {
		var sessionInfo: ActivityInfo? = null
		withContext(Dispatchers.IO){
			sessionInfo = dao.findBySessionId(sessionId)
		}
		return sessionInfo
	}


	suspend fun updateSessionInfo(sessionInfo: ActivityInfo){
		withContext(Dispatchers.IO){
			dao.updateBySessionId(sessionInfo)
		}
	}


	suspend fun insertSessionInfo(sessionInfo: ActivityInfo){
		withContext(Dispatchers.IO){
			dao.insert(sessionInfo)
		}
	}

}