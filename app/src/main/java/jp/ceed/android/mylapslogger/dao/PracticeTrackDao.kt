package jp.ceed.android.mylapslogger.dao

import androidx.room.Dao
import androidx.room.Query
import jp.ceed.android.mylapslogger.entity.PracticeTrack

@Dao
interface PracticeTrackDao {

    @Query(
        "SELECT p.id, p.track_id, t.name, p.lap_count, p.best_lap, p.start_time, p.end_time, p.total_training_time " +
                "FROM Practice p, Track t WHERE p.track_id = t.id " +
                "AND p.track_id = (:trackId) " +
                "ORDER BY p.best_lap limit 1 "
    )
    fun findBestLapByTrackId(trackId: Int): PracticeTrack

}