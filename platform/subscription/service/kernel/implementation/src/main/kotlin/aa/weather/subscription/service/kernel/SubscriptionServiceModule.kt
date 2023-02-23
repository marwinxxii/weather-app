package aa.weather.subscription.service.kernel

import aa.weather.component.di.AppPlugin
import aa.weather.component.di.AppPluginKey
import aa.weather.subscription.api.Subscribable
import aa.weather.subscription.service.api.SubscriptionService
import aa.weather.subscription.service.plugin.api.SubscribableDataProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
object SubscriptionServiceModule {
    @Provides
    @Singleton
    fun provideSubscriptionService(
        providers: @JvmSuppressWildcards Map<Class<out Subscribable>, SubscribableDataProvider>,
    ): SubscriptionService =
        KernelSubscriptionService(providers.mapKeys { it.key })

    @Provides
    @IntoMap
    @AppPluginKey(SubscriptionService::class)
    fun provideSubscriptionServicePlugin(instance: SubscriptionService): AppPlugin = instance
}
