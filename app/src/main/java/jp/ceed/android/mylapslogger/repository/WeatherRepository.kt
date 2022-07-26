package jp.ceed.android.mylapslogger.repository

import io.ktor.client.request.*
import jp.ceed.android.mylapslogger.model.OpenWeatherResult
import jp.ceed.android.mylapslogger.model.WeatherDataDto
import jp.ceed.android.mylapslogger.network.JsonApiKtorClientCreator
import jp.ceed.android.mylapslogger.util.WeatherResultConverter
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val jsonApiKtorClientCreator: JsonApiKtorClientCreator
) {

    suspend fun getWeatherDataByLocationWithKtor(lat: Double, lon: Double): WeatherDataDto? {
        return try {
            val jsonApiClient = jsonApiKtorClientCreator.get()
            val result = jsonApiClient.get<OpenWeatherResult>(BASE_URL) {
                parameter(LAT, lat)
                parameter(LON, lon)
                parameter(APPID, API_KEY)
            }
            val main = WeatherResultConverter().createWeatherDataDto(result.main)
            WeatherDataDto(main)
        } catch (e: Exception) {
            null
        }
    }

    companion object {

        const val API_KEY = "d176cc1af56c7978700e396a98fc2243"
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/weather"
        const val LAT = "lat"
        const val LON = "lon"
        const val APPID = "APPID"
    }

}