package aa.weather.screens.location.plugin.forecast.daily

import aa.weather.repository.api.data.DayForecast
import aa.weather.screens.location.plugin.api.PluginUIState

internal data class DailyForecastUIState(
    val days: List<DayForecast>,
) : PluginUIState {
    override val items: List<Any>
        get() = days
}
