package aa.weather.entities.weather.repository.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDto(
    @SerialName("temp_c")
    val temperatureCelcius: Double,
    @SerialName("temp_f")
    val temperatureFahrenheit: Double,
    val condition: ConditionDto,
)
