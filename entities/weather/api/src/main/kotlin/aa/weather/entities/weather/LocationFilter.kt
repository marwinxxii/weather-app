package aa.weather.entities.weather

import aa.weather.subscription.api.SubscriptionFilter

data class LocationFilter(val location: Location) : SubscriptionFilter
