package aa.weather.entities.weather.repository

import aa.weather.entities.weather.DailyForecast
import aa.weather.entities.weather.DailyForecastArguments
import aa.weather.entities.weather.DayForecast
import aa.weather.entities.weather.Location
import aa.weather.entities.weather.LocationDailyForecast
import aa.weather.entities.weather.LocationFilter
import aa.weather.entities.weather.Temperature
import aa.weather.entities.weather.WeatherCondition
import aa.weather.subscription.api.Subscribable
import aa.weather.subscription.api.Subscription
import aa.weather.subscription.api.takeIfTopic
import aa.weather.subscription.service.api.SubscriptionDataProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

object DailyForecastMockProvider : SubscriptionDataProvider {
    override fun <T : Subscribable> observeData(subscription: Subscription<T>): Flow<T> =
        flow {
            subscription
                .takeIfTopic(DailyForecast::class.java)
                ?.takeIf { it.arguments is DailyForecastArguments }
                ?.dataFilters
                ?.filterIsInstance<LocationFilter>()
                ?.forEach {
                    emitAll(
                        mockData(
                            it.location,
                            subscription.arguments as DailyForecastArguments,
                        ) as Flow<T>
                    )
                }
            // else report error
        }

    private fun mockData(
        location: Location,
        arguments: DailyForecastArguments,
    ): Flow<DailyForecast> =
        flowOf(
            LocationDailyForecast(
                location = location,
                days = List(arguments.daysCount) {
                    DayForecast(
                        weather = setOf(
                            WeatherCondition.CLOUDS,
                            if (it % 2 == 0) WeatherCondition.SUNNY else WeatherCondition.RAIN,
                        ),
                        temperatureMin = Temperature(
                            value = 4 + it,
                            scale = Temperature.Scale.CELCIUS,
                        ),
                        temperatureMax = Temperature(
                            value = 8 + it,
                            scale = Temperature.Scale.CELCIUS,
                        ),
                    )
                },
            )
                .let(::listOf)
                .let(::DailyForecast)
        )
}
