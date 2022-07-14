package jp.ceed.android.mylapslogger.repository

import jp.ceed.android.mylapslogger.dao.ActivityInfoTrackDao
import jp.ceed.android.mylapslogger.di.IoDispatcher
import jp.ceed.android.mylapslogger.model.FuelConsumptionListItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ActivityInfoTrackRepository @Inject constructor(
    private val activityInfoTrackDao: ActivityInfoTrackDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getFuelConsumptionList(): List<FuelConsumptionListItem> =
        withContext(dispatcher){
            activityInfoTrackDao.findAsFuelConsumptionList()
                .map{
                    FuelConsumptionListItem(it)
                }
        }
}