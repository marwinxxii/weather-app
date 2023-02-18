package aa.weather.app.screens.weather

import aa.weather.screens.location.plugin.api.DataRepository
import aa.weather.screens.location.plugin.api.LocationWeather
import aa.weather.screens.location.plugin.api.ManagedData
import aa.weather.screens.location.plugin.header.HeaderPluginModule
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelInitializer
import dagger.Component
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Provider

@Component(
    modules = [
        HeaderPluginModule::class,
        WeatherFragmentModule::class,
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
    ) = ViewModelProvider.Factory.from(
        ViewModelInitializer(
            WeatherViewModel::class.java
        ) { weatherViewModel.get() },
    )

    private val locationWeather = LocationWeather("Berlin", "24 C")
    private val repository = object : DataRepository {
        override fun <T : ManagedData> observe(cl: Class<T>): Flow<T> =
            if (cl == LocationWeather::class.java) {
                flowOf(locationWeather as T)
            } else {
                emptyFlow()
            }
    }

    @Provides
    fun provideRepository(): DataRepository = repository
}