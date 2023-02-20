package aa.weather.entities.weather.repository.providers

import aa.weather.entities.weather.DailyForecast
import aa.weather.entities.weather.DailyForecastArguments
import aa.weather.entities.weather.DayForecast
import aa.weather.entities.weather.Location
import aa.weather.entities.weather.LocationDailyForecast
import aa.weather.entities.weather.LocationFilter
import aa.weather.entities.weather.Temperature
import aa.weather.entities.weather.repository.PersistedStorage
import aa.weather.entities.weather.repository.WeatherService
import aa.weather.entities.weather.repository.dto.DailyForecastDto
import aa.weather.subscription.api.Subscribable
import aa.weather.subscription.api.Subscription
import aa.weather.subscription.api.takeIfTopic
import aa.weather.subscription.service.api.SubscriptionDataProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class DailyForecastProvider(
    private val weatherService: WeatherService,
    private val persistedStorage: PersistedStorage,
    private val userPreferencesProvider: Any,
) : SubscriptionDataProvider {
    override fun <T : Subscribable> observeData(subscription: Subscription<T>): Flow<T> =
        flow {
            subscription
                .takeIfTopic(DailyForecast::class.java)
                ?.takeIf { it.arguments is DailyForecastArguments }
                ?.dataFilters
                ?.filterIsInstance<LocationFilter>()
                ?.forEach {
                    emitAll(requestData(it.location, subscription.arguments as DailyForecastArguments) as Flow<T>)
                }
            // else report error
        }

    private fun requestData(location: Location, args: DailyForecastArguments): Flow<DailyForecast> =
        flow {
            persistedStorage.getPersistedData<DailyForecastDto>(location)
                .let { persisted ->
                    // check if fresh enough
                    persisted ?: weatherService.getForecast(location.name, args.daysCount).also {
                        if (it != null) {
                            persistedStorage.persist(key = location, data = it)
                        }
                    }
                }
                ?.let { forecast ->
                    LocationDailyForecast(
                        location = forecast.location.name,
                        days = forecast.forecast.days.map {
                            DayForecast(
                                date = it.date,
                                weatherConditions = it.weather.condition.text,
                                temperatureMin = Temperature(
                                    value = it.weather.minTemperatureCelcius.toInt(),
                                    scale = Temperature.Scale.CELCIUS
                                ),
                                temperatureMax = Temperature(
                                    value = it.weather.maxTemperatureCelcius.toInt(),
                                    scale = Temperature.Scale.CELCIUS
                                ),
                            )
                        },
                    )
                }
                ?.let(::listOf)
                ?.let(::DailyForecast)
                ?.also { emit(it) }
        }
}
