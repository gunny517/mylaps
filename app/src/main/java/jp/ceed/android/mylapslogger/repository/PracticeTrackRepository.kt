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

    suspend fun getTotalDistanceList(): List<TotalDistance> =
        withContext(dispatcher){
            practiceTrackDao.getTotalDistance()
        }

    suspend fun findBestLapList(): List<PracticeTrack> =
        withContext(dispatcher){
            val list: MutableList<PracticeTrack> = mutableListOf()
            val trackList = trackDao.findAll()
            for(track in trackList){
                val record = getBestLapByTrackId(track.id)
                record?.let {
                    list.add(record)
                }
            }
            list
        }

    private suspend fun getBestLapByTrackId(trackId: Int): PracticeTrack? =
        withContext(dispatcher){
            practiceTrackDao.findBestLapByTrackId(trackId)
        }

    suspend fun getPracticeListByTrack(trackId: Int, sortByBestLap: Boolean): List<PracticeTrack> =
        withContext(dispatcher){
            if(sortByBestLap) {
                practiceTrackDao.getPracticeListByTrackOrderByBestLap(trackId)
            } else {
                practiceTrackDao.getPracticeListByTrackOrderByDate(trackId)
            }
        }
}