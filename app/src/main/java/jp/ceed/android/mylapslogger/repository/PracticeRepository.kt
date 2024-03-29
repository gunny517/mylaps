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

    suspend fun findById(id: Int): Practice =
        withContext(dispatcher){
            practiceDao.findById(id)
        }

    suspend fun savePractice(practice: Practice) =
        withContext(dispatcher){
            practiceDao.save(practice)
        }

    suspend fun getPracticeIdList(): List<Int> =
        withContext(dispatcher){
            val list: ArrayList<Int> = ArrayList()
            val practiceList = practiceDao.findAll()
            for(entry in practiceList){
                list.add(entry.id)
            }
            list
        }
}