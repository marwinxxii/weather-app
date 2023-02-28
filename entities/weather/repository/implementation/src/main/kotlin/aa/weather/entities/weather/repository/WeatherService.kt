package aa.weather.entities.weather.repository

import aa.weather.entities.location.LocationID
import aa.weather.entities.weather.repository.dto.DailyForecastDto
import aa.weather.entities.weather.repository.dto.LocationCurrentWeatherDto

interface WeatherService {
    suspend fun getLatestWeather(
        location: LocationID,
        language: String,
    ): LocationCurrentWeatherDto?

    suspend fun getForecast(
        location: LocationID,
        daysCount: Int,
        language: String,
    ): DailyForecastDto?
}
