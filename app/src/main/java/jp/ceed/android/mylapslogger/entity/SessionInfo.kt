package jp.ceed.android.mylapslogger.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import jp.ceed.android.mylapslogger.util.DateUtil
import java.text.SimpleDateFormat

@Entity
data class SessionInfo(
    @PrimaryKey @ColumnInfo(name = "session_id") var sessionId: Long,
    @ColumnInfo(name = "temperature") var temperature: String? = null,
    @ColumnInfo(name = "pressure") var pressure: String? = null,
    @ColumnInfo(name = "humidity") var humidity: String? = null,
    @ColumnInfo(name = "description") var description: String? = null,
    @ColumnInfo(name = "created") var created: String = DateUtil.createDateTimeString()
)