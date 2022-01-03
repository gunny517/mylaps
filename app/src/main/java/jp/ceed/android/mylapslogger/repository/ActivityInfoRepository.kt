package jp.ceed.android.mylapslogger.repository

import android.content.Context
import jp.ceed.android.mylapslogger.database.AppDatabase
import jp.ceed.android.mylapslogger.entity.ActivityInfo
import jp.ceed.android.mylapslogger.util.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ActivityInfoRepository(val context: Context) {

    private val dao = AppDatabase.getInstance(context).activityInfoDao()

    suspend fun findById(sessionId: Int): ActivityInfo? {
        var sessionInfo: ActivityInfo? = null
        withContext(Dispatchers.IO) {
            sessionInfo = dao.findById(sessionId)
            Util.checkThread(context, "withContext()")
        }
        return sessionInfo
    }

    suspend fun update(sessionInfo: ActivityInfo) {
        withContext(Dispatchers.IO) {
            dao.updateId(sessionInfo)
        }
    }

    suspend fun insert(sessionInfo: ActivityInfo) {
        withContext(Dispatchers.IO) {
            dao.insert(sessionInfo)
        }
    }

}