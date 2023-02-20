package aa.weather.subscription.service.api

import aa.weather.subscription.api.Subscribable
import aa.weather.subscription.api.Subscription
import kotlinx.coroutines.flow.Flow

interface SubscriptionService {
    fun <T: Subscribable> observe(subscription: Subscription<T>): Flow<T>
}
