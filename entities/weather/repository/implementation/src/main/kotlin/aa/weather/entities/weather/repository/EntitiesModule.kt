package aa.weather.entities.weather.repository

import aa.weather.entities.weather.DailyForecast
import aa.weather.entities.weather.LatestWeather
import aa.weather.entities.weather.repository.providers.DailyForecastProvider
import aa.weather.entities.weather.repository.providers.LatestWeatherProvider
import aa.weather.entities.weather.repository.rest.ApiKey
import aa.weather.entities.weather.repository.rest.WeatherAPI
import aa.weather.subscription.service.api.SubscriptionDataProvider
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
private object PrivateDataModule {
    @Provides
    fun provideStorage(): PersistedStorage =
        object : PersistedStorage {
            override suspend fun <T> getPersistedData(key: Any): T? {
                return null
            }

            override fun persist(key: Any, data: Any) {
            }
        }

    @Provides
    @IntoMap
    @ClassKey(LatestWeather::class)
    fun provideLatestWeatherProvider(
        weatherService: WeatherService,
        persistedStorage: PersistedStorage,
    ): SubscriptionDataProvider =
        LatestWeatherProvider(
            weatherService,
            persistedStorage,
            ""
        )

    @Provides
    @IntoMap
    @ClassKey(DailyForecast::class)
    fun provideDailyForecastProvider(
        weatherService: WeatherService,
        persistedStorage: PersistedStorage,
    ): SubscriptionDataProvider =
        DailyForecastProvider(
            weatherService,
            persistedStorage,
            ""
        )

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun providerWeatherApi(key: ApiKey): WeatherAPI {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                chain.request()
                    .newBuilder()
                    .url(
                        chain
                            .request()
                            .url
                            .newBuilder()
                            .addQueryParameter("key", key.value)
                            .build()
                    )
                    .build()
                    .let(chain::proceed)
            }
            .build()
        return Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/")
            .addConverterFactory(
                Json { ignoreUnknownKeys = true }
                    .asConverterFactory("application/json".toMediaType())
            )
            .client(okHttpClient)
            .build()
            .create(WeatherAPI::class.java)
    }

    @Provides
    fun provideWeatherService(weatherAPI: WeatherAPI): WeatherService =
        weatherAPI
}

@Module(includes = [PrivateDataModule::class])
interface EntitiesModule
