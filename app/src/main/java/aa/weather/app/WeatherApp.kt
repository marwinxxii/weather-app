package aa.weather.app

import aa.weather.component.di.AppPlugin
import aa.weather.entities.weather.repository.rest.ApiKey
import aa.weather.screen.api.ScreenDependenciesLocator
import aa.weather.screens.locations.LocationsScreen
import aa.weather.screens.weather.PreferencesDestination
import aa.weather.screens.weather.WeatherScreen
import android.app.Application
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class WeatherApp : Application(), ScreenDependenciesLocator {
    @Inject
    internal lateinit var appPlugins: @JvmSuppressWildcards dagger.Lazy<Map<Class<out AppPlugin>, AppPlugin>>

    override fun onCreate() {
        super.onCreate()
        val component = DaggerAppComponent.factory().create(
            context = this,
            ioDispatcher = Dispatchers.IO,
            apiKey = ApiKey(value = BuildConfig.WEATHER_API_KEY),
            screens = mapOf(
                LocationsScreen.Destination::class.java to LocationsScreen.NavigationPlugin,
                WeatherScreen.Destination::class.java to WeatherScreen.NavigationPlugin,
                PreferencesDestination::class.java to LocationsScreen.NavigationPlugin,
            ),
        )
        component.inject(this)
        FragmentInjector(this).registerSelf(this)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : AppPlugin> getOrCreateDependency(dependencyClass: Class<T>): T =
        appPlugins.get()[dependencyClass]
            .let {
                requireNotNull(it) { "Requested plugin was not registered: $dependencyClass" } as T
            }
}
