package aa.weather.screens.location.plugin.forecast.daily

import aa.weather.repository.api.DataRepository
import aa.weather.repository.api.data.DailyForecast
import aa.weather.repository.api.data.DailyForecastArguments
import aa.weather.screens.location.plugin.api.ItemsState
import aa.weather.screens.location.plugin.api.PluginUIState
import aa.weather.screens.location.plugin.api.PluginUIStateProvider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.Flow

internal class DailyForecastUIStateProvider(
    private val dataRepository: DataRepository,
    private val arguments: DailyForecastArguments,
) : PluginUIStateProvider {
    private val forecast by lazy { dataRepository.observe(DailyForecast::class.java, arguments) }

    @Composable
    override fun getState() = subscribe(forecast)

    @Composable
    private fun subscribe(forecast: Flow<DailyForecast>): PluginUIState? {
        val daysForecast by forecast.collectAsState(initial = null)
        return daysForecast?.days
            ?.map {
                DayForecastUIModel(
                    temperatureMin = it.temperatureMin.formatted,
                    temperatureMax = it.temperatureMax.formatted,
                    weatherConditions = it.weather.toString(),
                )
            }
            ?.let(::ItemsState)
    }
}
