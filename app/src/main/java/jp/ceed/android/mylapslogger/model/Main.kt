package jp.ceed.android.mylapslogger.model


import com.squareup.moshi.Json

data class Main(
    @Json(name = "feels_like")
    val feelsLike: Double,
    @Json(name = "humidity")
    val humidity: Int,
    @Json(name = "pressure")
    val pressure: Int,
    @Json(name = "temp")
    var temp: Double,
    @Json(name = "temp_max")
    var tempMax: Double,
    @Json(name = "temp_min")
    var tempMin: Double
)