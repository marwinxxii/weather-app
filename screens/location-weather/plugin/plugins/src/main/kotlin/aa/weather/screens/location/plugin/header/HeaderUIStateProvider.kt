package aa.weather.screens.location.plugin.header

import aa.weather.repository.api.DataRepository
import aa.weather.repository.api.data.LocationLatestWeather
import aa.weather.screens.location.plugin.api.ItemsState
import aa.weather.screens.location.plugin.api.PluginUIState
import aa.weather.screens.location.plugin.api.PluginUIStateProvider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class HeaderUIStateProvider @Inject constructor(
    private val repository: DataRepository,
) : PluginUIStateProvider {
    private val weather by lazy { repository.observe(LocationLatestWeather::class.java) }

    @Composable
    override fun getState() = subscribe(weather)

    @Composable
    private fun subscribe(weather: Flow<LocationLatestWeather>): PluginUIState? {
        val locationWeather by weather.collectAsState(initial = null)
        return locationWeather
            ?.let {
                HeaderUIModel(
                    locationName = it.locationName,
                    temperature = it.currentTemperature.formatted,
                    weatherConditions = it.weather.toString(),
                )
            }
            ?.let { ItemsState(items = listOf(it)) }
    }
}
