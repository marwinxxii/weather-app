package aa.weather.repository

import aa.weather.repository.api.DataRepository
import aa.weather.repository.api.data.LocationWeather
import aa.weather.repository.api.data.ManagedData
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow

internal class MockRepository : DataRepository {
    override fun <T : ManagedData> observe(cl: Class<T>): Flow<T> =
        if (cl == LocationWeather::class.java) {
            locationWeatherMock()
        } else {
            emptyFlow()
        }

    private fun <T : ManagedData> locationWeatherMock(): Flow<T> =
        flow {
            val initial = LocationWeather("Berlin", "24 C")
            emit(initial as T)
            repeat(100) {
                currentCoroutineContext().ensureActive()
                delay(5000)
                emit(initial.copy(temperature = "${24 + it} C") as T)
            }
        }
}
