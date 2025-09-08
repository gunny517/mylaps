package jp.ceed.android.mylapslogger.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import jp.ceed.android.mylapslogger.R
import jp.ceed.android.mylapslogger.dto.FinalRatioDto
import jp.ceed.android.mylapslogger.model.FinalRatioItem
import jp.ceed.android.mylapslogger.model.FinalRatioResult
import java.util.Locale
import javax.inject.Inject

class FinalRatioRepository @Inject constructor (
    @ApplicationContext var context: Context
) {

    companion object {
        const val DRIVE_MIN = 10
        const val DRIVE_MAX = 13
        const val DRIVEN_MIN = 80
        const val DRIVEN_MAX = 95
        const val KEY_DRIVE_MIN = "DRIVE_MIN"
        const val KEY_DRIVE_MAX = "DRIVE_MAX"
        const val KEY_DRIVEN_MIN = "DRIVEN_MIN"
        const val KEY_DRIVEN_MAX = "DRIVEN_MAX"
    }

    fun getFinalRatioData(
        paramDriveMin: String?,
        paramDriveMax: String?,
        paramDrivenMin: String?,
        paramDrivenMax: String?
    ): FinalRatioResult {
        val driveMin: Int = paramDriveMin?.toInt() ?: DRIVE_MIN
        val driveMax: Int = paramDriveMax?.toInt() ?: DRIVE_MAX
        val drivenMin: Int = paramDrivenMin?.toInt() ?: DRIVEN_MIN
        val drivenMax: Int = paramDrivenMax?.toInt() ?: DRIVEN_MAX

        val driveList: ArrayList<FinalRatioItem> = ArrayList()
        for(i in driveMin..driveMax){
            driveList.add(createItem(i.toString()))
        }
        val list: ArrayList<FinalRatioItem> = ArrayList()
        for(driven in drivenMin..drivenMax){
            list.add(createItem(driven.toString()))
            for(drive in driveList){
                val ratio: Float = driven.toFloat() / drive.text.toFloat()
                list.add(createItem(String.format(Locale.JAPAN, "%.2f", ratio)))
            }
        }
        driveList.add(0, createItem(""))
        saveCalculateValue(driveMin, driveMax, drivenMin, drivenMax)
        return FinalRatioResult(driveList, list)
    }

    private fun createItem(value: String): FinalRatioItem {
        return FinalRatioItem(
            text = value,
            defaultBgColor = ContextCompat.getColor(context, R.color.grid_cell),
            selectedBgColor = ContextCompat.getColor(context, R.color.grid_cell_selected)
        )
    }

    fun getSavedValue(): FinalRatioDto {
        val prefs = sharedPreferences()
        return FinalRatioDto(
            prefs.getInt(KEY_DRIVE_MIN, DRIVE_MIN),
            prefs.getInt(KEY_DRIVE_MAX, DRIVE_MAX),
            prefs.getInt(KEY_DRIVEN_MIN, DRIVEN_MIN),
            prefs.getInt(KEY_DRIVEN_MAX, DRIVEN_MAX)
        )
    }

    private fun saveCalculateValue(driveMin: Int, driveMax: Int, drivenMin: Int, drivenMax: Int){
        val editor = sharedPreferences().edit()
        editor.putInt(KEY_DRIVE_MIN, driveMin)
            .putInt(KEY_DRIVE_MAX, driveMax)
            .putInt(KEY_DRIVEN_MIN, drivenMin)
            .putInt(KEY_DRIVEN_MAX, drivenMax)
            .apply()
    }

    private fun sharedPreferences(): SharedPreferences {
        return context.getSharedPreferences(FinalRatioRepository.javaClass.simpleName, Context.MODE_PRIVATE)
    }

}