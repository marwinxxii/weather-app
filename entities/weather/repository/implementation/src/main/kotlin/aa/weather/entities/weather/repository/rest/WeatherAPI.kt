package aa.weather.entities.weather.repository.rest

import aa.weather.entities.weather.repository.dto.LocationCurrentWeatherDto
import aa.weather.entities.weather.repository.WeatherService
import retrofit2.http.GET
import retrofit2.http.Query

internal interface WeatherAPI : WeatherService {
    @GET("current.json")
    suspend fun getLatestWeather(@Query("q") location: String): LocationCurrentWeatherDto?
}
