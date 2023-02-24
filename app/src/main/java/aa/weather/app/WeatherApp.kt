package aa.weather.app

import aa.weather.component.di.AppPlugin
import aa.weather.screen.api.ScreenDependenciesLocator
import aa.weather.entities.weather.repository.rest.ApiKey
import aa.weather.screens.location.WeatherScreen
import aa.weather.screens.locations.LocationsScreen
import android.app.Application
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class WeatherApp : Application(), ScreenDependenciesLocator {
    @Inject
    internal lateinit var appPlugins: @JvmSuppressWildcards dagger.Lazy<Map<Class<out AppPlugin>, AppPlugin>>

    internal lateinit var component: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.factory().create(
            context = this,
            ioDispatcher = Dispatchers.IO,
            apiKey = ApiKey(value = BuildConfig.WEATHER_API_KEY),
            screens = mapOf(
                LocationsScreen.Destination::class.java to LocationsScreen.NavigationPlugin,
                WeatherScreen.Destination::class.java to WeatherScreen.NavigationPlugin,
            ),
        )
        component.inject(this)
        FragmentInjector(this).registerSelf(this)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> getOrCreateDependency(dependencyClass: Class<T>): T {
        require(AppPlugin::class.java.isAssignableFrom(dependencyClass))
        return requireNotNull(appPlugins.get()[dependencyClass as Class<out AppPlugin>]) {
            "Requested plugin was not registered: $dependencyClass"
        } as T
    }
}
