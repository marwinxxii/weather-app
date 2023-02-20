package aa.weather.subscription.kernel

import aa.weather.subscription.api.Subscribable
import aa.weather.subscription.api.Subscription
import aa.weather.subscription.service.api.SubscriptionDataProvider
import aa.weather.subscription.service.api.SubscriptionService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

internal class KernelSubscriptionService(
    private val providers: Map<Class<out Subscribable>, SubscriptionDataProvider>,
) : SubscriptionService {
    override fun <T : Subscribable> observe(subscription: Subscription<T>): Flow<T> =
        providers[subscription.topicClass]
            ?.observeData(subscription)
            ?: emptyFlow()
}
