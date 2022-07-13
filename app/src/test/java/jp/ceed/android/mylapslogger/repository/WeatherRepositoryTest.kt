package jp.ceed.android.mylapslogger.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import org.junit.Assert.fail
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherRepositoryTest {

    private lateinit var weatherRepository: WeatherRepository

    private lateinit var locationRepository: LocationRepository

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