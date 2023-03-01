package aa.weather.app

import aa.weather.component.di.AppPlugin
import aa.weather.network.rest.api.APIConfiguration
import aa.weather.screen.api.ScreenDependenciesLocator
import android.app.Application
import android.util.Log

class WeatherApp : Application(), ScreenDependenciesLocator {
    internal val delegate = AppDelegate(this)

    override fun onCreate() {
        super.onCreate()
        setExceptionHandler()
        require(BuildConfig.WEATHER_API_KEY.isNotBlank())
        delegate.injectDependencies(
            APIConfiguration(
                key = BuildConfig.WEATHER_API_KEY,
                baseUrl = BuildConfig.API_BASE_URL,
            )
        )
    }

    private fun setExceptionHandler() {
        val delegate = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            Log.e("WeatherApp", "Uncaught exception in $t", e)
            if (delegate != null) {
                delegate.uncaughtException(t, e)
            } else {
                throw e
            }
        }
    }

    override fun <T : AppPlugin> getOrCreateDependency(dependencyClass: Class<T>): T =
        delegate.getOrCreateDependency(dependencyClass)
}
