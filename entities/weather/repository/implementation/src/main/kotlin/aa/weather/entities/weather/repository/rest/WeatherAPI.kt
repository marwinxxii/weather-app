package aa.weather.entities.weather.repository.rest

import aa.weather.entities.location.LocationID
import aa.weather.entities.weather.repository.dto.DailyForecastDto
import aa.weather.entities.weather.repository.dto.LocationCurrentWeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

internal interface WeatherAPI {
    @GET("current.json")
    suspend fun getLatestWeather(
        @Query("q") location: LocationID,
        @Query("lang") language: String,
    ): LocationCurrentWeatherDto?

    @GET("forecast.json")
    suspend fun getForecast(
        @Query("q") location: LocationID,
        @Query("days") daysCount: Int,
        @Query("lang") language: String,
    ): DailyForecastDto?
}
