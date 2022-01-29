package jp.ceed.android.mylapslogger.dao

import androidx.room.*
import jp.ceed.android.mylapslogger.entity.Track

@Dao
interface TrackDao {

    @Query("SELECT * from Track")
    fun findAll(): List<Track>

    @Query("SELECT * FROM Track WHERE id = (:id)")
    fun findById(id: Int): Track

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertIgnore(track: Track): Long

    @Update()
    fun update(track: Track)

    @Transaction
    fun save(track: Track){
        if(insertIgnore(track) == -1L){
            update(track)
        }
    }

}