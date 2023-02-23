package aa.weather.entities.weather.repository.providers

import aa.weather.entities.location.LocationFilter
import aa.weather.entities.location.LocationID
import aa.weather.entities.weather.DailyForecast
import aa.weather.entities.weather.DailyForecastArguments
import aa.weather.entities.weather.DayForecast
import aa.weather.entities.weather.LocationDailyForecast
import aa.weather.entities.weather.Temperature
import aa.weather.entities.weather.repository.WeatherService
import aa.weather.entities.weather.repository.dto.DailyForecastDto
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

internal class DailyForecastProvider(
    private val weatherService: WeatherService,
    private val persistedStorage: PersistedStorage,
    private val userPreferencesProvider: Any,
) : SubscribableDataProvider {
    override fun <T : Subscribable> observeData(subscription: Subscription<T>): Flow<T> =
        flow {
            subscription
                .takeIfTopic(DailyForecast::class.java)
                ?.takeIf { it.arguments is DailyForecastArguments }
                ?.dataFilters
                ?.filterIsInstance<LocationFilter>()
                ?.forEach {
                    emitAll(
                        requestData(
                            it.location,
                            subscription.arguments as DailyForecastArguments
                        ) as Flow<T>
                    )
                }
            // else report error
        }

    private fun requestData(
        location: LocationID,
        args: DailyForecastArguments,
    ): Flow<DailyForecast> =
        flow {
            persistedStorage.getOrPersist(
                PersistenceConfiguration(
                    key = "daily-forecast-${location.value}",
                    ttl = TimeUnit.HOURS.toMillis(4L),
                ),
                DailyForecastDto.serializer(),
                DailyForecastDto.serializer(),
            ) {
                weatherService.getForecast(location.value, args.daysCount)
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
