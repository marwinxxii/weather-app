package aa.weather.screens.location

import aa.weather.repository.RepositoryModule
import aa.weather.screens.location.kernel.PluginManager
import aa.weather.screens.location.kernel.ScreenConfiguration
import aa.weather.screens.location.plugin.forecast.daily.DailyForecastConfiguration
import aa.weather.screens.location.plugin.forecast.daily.DailyForecastPlugin
import aa.weather.screens.location.plugin.header.HeaderPlugin
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelInitializer
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Component(
    modules = [
        WeatherFragmentModule::class,
        RepositoryModule::class,
        PluginsModule::class,
    ],
)
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
