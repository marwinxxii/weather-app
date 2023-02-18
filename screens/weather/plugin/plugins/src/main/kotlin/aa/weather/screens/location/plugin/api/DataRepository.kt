package aa.weather.screens.location.plugin.api

import kotlinx.coroutines.flow.Flow

sealed interface ManagedData {

}

interface DataRepository {
    fun <T: ManagedData> observe(cl: Class<T>): Flow<T>
}

data class LocationWeather(
    val name: String,
    val temperature: String,
) : ManagedData