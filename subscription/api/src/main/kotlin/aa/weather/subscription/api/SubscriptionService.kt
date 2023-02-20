package aa.weather.subscription.api

import kotlinx.coroutines.flow.Flow

interface SubscriptionService {
    fun <T: Subscribable> observe(subscription: Subscription<T>): Flow<T>
}
