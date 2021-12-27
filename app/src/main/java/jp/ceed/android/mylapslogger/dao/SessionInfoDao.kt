package jp.ceed.android.mylapslogger.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import jp.ceed.android.mylapslogger.entity.SessionInfo

@Dao
interface SessionInfoDao {

	@Query("SELECT * FROM SessionInfo WHERE session_id = (:sessionId)")
	fun findById(sessionId: Int) : SessionInfo

	@Insert
	fun insert(sessionInfo: SessionInfo)

	@Update(entity = SessionInfo::class)
	fun update(sessionInfo: SessionInfo)

}