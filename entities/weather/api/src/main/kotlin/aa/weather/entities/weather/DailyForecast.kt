package aa.weather.entities.weather

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
    val weather: Set<WeatherCondition>,
    val temperatureMin: Temperature,
    val temperatureMax: Temperature,
)

enum class WeatherCondition {
    SUNNY, CLOUDS, RAIN, SNOW, FOG, WINDY
}

data class Temperature(
    val value: Int,
    val scale: Scale,
) {
    enum class Scale {
        CELCIUS, FARENHEIT
    }
}

data class DailyForecastArguments(
    val daysCount: Int,
) : SubscriptionArguments
