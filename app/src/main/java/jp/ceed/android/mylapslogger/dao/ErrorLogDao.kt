package jp.ceed.android.mylapslogger.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import jp.ceed.android.mylapslogger.entity.ErrorLog

@Dao
interface ErrorLogDao {

    @Insert
    fun insert(errorLog: ErrorLog)

    @Query("SELECT * FROM ErrorLog")
    fun findAll(): List<ErrorLog>

}