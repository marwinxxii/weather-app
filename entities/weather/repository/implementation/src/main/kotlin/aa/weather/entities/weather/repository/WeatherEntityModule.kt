package aa.weather.entities.weather.repository

import aa.weather.entities.weather.DailyForecast
import aa.weather.entities.weather.LatestWeather
import aa.weather.entities.weather.repository.providers.DailyForecastProvider
import aa.weather.entities.weather.repository.providers.LatestWeatherProvider
import aa.weather.entities.weather.repository.rest.WeatherAPI
import aa.weather.network.rest.api.APIFactory
import aa.weather.subscription.service.plugin.api.SubscribableDataProvider
import aa.weather.subscription.service.plugin.api.SubscribableDataProviderKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
private object PrivateDataModule {
    @Provides
    @IntoMap
    @SubscribableDataProviderKey(LatestWeather::class)
    fun provideLatestWeatherProvider(instance: LatestWeatherProvider): SubscribableDataProvider =
        instance

    @Provides
    @IntoMap
    @SubscribableDataProviderKey(DailyForecast::class)
    fun provideDailyForecastProvider(instance: DailyForecastProvider): SubscribableDataProvider =
        instance

    @Provides
    @Singleton
    fun providerWeatherApi(apiFactory: APIFactory): WeatherAPI =
        apiFactory.createRESTAPI(WeatherAPI::class.java)
}

@Module(includes = [PrivateDataModule::class])
interface WeatherEntityModule
