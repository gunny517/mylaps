package jp.ceed.android.mylapslogger.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import jp.ceed.android.mylapslogger.entity.MaintenanceItem

@Dao
interface MaintenanceItemDao {

    @Query("SELECT * FROM MaintenanceItem")
    fun findAll(): List<MaintenanceItem>

    @Query("SELECT * FROM MaintenanceItem WHERE id = (:id)")
    fun findById(id: Int): MaintenanceItem

    @Insert
    fun insert(entity: MaintenanceItem)

    @Update(entity = MaintenanceItem::class)
    fun update(entity: MaintenanceItem)

    @Delete(entity = MaintenanceItem::class)
    fun delete(entity: MaintenanceItem)

}