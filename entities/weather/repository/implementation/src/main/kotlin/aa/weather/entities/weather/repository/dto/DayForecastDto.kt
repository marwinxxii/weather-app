package aa.weather.entities.weather.repository.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DayForecastDto(
    val date: String,
    @SerialName("day")
    val weather: DayForecastedWeatherDto
)
