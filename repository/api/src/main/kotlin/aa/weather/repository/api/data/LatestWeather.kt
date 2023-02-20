package aa.weather.repository.api.data

import aa.weather.subscription.api.Subscribable

data class LatestWeather(
    val locations: List<LocationLatestWeather>,
) : Subscribable

data class LocationLatestWeather(
    val location: Location,
    val weather: Set<Weather>,
    val currentTemperature: Temperature,
)
