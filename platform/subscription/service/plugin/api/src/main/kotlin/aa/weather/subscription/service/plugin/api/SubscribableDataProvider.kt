package aa.weather.subscription.service.plugin.api

import aa.weather.subscription.api.Subscribable
import aa.weather.subscription.api.Subscription
import kotlinx.coroutines.flow.Flow

interface SubscribableDataProvider {
    fun <T : Subscribable> observeData(subscription: Subscription<T>): Flow<T>
}
