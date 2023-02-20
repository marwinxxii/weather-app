package aa.weather.entities.weather.repository

import aa.weather.entities.weather.repository.dto.DailyForecastDto
import aa.weather.entities.weather.repository.dto.LocationCurrentWeatherDto

interface WeatherService {
    suspend fun getLatestWeather(location: String): LocationCurrentWeatherDto?

    suspend fun getForecast(location: String, daysCount: Int): DailyForecastDto?
}
