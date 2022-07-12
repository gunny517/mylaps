package jp.ceed.android.mylapslogger.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import jp.ceed.android.mylapslogger.dao.ActivityInfoTrackDao
import jp.ceed.android.mylapslogger.database.AppDatabase
import jp.ceed.android.mylapslogger.di.IoDispatcher
import jp.ceed.android.mylapslogger.model.FuelConsumptionListItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ActivityInfoTrackRepository @Inject constructor(
    @ApplicationContext val context: Context,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    val dao: ActivityInfoTrackDao = AppDatabase.getInstance(context).activityInfoTrack()

    suspend fun getFuelConsumptionList(): List<FuelConsumptionListItem> =
        withContext(dispatcher){
            dao.findAsFuelConsumptionList()
                .map{
                    FuelConsumptionListItem(it)
                }
        }
}