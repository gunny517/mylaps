package jp.ceed.android.mylapslogger.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import jp.ceed.android.mylapslogger.entity.MaintenanceLog

@Dao
interface MaintenanceLogDao {

    @Query("SELECT * from MaintenanceLog")
    fun findAll(): List<MaintenanceLog>

    @Insert
    fun insert(entity: MaintenanceLog)

    @Update(entity = MaintenanceLog::class)
    fun update(entity: MaintenanceLog)



}