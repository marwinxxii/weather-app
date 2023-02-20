package aa.weather.subscription.kernel

import aa.weather.subscription.api.Subscribable
import aa.weather.subscription.service.api.SubscriptionDataProvider
import aa.weather.subscription.service.api.SubscriptionService
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
object SubscriptionServiceModule {
    @Provides
    @Named("kernel")
    fun provideSubscriptionService(
        providers: @JvmSuppressWildcards Map<Class<*>, SubscriptionDataProvider>,
    ): SubscriptionService =
        KernelSubscriptionService(providers.mapKeys { it.key as Class<out Subscribable> })
}
