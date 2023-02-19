package aa.weather.repository

import aa.weather.repository.api.Arguments
import aa.weather.repository.api.DataRepository
import aa.weather.repository.api.data.DailyForecast
import aa.weather.repository.api.data.DailyForecastArguments
import aa.weather.repository.api.data.DayForecast
import aa.weather.repository.api.data.LocationWeather
import aa.weather.repository.api.data.ManagedData
import aa.weather.repository.api.data.Temperature
import aa.weather.repository.api.data.Weather
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

internal class MockRepository : DataRepository {
    // type is checked by compiler
    @Suppress("UNCHECKED_CAST")
    override fun <T : ManagedData> observe(cl: Class<T>, arguments: Arguments?): Flow<T> =
        when (cl) {
            LocationWeather::class.java -> locationWeatherMock()
            DailyForecast::class.java -> dailyForecastMock(arguments as DailyForecastArguments?)
            else -> emptyFlow()
        } as Flow<T>

    private fun locationWeatherMock(): Flow<LocationWeather> =
        flow {
            val initial = LocationWeather("Berlin", "24 C")
            emit(initial)
            repeat(100) {
                currentCoroutineContext().ensureActive()
                delay(5000)
                emit(initial.copy(temperature = "${24 + it} C"))
            }
        }

    private fun dailyForecastMock(arguments: DailyForecastArguments?) =
        flowOf(
            DailyForecast(
                days = List(arguments?.daysCount ?: 10) {
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
        )
}
