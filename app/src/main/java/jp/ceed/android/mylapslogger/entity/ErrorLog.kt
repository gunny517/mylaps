package jp.ceed.android.mylapslogger.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ErrorLog(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "stack_trace")
    val stackTrace: String,

    val created: Long
){
    constructor(_stackTrace: String?) :this(
        0,
        _stackTrace ?: UNKNOWN_ERROR,
        System.currentTimeMillis()
    )

    companion object {
        const val UNKNOWN_ERROR = "unknown error"
    }
}