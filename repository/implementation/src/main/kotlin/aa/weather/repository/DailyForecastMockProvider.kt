package aa.weather.repository

import aa.weather.repository.api.data.DailyForecast
import aa.weather.repository.api.data.DailyForecastArguments
import aa.weather.repository.api.data.DayForecast
import aa.weather.repository.api.data.Location
import aa.weather.repository.api.data.LocationFilter
import aa.weather.repository.api.data.LocationDailyForecast
import aa.weather.repository.api.data.Temperature
import aa.weather.repository.api.data.Weather
import aa.weather.subscription.api.Subscribable
import aa.weather.subscription.api.Subscription
import aa.weather.subscription.api.takeIfTopic
import aa.weather.subscription.kernel.SubscriptionDataProvider
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
                            Weather.CLOUDS,
                            if (it % 2 == 0) Weather.SUNNY else Weather.RAIN,
                        ),
                        temperatureMin = Temperature(
                            value = 4 + it,
                            scale = "Celcius",
                            formatted = "${4 + it} C"
                        ),
                        temperatureMax = Temperature(
                            value = 8 + it,
                            scale = "Celcius",
                            formatted = "${8 + it} C"
                        ),
                    )
                },
            )
                .let(::listOf)
                .let(::DailyForecast)
        )
}
