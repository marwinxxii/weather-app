package aa.weather.entities.weather.repository.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DayForecastDto(
    @SerialName("date_epoch")
    val timestamp: Int,
    @SerialName("day")
    val weather: DayForecastedWeatherDto
)
