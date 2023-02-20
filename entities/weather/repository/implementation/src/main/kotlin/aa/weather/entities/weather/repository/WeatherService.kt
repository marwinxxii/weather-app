package aa.weather.entities.weather.repository

import aa.weather.entities.weather.repository.dto.LocationCurrentWeatherDto

interface WeatherService {
    suspend fun getCurrentWeather(location: String): LocationCurrentWeatherDto?
}
