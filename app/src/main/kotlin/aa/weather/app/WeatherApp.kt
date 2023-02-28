package aa.weather.app

import aa.weather.component.di.AppPlugin
import aa.weather.network.rest.api.APIConfiguration
import aa.weather.screen.api.ScreenDependenciesLocator
import android.app.Application

class WeatherApp : Application(), ScreenDependenciesLocator {
    internal val delegate = AppDelegate(this)

    override fun onCreate() {
        super.onCreate()
        delegate.injectDependencies(
            APIConfiguration(
                key = BuildConfig.WEATHER_API_KEY,
                baseUrl = BuildConfig.API_BASE_URL,
            )
        )
    }

    override fun <T : AppPlugin> getOrCreateDependency(dependencyClass: Class<T>): T =
        delegate.getOrCreateDependency(dependencyClass)
}
