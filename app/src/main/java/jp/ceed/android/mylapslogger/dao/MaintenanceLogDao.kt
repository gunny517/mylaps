package jp.ceed.android.mylapslogger.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import jp.ceed.android.mylapslogger.entity.MaintenanceLog

@Dao
interface MaintenanceLogDao {

    @Query("SELECT * from MaintenanceLog")
    fun findAll(): List<MaintenanceLog>

    @Query("SELECT * FROM MaintenanceLog WHERE item_id = (:itemId) ORDER BY issue_date ASC")
    fun findByItemId(itemId: Int): List<MaintenanceLog>

    @Query("SELECT * FROM maintenancelog WHERE id = (:id)")
    fun findById(id: Int): MaintenanceLog

    @Insert
    fun insert(entity: MaintenanceLog)

    @Update(entity = MaintenanceLog::class)
    fun update(entity: MaintenanceLog)

    @Delete(entity = MaintenanceLog::class)
    fun delete(entity: MaintenanceLog)

    fun save(entity: MaintenanceLog) {
        if (entity.id == 0) {
            insert(entity)
        } else {
            update(entity)
        }
    }
}
