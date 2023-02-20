package aa.weather.screens.location

import aa.weather.repository.DailyForecastMockProvider
import aa.weather.repository.LatestWeatherMockProvider
import aa.weather.repository.api.data.DailyForecast
import aa.weather.repository.api.data.LatestWeather
import aa.weather.screens.location.state.LocationBoundSubscriptionService
import aa.weather.screens.location.kernel.PluginManager
import aa.weather.screens.location.kernel.ScreenConfiguration
import aa.weather.screens.location.plugin.forecast.daily.DailyForecastConfiguration
import aa.weather.screens.location.plugin.forecast.daily.DailyForecastPlugin
import aa.weather.screens.location.plugin.header.HeaderPlugin
import aa.weather.subscription.api.SubscriptionService
import aa.weather.subscription.kernel.KernelSubscriptionService
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelInitializer
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Provider
import javax.inject.Singleton

@Component(
    modules = [
        WeatherFragmentModule::class,
        PluginsModule::class,
    ],
)
@Singleton
interface WeatherFragmentComponent {
    fun inject(fragment: WeatherFragment)

    @Component.Factory
    interface Factory {
        fun create(): WeatherFragmentComponent
    }
}

@Module
private object WeatherFragmentModule {
    @Provides
    fun provideVMFactory(
        weatherViewModel: Provider<WeatherViewModel>,
    ): ViewModelProvider.Factory = ViewModelProvider.Factory.from(
        ViewModelInitializer(WeatherViewModel::class.java) { weatherViewModel.get() },
    )

    @Provides
    @Singleton
    fun provideLocationBoundService(): LocationBoundSubscriptionService =
        LocationBoundSubscriptionService(
            delegate = KernelSubscriptionService(
                mapOf(
                    LatestWeather::class.java to LatestWeatherMockProvider,
                    DailyForecast::class.java to DailyForecastMockProvider,
                )
            )
        )

    @Provides
    fun provideSubscriptionService(instance: LocationBoundSubscriptionService): SubscriptionService =
        instance
}

@Module
private object PluginsModule {
    @Provides
    fun provideScreenPlugins(
        headerPlugin: HeaderPlugin,
        dailyForecastPlugin: DailyForecastPlugin,
    ): PluginManager =
        ScreenConfiguration.builder()
            .registerPlugin(
                key = "header",
                plugin = headerPlugin,
                configuration = null,
            )
            .registerPlugin(
                key = "next days forecast",
                plugin = dailyForecastPlugin,
                configuration = DailyForecastConfiguration(daysCount = 50),
            )
            .assemblePlugins()
}
