package jp.ceed.android.mylapslogger.repository

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import jp.ceed.android.mylapslogger.model.OpenWeatherResult
import jp.ceed.android.mylapslogger.model.WeatherDataDto
import jp.ceed.android.mylapslogger.util.WeatherResultConverter
import java.io.IOException
import javax.inject.Inject

class WeatherRepository @Inject constructor() {

    private val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    fun getWeatherDataByLocation(lat: Double, lon: Double, callback: (kotlin.Result<WeatherDataDto>) -> Unit) {
        val param = listOf(Pair(LAT, lat), Pair(LON, lon), Pair(APPID, API_KEY))
        BASE_URL.httpGet(param).responseString { request, response, result ->
            when (result) {
                is Result.Success -> {
                    val openWeatherResult = moshi.adapter(OpenWeatherResult::class.java).fromJson(result.get())
                    openWeatherResult?.let {
                        val main = WeatherResultConverter().createWeatherDataDto(it.main)
                        callback(kotlin.Result.success(WeatherDataDto(main)))
                    }
                }
                is Result.Failure -> {
                    callback(kotlin.Result.failure(IOException("unKnown")))
                }
            }
        }.join()
    }

    companion object {

        const val API_KEY = "d176cc1af56c7978700e396a98fc2243"
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/weather"
        const val LAT = "lat"
        const val LON = "lon"
        const val APPID = "APPID"
    }

}