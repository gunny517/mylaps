package jp.ceed.android.mylapslogger.dao

import androidx.room.Dao
import androidx.room.Query
import jp.ceed.android.mylapslogger.entity.ActivityInfoTrack

@Dao
interface ActivityInfoTrackDao {

    @Query("SELECT t.id, " +
            "t.name, " +
            "a.fuel_consumption, " +
            "a.date_time " +
            "FROM Track t, ActivityInfo a " +
            "WHERE a.track_id = t.id " +
            "AND a.fuel_consumption IS NOT NULL AND a.fuel_consumption != '' " +
            "ORDER BY a.date_time DESC")
    fun findAsFuelConsumptionList(): List<ActivityInfoTrack>
}