package aa.weather.entities.location

import aa.weather.subscription.api.SubscriptionFilter

data class LocationFilter(val location: LocationID) : SubscriptionFilter
