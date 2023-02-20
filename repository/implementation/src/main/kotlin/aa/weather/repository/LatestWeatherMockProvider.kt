package aa.weather.repository

import aa.weather.repository.api.data.LatestWeather
import aa.weather.repository.api.data.Location
import aa.weather.repository.api.data.LocationFilter
import aa.weather.repository.api.data.LocationLatestWeather
import aa.weather.repository.api.data.Temperature
import aa.weather.repository.api.data.Weather
import aa.weather.subscription.api.Subscribable
import aa.weather.subscription.api.Subscription
import aa.weather.subscription.api.takeIfTopic
import aa.weather.subscription.kernel.SubscriptionDataProvider
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

object LatestWeatherMockProvider : SubscriptionDataProvider {
    override fun <T : Subscribable> observeData(subscription: Subscription<T>): Flow<T> =
        flow {
            subscription
                .takeIfTopic(LatestWeather::class.java)
                ?.dataFilters
                ?.filterIsInstance<LocationFilter>()
                ?.forEach { emitAll(mockData(it.location) as Flow<T>) }
            // else report error
        }

    private fun mockData(location: Location): Flow<LatestWeather> =
        flow {
            val initial = LocationLatestWeather(
                location = location,
                weather = setOf(Weather.CLOUDS),
                currentTemperature = Temperature(24, "C", formatted = "24 C"),
            )
            emit(LatestWeather(listOf(initial)))
            repeat(100) { i ->
                currentCoroutineContext().ensureActive()
                println("Next weather: $i")
                delay(5000)
                emit(
                    initial.copy(
                        currentTemperature = initial.currentTemperature.copy(
                            value = 24 + i,
                            formatted = "${24 + i} C",
                        ),
                    )
                        .let(::listOf)
                        .let(::LatestWeather)
                )
            }
        }
}
