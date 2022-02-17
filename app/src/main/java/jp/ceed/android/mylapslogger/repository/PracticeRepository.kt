package jp.ceed.android.mylapslogger.repository

import android.content.Context
import jp.ceed.android.mylapslogger.database.AppDatabase
import jp.ceed.android.mylapslogger.entity.Practice
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PracticeRepository(
    context: Context,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    private val practiceDao = AppDatabase.getInstance(context).practiceDao()

    suspend fun savePractice(practice: Practice){
        withContext(dispatcher){
            practiceDao.save(practice)
        }
    }

    suspend fun getPracticeIdList(): List<Int> {
        val list: ArrayList<Int> = ArrayList()
        withContext(dispatcher){
            val practiceList = practiceDao.findAll()
            for(entry in practiceList){
                list.add(entry.id)
            }
        }
        return list
    }

}