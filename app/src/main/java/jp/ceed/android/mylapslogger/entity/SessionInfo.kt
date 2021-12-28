package jp.ceed.android.mylapslogger.entity

import androidx.annotation.ColorInt
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SessionInfo(
	@PrimaryKey @ColumnInfo(name = "session_id") var sessionId: Int,
	@ColumnInfo(name = "temperature") var temperature: String? = null,
	@ColumnInfo(name = "pressure") var pressure: String? = null,
	@ColumnInfo(name = "humidity") var humidity: String? = null,
	@ColumnInfo(name = "description") var description: String? = null
) {
}