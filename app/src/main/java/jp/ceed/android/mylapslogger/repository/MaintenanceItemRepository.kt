package jp.ceed.android.mylapslogger.repository

import jp.ceed.android.mylapslogger.dao.MaintenanceItemDao
import jp.ceed.android.mylapslogger.di.IoDispatcher
import jp.ceed.android.mylapslogger.entity.MaintenanceItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MaintenanceItemRepository @Inject constructor (
    private val maintenanceItemDao: MaintenanceItemDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun findAll(): List<MaintenanceItem> {
        return withContext(dispatcher) {
            maintenanceItemDao.findAll()
        }
    }

    suspend fun findById(id: Int): MaintenanceItem {
        return withContext(dispatcher) {
            maintenanceItemDao.findById(id)
        }
    }

    suspend fun insert(entity: MaintenanceItem) {
        withContext(dispatcher) {
            maintenanceItemDao.insert(entity)
        }
    }

    suspend fun update(entity: MaintenanceItem) {
        withContext(dispatcher) {
            maintenanceItemDao.update(entity)
        }
    }

    suspend fun save(entity: MaintenanceItem) {
        withContext(dispatcher) {
            if (entity.id == 0) {
                maintenanceItemDao.insert(entity)
            } else {
                maintenanceItemDao.update(entity)
            }
        }
    }

    suspend fun delete(entity: MaintenanceItem) {
        withContext(dispatcher) {
            maintenanceItemDao.delete(entity)
        }
    }
}