package aa.weather.entities.weather.repository.rest

import aa.weather.entities.weather.repository.dto.LocationCurrentWeatherDto
import aa.weather.entities.weather.repository.WeatherService
import aa.weather.entities.weather.repository.dto.DailyForecastDto
import retrofit2.http.GET
import retrofit2.http.Query

internal interface WeatherAPI : WeatherService {
    @GET("current.json")
    override suspend fun getLatestWeather(@Query("q") location: String): LocationCurrentWeatherDto?

    @GET("forecast.json")
    override suspend fun getForecast(
        @Query("q") location: String,
        @Query("days") daysCount: Int
    ): DailyForecastDto?
}
