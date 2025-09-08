package jp.ceed.android.mylapslogger.model

import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.MutableLiveData

data class FinalRatioItem(
    val text: String,
    var isSelected: Boolean = false,
    val selectedBgColor: Int,
    val defaultBgColor: Int,
    var background: MutableLiveData<Drawable> = MutableLiveData(defaultBgColor.toDrawable())
) {

    fun changeSelected() {
        isSelected = !isSelected
        setBgColor()
    }

    fun setBgColor() {
        this.background.value = if (isSelected) {
            selectedBgColor.toDrawable()
        } else {
            defaultBgColor.toDrawable()
        }
    }

    fun reset() {
        isSelected = false
        setBgColor()
    }

}
