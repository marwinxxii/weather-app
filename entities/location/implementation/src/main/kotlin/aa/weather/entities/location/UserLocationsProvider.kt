package aa.weather.entities.location

import aa.weather.subscription.api.Subscribable
import aa.weather.subscription.api.Subscription
import aa.weather.subscription.api.takeIfTopic
import aa.weather.subscription.service.plugin.api.SubscribableDataProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

internal class UserLocationsProvider @Inject constructor(
    private val service: UserLocationsService,
) : SubscribableDataProvider {
    override fun <T : Subscribable> observeData(subscription: Subscription<T>): Flow<T> =
        subscription
            .takeIfTopic(UserLocations::class.java)
            ?.let { service.observeUserLocations() as Flow<T> }
            ?: emptyFlow()
    // else report error
}
