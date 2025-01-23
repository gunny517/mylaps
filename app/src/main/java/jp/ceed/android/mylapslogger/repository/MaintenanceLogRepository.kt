package jp.ceed.android.mylapslogger.repository

import jp.ceed.android.mylapslogger.dao.MaintenanceLogDao
import jp.ceed.android.mylapslogger.di.IoDispatcher
import jp.ceed.android.mylapslogger.entity.MaintenanceLog
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MaintenanceLogRepository @Inject constructor(
    private val maintenanceLogDao: MaintenanceLogDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun findAll(): List<MaintenanceLog> {
        return withContext(dispatcher) {
            maintenanceLogDao.findAll()
        }
    }

    suspend fun findByItemId(itemId: Int): List<MaintenanceLog> {
        return withContext(dispatcher) {
            maintenanceLogDao.findByItemId(itemId)
        }
    }

    suspend fun findById(id: Int): MaintenanceLog {
        return withContext(dispatcher) {
            maintenanceLogDao.findById(id)
        }
    }

    suspend fun save(entity: MaintenanceLog) {
        return withContext(dispatcher) {
            maintenanceLogDao.save(entity)
        }
    }

    suspend fun delete(entity: MaintenanceLog) {
        return withContext(dispatcher) {
            maintenanceLogDao.delete(entity)
        }
    }
}