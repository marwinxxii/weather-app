package aa.weather.entities.weather

import aa.weather.subscription.api.Subscribable

data class LatestWeather(
    val locations: List<LocationLatestWeather>,
) : Subscribable

data class LocationLatestWeather(
    val location: String,
    val conditions: String,
    val temperature: Temperature,
)
