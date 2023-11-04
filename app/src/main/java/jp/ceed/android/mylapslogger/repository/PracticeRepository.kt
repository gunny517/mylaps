package jp.ceed.android.mylapslogger.repository

import jp.ceed.android.mylapslogger.dao.PracticeDao
import jp.ceed.android.mylapslogger.entity.Practice
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PracticeRepository @Inject constructor (
    private val practiceDao: PracticeDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    suspend fun findByActivityId(activityId: Long): Practice =
        withContext(dispatcher){
            practiceDao.findByActivityId(activityId)
        }

    suspend fun savePractice(practice: Practice) =
        withContext(dispatcher){
            practiceDao.save(practice)
        }

    suspend fun getPracticeIdList(): List<Long> =
        withContext(dispatcher){
            val list: ArrayList<Long> = ArrayList()
            val practiceList = practiceDao.findAll()
            for(entry in practiceList){
                list.add(entry.activityId)
            }
            list
        }
}