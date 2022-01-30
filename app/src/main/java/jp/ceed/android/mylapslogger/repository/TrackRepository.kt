package jp.ceed.android.mylapslogger.repository

import android.content.Context
import jp.ceed.android.mylapslogger.database.AppDatabase
import jp.ceed.android.mylapslogger.entity.Track
import jp.ceed.android.mylapslogger.model.ActivitiesItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TrackRepository(context: Context) {

    private val trackDao = AppDatabase.getInstance(context).trackDao()

    suspend fun saveAll(activities: List<ActivitiesItem>){
        val trackIdList = getTrackIdList()
        withContext(Dispatchers.IO){
            for(entry in activities){
                if(trackIdList.contains(entry.id)){
                    continue
                }
                trackDao.save(Track(entry))
            }
        }
    }

    private suspend fun getTrackIdList(): List<Int> {
        val list: MutableList<Int> = mutableListOf()
        withContext(Dispatchers.IO){
            val trackList = trackDao.findAll()
            for(entry in trackList){
                list.add(entry.id)
            }
        }
        return list
    }

}