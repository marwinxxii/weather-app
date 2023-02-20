package aa.weather.entities.weather.repository.dto

import kotlinx.serialization.Serializable

@Serializable
data class ConditionDto(
    val text: String,
    val code: Int,
)
