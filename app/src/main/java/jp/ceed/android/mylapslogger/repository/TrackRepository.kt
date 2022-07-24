package jp.ceed.android.mylapslogger.repository

import jp.ceed.android.mylapslogger.dao.TrackDao
import jp.ceed.android.mylapslogger.entity.Track
import jp.ceed.android.mylapslogger.model.ActivitiesItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
class TrackRepository @Inject constructor(
    private val trackDao: TrackDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun saveAll(activities: List<ActivitiesItem>) =
        withContext(dispatcher){
            val trackIdList = getTrackIdList()
            for(entry in activities){
                if(trackIdList.contains(entry.id)){
                    continue
                }
                trackDao.save(Track(entry))
            }
        }

    private suspend fun getTrackIdList(): List<Int> =
        withContext(dispatcher){
            val list: MutableList<Int> = mutableListOf()
            val trackList = trackDao.findAll()
            for(entry in trackList){
                list.add(entry.id)
            }
            list
        }
}

