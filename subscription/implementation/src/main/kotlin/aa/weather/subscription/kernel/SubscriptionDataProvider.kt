package aa.weather.subscription.kernel

import aa.weather.subscription.api.Subscribable
import aa.weather.subscription.api.Subscription
import kotlinx.coroutines.flow.Flow

interface SubscriptionDataProvider {
    fun <T: Subscribable> observeData(subscription: Subscription<T>): Flow<T>
}
