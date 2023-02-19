package aa.weather.screens.location.plugin.forecast.daily

import aa.weather.screens.location.plugin.api.PluginRenderer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

internal class DailyForecastRenderer : PluginRenderer<DayForecastUIModel> {
    @Composable
    override fun render(model: DayForecastUIModel) {
        Text("${model.temperatureMin}–${model.temperatureMax} ${model.weatherConditions}")
    }
}
