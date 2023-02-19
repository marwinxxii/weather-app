package aa.weather.app.screens.weather

import aa.weather.repository.RepositoryModule
import aa.weather.screens.location.plugin.header.HeaderPluginModule
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelInitializer
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Component(
    modules = [
        HeaderPluginModule::class,
        WeatherFragmentModule::class,
        RepositoryModule::class,
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
}
