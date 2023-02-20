package aa.weather.repository.api.data

import aa.weather.subscription.api.Subscribable
import aa.weather.subscription.api.SubscriptionArguments

data class DailyForecast(
    val locations: List<LocationDailyForecast>,
) : Subscribable

data class LocationDailyForecast(
    val location: Location,
    val days: List<DayForecast>,
)

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
) : SubscriptionArguments
