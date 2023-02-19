package aa.weather.repository.api.data

import aa.weather.repository.api.Arguments

data class DailyForecast(
    val days: List<DayForecast>,
) : ManagedData

data class DayForecast(
    val weather: Set<Weather>,
    val temperatureMin: Temperature,
    val temperatureMax: Temperature,
)

enum class Weather {
    SUNNY, CLOUDS, RAIN, SNOW, FOG, WINDY
}

data class Temperature(
    val value: Int,
    val scale: Any,
    val formatted: String,
)

data class DailyForecastArguments(
    val daysCount: Int,
) : Arguments
