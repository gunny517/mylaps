package jp.ceed.android.mylapslogger.util

import jp.ceed.android.mylapslogger.model.OpenWeatherResult

class WeatherResultConverter {

    companion object{
        const val TEMP_DELTA: Double = 273.15

        fun convert(original: OpenWeatherResult){
            original.main.temp -= TEMP_DELTA
            original.main.tempMin -= TEMP_DELTA
            original.main.tempMax -= TEMP_DELTA
        }
    }

}