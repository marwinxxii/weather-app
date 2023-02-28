package aa.weather.app

import aa.weather.component.di.AppPlugin
import aa.weather.i18n.api.LocaleProvider
import aa.weather.network.rest.api.APIConfiguration
import aa.weather.screen.api.ScreenDependenciesLocator
import aa.weather.screens.locations.LocationsScreen
import aa.weather.screens.weather.PreferencesDestination
import aa.weather.screens.weather.WeatherScreen
import android.annotation.SuppressLint
import android.app.Application
import kotlinx.coroutines.Dispatchers
import java.util.Locale
import javax.inject.Inject

internal class AppDelegate(private val app: Application) : ScreenDependenciesLocator {
    @Inject
    internal lateinit var appPlugins: @JvmSuppressWildcards dagger.Lazy<Map<Class<out AppPlugin>, AppPlugin>>

    fun injectDependencies(apiConfiguration: APIConfiguration, locale: Locale) {
        val component = DaggerAppComponent.factory().create(
            context = app,
            ioDispatcher = Dispatchers.IO,
            apiConfiguration = { apiConfiguration },
            screens = mapOf(
                LocationsScreen.Destination::class.java to LocationsScreen.NavigationPlugin,
                WeatherScreen.Destination::class.java to WeatherScreen.NavigationPlugin,
                PreferencesDestination::class.java to LocationsScreen.NavigationPlugin,
            ),
            localeProvider = object : LocaleProvider {
                @SuppressLint("ConstantLocale")
                override val locale = locale
            },
        )
        component.inject(this)
        FragmentInjector(this).registerSelf(app)
    }

    fun injectDependencies(apiConfiguration: APIConfiguration) {
        injectDependencies(
            apiConfiguration,
            locale = app.resources
                .configuration
                .locales
                .takeIf { !it.isEmpty }
                ?.get(0)
                ?: Locale.getDefault(),
        )
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : AppPlugin> getOrCreateDependency(dependencyClass: Class<T>): T =
        appPlugins.get()[dependencyClass]
            .let {
                requireNotNull(it) { "Requested plugin was not registered: $dependencyClass" } as T
            }
}
