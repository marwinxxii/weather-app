package aa.weather.entities.weather.repository

import aa.weather.entities.location.LocationID
import aa.weather.entities.weather.repository.dto.DailyForecastDto
import aa.weather.entities.weather.repository.dto.LocationCurrentWeatherDto
import aa.weather.entities.weather.repository.rest.WeatherAPI
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

internal class WeatherService @Inject constructor(
    private val weatherAPI: WeatherAPI,
) {
    // these are expected exceptions
    @Suppress("SwallowedException")
    suspend fun getLatestWeather(
        location: LocationID,
        language: String,
    ): LocationCurrentWeatherDto? =
        try {
            weatherAPI.getLatestWeather(location, language)
        } catch (e: IOException) {
            null
        } catch (e: HttpException) {
            null
        }

    // these are expected exceptions
    @Suppress("SwallowedException")
    suspend fun getForecast(
        location: LocationID,
        daysCount: Int,
        language: String,
    ): DailyForecastDto? =
        try {
            weatherAPI.getForecast(location, daysCount, language)
        } catch (e: IOException) {
            null
        } catch (e: HttpException) {
            null
        }
}
