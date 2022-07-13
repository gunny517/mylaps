package jp.ceed.android.mylapslogger.repository

import jp.ceed.android.mylapslogger.dao.PracticeTrackDao
import jp.ceed.android.mylapslogger.dao.TrackDao
import jp.ceed.android.mylapslogger.di.IoDispatcher
import jp.ceed.android.mylapslogger.entity.PracticeTrack
import jp.ceed.android.mylapslogger.entity.TotalDistance
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PracticeTrackRepository @Inject constructor (
    private val practiceTrackDao: PracticeTrackDao,
    private val trackDao: TrackDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

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