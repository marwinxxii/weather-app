package aa.weather.entities.weather.repository.providers

import aa.weather.entities.location.LocationFilter
import aa.weather.entities.location.LocationID
import aa.weather.entities.weather.LatestWeather
import aa.weather.entities.weather.LocationLatestWeather
import aa.weather.entities.weather.Temperature
import aa.weather.entities.weather.repository.WeatherService
import aa.weather.entities.weather.repository.dto.LocationCurrentWeatherDto
import aa.weather.i18n.api.LocaleProvider
import aa.weather.persisted.storage.api.PersistedStorage
import aa.weather.persisted.storage.api.PersistenceConfiguration
import aa.weather.persisted.storage.api.getOrPersist
import aa.weather.subscription.api.Subscribable
import aa.weather.subscription.api.Subscription
import aa.weather.subscription.api.takeIfTopic
import aa.weather.subscription.service.plugin.api.SubscribableDataProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

internal class LatestWeatherProvider @Inject constructor(
    private val weatherService: WeatherService,
    private val persistedStorage: PersistedStorage,
    private val localeProvider: LocaleProvider,
) : SubscribableDataProvider {
    override fun <T : Subscribable> observeData(subscription: Subscription<T>): Flow<T> =
        flow {
            subscription
                .takeIfTopic(LatestWeather::class.java)
                ?.dataFilters
                ?.filterIsInstance<LocationFilter>()
                ?.forEach {
                    // type check is ensured by generic
                    @Suppress("UNCHECKED_CAST")
                    emitAll(requestData(it.location) as Flow<T>)
                }
            // TODO else report error
        }

    private fun requestData(location: LocationID): Flow<LatestWeather> =
        flow {
            persistedStorage.getOrPersist(
                PersistenceConfiguration(
                    key = "latest-weather-${location.value}",
                    ttl = TimeUnit.HOURS.toMillis(1L),
                ),
                LocationCurrentWeatherDto.serializer(),
                LocationCurrentWeatherDto.serializer(),
            ) {
                weatherService.getLatestWeather(location, localeProvider.locale.language)
            }
                ?.let {
                    LocationLatestWeather(
                        location = it.location.name,
                        conditions = it.current.condition.text,
                        temperature = it.current.temperatureCelcius
                            .toTemperatureModel(Temperature.Scale.CELCIUS),
                    )
                }
                ?.let(::listOf)
                ?.let(::LatestWeather)
                ?.also { emit(it) }
        }
}
