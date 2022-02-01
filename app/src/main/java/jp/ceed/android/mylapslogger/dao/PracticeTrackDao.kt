package jp.ceed.android.mylapslogger.dao

import androidx.room.Dao
import androidx.room.Query
import jp.ceed.android.mylapslogger.entity.PracticeTrack
import jp.ceed.android.mylapslogger.entity.TotalDistance

@Dao
interface PracticeTrackDao {

    @Query(
        "SELECT p.id, " +
                "p.track_id, " +
                "t.name, " +
                "p.lap_count, " +
                "p.best_lap, " +
                "p.start_time, " +
                "p.end_time, " +
                "p.display_time, " +
                "p.total_training_time, " +
                "p.activity_id, " +
                "t.length as track_length " +
                "FROM Practice p, Track t WHERE p.track_id = t.id " +
                "AND p.track_id = (:trackId) " +
                "ORDER BY p.best_lap limit 1 "
    )
    fun findBestLapByTrackId(trackId: Int): PracticeTrack?

    @Query(
        "SELECT t.id, t.name, " +
                "t.length, " +
                "sum(p.lap_count) AS total_lap_count, " +
                "(t.length * sum(p.lap_count)) AS distance, " +
                "count(p.id) AS training_count " +
                "FROM Practice p, Track t " +
                "WHERE p.track_id = t.id " +
                "GROUP BY track_id"

    )
    fun getTotalDistance(): List<TotalDistance>

}