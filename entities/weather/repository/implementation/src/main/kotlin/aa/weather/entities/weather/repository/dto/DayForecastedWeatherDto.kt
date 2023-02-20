package aa.weather.entities.weather.repository.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DayForecastedWeatherDto(
    @SerialName("mintemp_c")
    val minTemperatureCelcius: Double,
    @SerialName("mintemp_f")
    val minTemperatureFahrenheit: Double,
    @SerialName("maxtemp_c")
    val maxTemperatureCelcius: Double,
    @SerialName("maxtemp_f")
    val maxTemperatureFahrenheit: Double,
    val condition: ConditionDto,
)
