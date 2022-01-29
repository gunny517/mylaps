package jp.ceed.android.mylapslogger.dao

import androidx.room.*
import jp.ceed.android.mylapslogger.entity.Practice

@Dao
interface PracticeDao {

    @Query("SELECT * FROM Practice WHERE id = (:id)")
    fun findById(id: Int): Practice

    @Query("SELECT * FROM Practice")
    fun findAll(): List<Practice>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun  insertIgnore(practice: Practice): Long

    @Update
    fun update(practice: Practice)

    @Transaction
    fun save(practice: Practice){
        if(insertIgnore(practice) == -1L){
            update(practice)
        }
    }

}