package jp.ceed.android.mylapslogger.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import jp.ceed.android.mylapslogger.entity.SessionInfo


@Dao
interface SessionInfoDao {

	@Query("SELECT * FROM SessionInfo")
	fun findAll(): List<SessionInfo>

	@Query("SELECT * FROM SessionInfo WHERE session_id = (:sessionId)")
	fun findBySessionId(sessionId: Int): SessionInfo

	@Insert
	fun insert(sessionInfo: SessionInfo)

	@Update(entity = SessionInfo::class)
	fun updateBySessionId(sessionInfo: SessionInfo)

}