package aa.weather.entities.weather.repository.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyForecastDto(
    val location: LocationDto,
    val forecast: ForecastDaysDto,
)

@Serializable
data class ForecastDaysDto(
    @SerialName("forecastday")
    val days: List<DayForecastDto>,
)
