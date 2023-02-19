package aa.weather.screens.location.plugin.forecast.daily

import aa.weather.screens.location.plugin.api.PluginRenderer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

internal class DailyForecastRenderer : PluginRenderer<DailyForecastUIState> {
    @Composable
    override fun render(state: DailyForecastUIState) {
        state.days.map {
            Text(it.toString())
        }
    }
}
