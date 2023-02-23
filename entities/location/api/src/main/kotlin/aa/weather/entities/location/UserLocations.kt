package aa.weather.entities.location

import aa.weather.subscription.api.Subscribable

data class UserLocations(
    val current: Location?,
    val all: List<Location>,
) : Subscribable
