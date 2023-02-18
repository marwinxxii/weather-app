package aa.weather.screens.location.plugin.header

import aa.weather.screens.location.plugin.api.DataRepository
import aa.weather.screens.location.plugin.api.LocationWeather
import aa.weather.screens.location.plugin.api.PluginUIStateProvider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class HeaderUIStateProvider @Inject constructor(
    private val repository: DataRepository,
) : PluginUIStateProvider {
    @Composable
    override fun getState(): HeaderUIState? {
        val locationWeather by repository
            .observe(LocationWeather::class.java)
            .let { it as Flow<LocationWeather?> }
            .collectAsState(initial = null)
        return locationWeather?.let {
            HeaderUIState(city = it.name, temperature = it.temperature)
        }
    }
}