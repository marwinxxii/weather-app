package aa.weather.screens.weather

import aa.weather.entities.location.LocationsService
import aa.weather.navigation.navigator.api.Navigator
import aa.weather.screen.api.FragmentScope
import aa.weather.screens.location.plugin.forecast.daily.DailyForecastConfiguration
import aa.weather.screens.location.plugin.forecast.daily.DailyForecastPlugin
import aa.weather.screens.location.plugin.header.HeaderPlugin
import aa.weather.screens.weather.kernel.PluginManager
import aa.weather.screens.weather.kernel.ScreenConfiguration
import aa.weather.screens.weather.state.LocationBoundSubscriptionService
import aa.weather.screens.weather.state.WeatherViewModel
import aa.weather.subscription.service.api.SubscriptionService
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelInitializer
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Provider

@Component(
    modules = [
        WeatherFragmentModule::class,
        PluginsModule::class,
    ],
)
@FragmentScope
internal interface WeatherFragmentComponent {
    fun inject(fragment: WeatherFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance
            @Named("app")
            subscriptionService: SubscriptionService,
            @BindsInstance locationsService: LocationsService,
            @BindsInstance navigator: Navigator,
        ): WeatherFragmentComponent
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
    @FragmentScope
    fun provideLocationBoundService(
        @Named("app") subscriptionService: SubscriptionService,
    ): LocationBoundSubscriptionService =
        LocationBoundSubscriptionService(delegate = subscriptionService)

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
                configuration = DailyForecastConfiguration(daysCount = 14),
            )
            .assemblePlugins()
}
