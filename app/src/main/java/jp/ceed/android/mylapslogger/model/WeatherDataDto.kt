package jp.ceed.android.mylapslogger.model

import jp.ceed.android.mylapslogger.entity.SessionInfo

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