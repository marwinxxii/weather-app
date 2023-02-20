package aa.weather.entities.weather.repository.providers

import aa.weather.entities.weather.LatestWeather
import aa.weather.entities.weather.Location
import aa.weather.entities.weather.LocationFilter
import aa.weather.entities.weather.LocationLatestWeather
import aa.weather.entities.weather.repository.PersistedStorage
import aa.weather.entities.weather.repository.WeatherService
import aa.weather.entities.weather.repository.dto.LocationCurrentWeatherDto
import aa.weather.subscription.api.Subscribable
import aa.weather.subscription.api.Subscription
import aa.weather.subscription.api.takeIfTopic
import aa.weather.subscription.service.api.SubscriptionDataProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class LatestWeatherProvider(
    private val weatherService: WeatherService,
    private val persistedStorage: PersistedStorage,
    private val userPreferencesProvider: Any,
) : SubscriptionDataProvider {
    override fun <T : Subscribable> observeData(subscription: Subscription<T>): Flow<T> =
        flow {
            subscription
                .takeIfTopic(LatestWeather::class.java)
                ?.dataFilters
                ?.filterIsInstance<LocationFilter>()
                ?.forEach { emitAll(requestData(it.location) as Flow<T>) }
            // else report error
        }

    private fun requestData(location: Location): Flow<LatestWeather> =
        flow {
            persistedStorage.getPersistedData<LocationCurrentWeatherDto>(location)
                .let { persisted ->
                    // check if fresh enough
                    persisted ?: weatherService.getCurrentWeather(location.name).also {
                        if (it != null) {
                            persistedStorage.persist(key = location, data = it)
                        }
                    }
                }
                ?.let {
                    LocationLatestWeather(
                        location = it.location.name,
                        conditions = it.current.condition.text,
                        temperature = it.current.temperatureCelcius.toString() + " C",
                    )
                }
                ?.let(::listOf)
                ?.let(::LatestWeather)
                ?.also { emit(it) }
        }
}
