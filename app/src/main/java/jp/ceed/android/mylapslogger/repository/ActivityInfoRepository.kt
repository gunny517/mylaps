package jp.ceed.android.mylapslogger.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import jp.ceed.android.mylapslogger.database.AppDatabase
import jp.ceed.android.mylapslogger.di.IoDispatcher
import jp.ceed.android.mylapslogger.entity.ActivityInfo
import jp.ceed.android.mylapslogger.util.Util
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ActivityInfoRepository @Inject constructor (
    @ApplicationContext val context: Context,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private val dao = AppDatabase.getInstance(context).activityInfoDao()

    suspend fun findById(sessionId: Int): ActivityInfo? {
        var sessionInfo: ActivityInfo?
        withContext(dispatcher) {
            sessionInfo = dao.findById(sessionId)
            Util.checkThread(context, "withContext()")
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