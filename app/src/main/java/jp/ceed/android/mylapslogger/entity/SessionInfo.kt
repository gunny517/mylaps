package jp.ceed.android.mylapslogger.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SessionInfo(
	@PrimaryKey @ColumnInfo(name = "session_id") val sessionId: Int,
	var description: String
) {
}