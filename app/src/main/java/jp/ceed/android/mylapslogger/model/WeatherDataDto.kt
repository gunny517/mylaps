package jp.ceed.android.mylapslogger.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherDataDto(
    var temperature: String,
    var pressure: String,
    var humidity: String,
){
   constructor(main: Main) :this(
       temperature = String.format("%.2f", main.temp),
       pressure = main.pressure.toString(),
       humidity = main.humidity.toString()
   )
}