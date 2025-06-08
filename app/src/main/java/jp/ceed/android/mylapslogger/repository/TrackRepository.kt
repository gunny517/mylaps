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

    suspend fun saveAll(activities: List<ActivitiesItem>, created: Long) =
        withContext(dispatcher){
            activities.filter { getTrackIdList().contains(it.locationId).not() }.forEach {
                trackDao.save(Track(it, created))
            }
        }

    private suspend fun getTrackIdList(): List<Int> =
        withContext(dispatcher){
            trackDao.findAll().map { it.id }
        }
}

