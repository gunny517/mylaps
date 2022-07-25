package jp.ceed.android.mylapslogger.util

import jp.ceed.android.mylapslogger.model.Main

class WeatherResultConverter {

    fun createWeatherDataDto(org: Main): Main{
        return Main(
            temp = org.temp - TEMP_DELTA,
            tempMin = org.tempMin - TEMP_DELTA,
            tempMax = org.tempMax - TEMP_DELTA,
            humidity = org.humidity,
            pressure = org.pressure,
            feelsLike = org.feelsLike,
            seaLevel = org.seaLevel,
            grandLevel = org.grandLevel,
        )
    }

    companion object{
        const val TEMP_DELTA: Double = 273.15
    }

}