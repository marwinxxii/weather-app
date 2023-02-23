package aa.weather.app

import aa.weather.component.di.AppPlugin
import aa.weather.screen.api.ScreenDependenciesLocator
import aa.weather.entities.weather.repository.rest.ApiKey
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
