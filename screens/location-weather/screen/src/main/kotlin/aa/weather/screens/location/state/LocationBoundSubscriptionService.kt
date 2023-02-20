package aa.weather.screens.location.state

import aa.weather.repository.api.data.Location
import aa.weather.repository.api.data.LocationFilter
import aa.weather.subscription.api.Subscribable
import aa.weather.subscription.api.Subscription
import aa.weather.subscription.api.SubscriptionService
import aa.weather.subscription.api.addDataFilter
import aa.weather.subscription.api.hasDataFilter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest

class LocationBoundSubscriptionService(
    private val delegate: SubscriptionService,
) : SubscriptionService {
    private val locationFlow = MutableStateFlow<Location?>(null)

    @ExperimentalCoroutinesApi
    override fun <T : Subscribable> observe(subscription: Subscription<T>): Flow<T> =
        subscription
            .takeIf { it.hasDataFilter(LocationFilter::class.java) }
            ?.let(delegate::observe)
            ?: locationFlow.flatMapLatest {
                if (it != null) {
                    delegate.observe(subscription.addDataFilter(LocationFilter(it)))
                } else {
                    emptyFlow()
                }
            }

    fun setLocation(currentLocation: Location) {
        locationFlow.tryEmit(currentLocation)
    }
}
