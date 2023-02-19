package aa.weather.screens.location.plugin.header

import aa.weather.repository.api.DataRepository
import aa.weather.repository.api.data.LocationWeather
import aa.weather.screens.location.plugin.api.PluginUIStateProvider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class HeaderUIStateProvider @Inject constructor(
    private val repository: DataRepository,
) : PluginUIStateProvider {
    private val weather by lazy {
        repository.observe(LocationWeather::class.java)
    }

    @Composable
    override fun getState() = subscribeToState(weather)

    @Composable
    private fun subscribeToState(weather: Flow<LocationWeather>): HeaderUIState? {
        val locationWeather by weather.collectAsState(initial = null)
        return locationWeather?.let {
            HeaderUIState(city = it.name, temperature = it.temperature)
        }
    }
}
