package jp.ceed.android.mylapslogger.repository

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import org.junit.Test

import org.junit.Assert.fail
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherRepositoryTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()

    private val weatherRepository = WeatherRepository()

    private val locationRepository = LocationRepository(context)

    @Test
    fun getWeatherDataByLocation() {
        locationRepository.getLocation { locationResult ->
            locationResult.onFailure {
                fail()
            }.onSuccess { location ->
                weatherRepository.getWeatherDataByLocation(location.latitude, location.longitude){ weatherResult ->
                    weatherResult.onFailure {
                        fail()
                    }.onSuccess { weatherDto ->
                        assertThat(weatherDto.temperature).isNotEmpty()
                        assertThat(weatherDto.humidity).isNotEmpty()
                        assertThat(weatherDto.pressure).isNotEmpty()
                    }
                }
            }
        }
    }
}