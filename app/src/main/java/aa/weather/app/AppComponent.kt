package aa.weather.app

import aa.weather.entities.location.LocationEntityModule
import aa.weather.entities.weather.repository.WeatherEntityModule
import aa.weather.entities.weather.repository.rest.ApiKey
import aa.weather.navigation.navigator.NavigationPlugins
import aa.weather.navigation.navigator.NavigatorModule
import aa.weather.persisted.storage.PersistedStorageModule
import aa.weather.subscription.service.kernel.SubscriptionServiceModule
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        LocationEntityModule::class,
        WeatherEntityModule::class,
        PersistedStorageModule::class,
        SubscriptionServiceModule::class,
        NavigatorModule::class,
    ]
)
internal interface AppComponent {
    fun inject(app: WeatherApp)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            @BindsInstance
            @Named("IO")
            ioDispatcher: CoroutineDispatcher,
            @BindsInstance apiKey: ApiKey,
            @BindsInstance screens: NavigationPlugins,
        ): AppComponent
    }
}
