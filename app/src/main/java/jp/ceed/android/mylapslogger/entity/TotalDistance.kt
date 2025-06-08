package jp.ceed.android.mylapslogger.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.util.Locale

@Entity
data class TotalDistance(
    val id: Int,
    val name: String,
    val length: Int,
    @ColumnInfo(name = "total_lap_count") val totalLapCount: Int,
    val distance: Int,
    @ColumnInfo(name = "training_count") val trainingCount: Int
){
    fun displayDistance(): String{
        val fDistance: Float = distance / 1000F
        return String.format(Locale.JAPAN, "%,.2f", fDistance)
    }

    fun displayLapCount(): String {
        return String.format(Locale.JAPAN, "%,d", totalLapCount)
    }
}