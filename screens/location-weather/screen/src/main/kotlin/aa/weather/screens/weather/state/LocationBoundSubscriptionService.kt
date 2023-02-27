package aa.weather.screens.weather.state

import aa.weather.entities.location.LocationFilter
import aa.weather.entities.location.UserLocations
import aa.weather.subscription.api.Subscribable
import aa.weather.subscription.api.Subscription
import aa.weather.subscription.api.addDataFilter
import aa.weather.subscription.api.hasDataFilter
import aa.weather.subscription.service.api.SubscriptionService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest

class LocationBoundSubscriptionService(
    private val delegate: SubscriptionService,
) : SubscriptionService {
    @ExperimentalCoroutinesApi
    override fun <T : Subscribable> observe(subscription: Subscription<T>): Flow<T> =
        subscription
            .takeIf { it.hasDataFilter(LocationFilter::class.java) }
            ?.let(delegate::observe)
            ?: delegate.observe(Subscription(UserLocations::class.java))
                .flatMapLatest { userLocations ->
                    userLocations.current?.id
                        ?.let(::LocationFilter)
                        ?.let { delegate.observe(subscription.addDataFilter(it)) }
                        ?: emptyFlow()
                }
}
