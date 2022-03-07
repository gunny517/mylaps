package jp.ceed.android.mylapslogger.dao

import androidx.room.*
import jp.ceed.android.mylapslogger.entity.SessionInfo

@Dao
interface SessionInfoDao {

	@Query("SELECT * FROM SessionInfo WHERE session_id = (:sessionId)")
	fun findById(sessionId: Long) : SessionInfo

	@Query("SELECT * FROM SessionInfo")
	fun findAll(): List<SessionInfo>

	@Insert
	fun insert(sessionInfo: SessionInfo)

	@Update(entity = SessionInfo::class)
	fun update(sessionInfo: SessionInfo)

	@Query("DELETE FROM SessionInfo")
	fun deleteAll()

}