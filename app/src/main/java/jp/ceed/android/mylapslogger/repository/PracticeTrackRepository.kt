package jp.ceed.android.mylapslogger.repository

import android.content.Context
import jp.ceed.android.mylapslogger.database.AppDatabase
import jp.ceed.android.mylapslogger.entity.PracticeTrack
import jp.ceed.android.mylapslogger.entity.TotalDistance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PracticeTrackRepository(context: Context) {

    private val practiceTrackDao = AppDatabase.getInstance(context).practiceTrackDao()

    private val trackDao = AppDatabase.getInstance(context).trackDao()


    suspend fun getTotalDistanceList(): List<TotalDistance> {
        val list: List<TotalDistance>
        withContext(Dispatchers.IO){
            list = practiceTrackDao.getTotalDistance()
        }
        return list
    }

    suspend fun findBestLapList(): List<PracticeTrack> {
        val list: MutableList<PracticeTrack> = mutableListOf()
        withContext(Dispatchers.IO){
            val trackList = trackDao.findAll()
            for(track in trackList){
                val record = getBestLapByTrackId(track.id)
                record?.let {
                    list.add(record)
                }
            }
        }
        return list
    }


    private suspend fun getBestLapByTrackId(trackId: Int): PracticeTrack? {
        var entity: PracticeTrack?
        withContext(Dispatchers.IO){
            entity = practiceTrackDao.findBestLapByTrackId(trackId)
        }
        return entity
    }

}