package aa.weather.screens.location

import aa.weather.repository.RepositoryModule
import aa.weather.screens.location.kernel.PluginManager
import aa.weather.screens.location.kernel.PluginManagerImpl
import aa.weather.screens.location.plugin.forecast.daily.DailyForecastModule
import aa.weather.screens.location.plugin.header.HeaderModule
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
        // plugins
        HeaderModule::class,
        DailyForecastModule::class,
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

    @Provides
    fun providePluginManager(instance: PluginManagerImpl): PluginManager = instance
}
