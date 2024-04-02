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

    suspend fun findById(id: Long): ActivityInfo? =
        withContext(dispatcher) {
            dao.findById(id)
        }

    suspend fun update(activityInfo: ActivityInfo) =
        withContext(dispatcher) {
            dao.updateId(activityInfo)
        }

    suspend fun insert(activityInfo: ActivityInfo) =
        withContext(dispatcher) {
            dao.insert(activityInfo)
        }
}