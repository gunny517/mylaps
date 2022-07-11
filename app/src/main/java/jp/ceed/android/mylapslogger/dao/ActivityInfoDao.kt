package jp.ceed.android.mylapslogger.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import jp.ceed.android.mylapslogger.entity.ActivityInfo


@Dao
interface ActivityInfoDao {

	@Query("SELECT * FROM ActivityInfo")
	fun findAll(): List<ActivityInfo>

	@Query("SELECT * FROM ActivityInfo WHERE activity_id = (:activityId)")
	fun findById(activityId: Int): ActivityInfo

	@Insert
	fun insert(sessionInfo: ActivityInfo)

	@Update(entity = ActivityInfo::class)
	fun updateId(sessionInfo: ActivityInfo)

	@Query("SELECT * FROM ActivityInfo WHERE fuel_consumption IS NOT NULL AND fuel_consumption != ''")
	fun findAllFuelConsumptionIsNotNull(): List<ActivityInfo>

}