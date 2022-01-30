package jp.ceed.android.mylapslogger.repository

import android.content.Context
import jp.ceed.android.mylapslogger.database.AppDatabase
import jp.ceed.android.mylapslogger.entity.Practice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PracticeRepository(context: Context) {

    private val apiRepository = ApiRepository(context)

    private val practiceDao = AppDatabase.getInstance(context).practiceDao()

    suspend fun savePractice(practice: Practice){
        withContext(Dispatchers.IO){
            practiceDao.save(practice)
        }
    }

    suspend fun getPracticeIdList(): List<Int> {
        val list: ArrayList<Int> = ArrayList()
        withContext(Dispatchers.IO){
            val practiceList = practiceDao.findAll()
            for(entry in practiceList){
                list.add(entry.id)
            }
        }
        return list
    }

}