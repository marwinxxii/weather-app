package aa.weather.entities.weather.repository.dto

import kotlinx.serialization.Serializable

@Serializable
data class LocationCurrentWeatherDto(
    val location: LocationDto,
    val current: WeatherDto,
)
