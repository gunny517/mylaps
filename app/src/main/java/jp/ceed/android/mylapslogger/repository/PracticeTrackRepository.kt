package jp.ceed.android.mylapslogger.repository

import android.content.Context
import jp.ceed.android.mylapslogger.database.AppDatabase
import jp.ceed.android.mylapslogger.entity.PracticeTrack
import jp.ceed.android.mylapslogger.entity.TotalDistance
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PracticeTrackRepository(context: Context,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    private val practiceTrackDao = AppDatabase.getInstance(context).practiceTrackDao()

    private val trackDao = AppDatabase.getInstance(context).trackDao()


    suspend fun getTotalDistanceList(): List<TotalDistance> {
        val list: List<TotalDistance>
        withContext(dispatcher){
            list = practiceTrackDao.getTotalDistance()
        }
        return list
    }

    suspend fun findBestLapList(): List<PracticeTrack> {
        val list: MutableList<PracticeTrack> = mutableListOf()
        withContext(dispatcher){
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
        withContext(dispatcher){
            entity = practiceTrackDao.findBestLapByTrackId(trackId)
        }
        return entity
    }

    suspend fun getPracticeListByTrack(trackId: Int): List<PracticeTrack> {
        var list: List<PracticeTrack>
        withContext(dispatcher){
            list = practiceTrackDao.getPracticeListByTrack(trackId)
        }
        return list
    }

}