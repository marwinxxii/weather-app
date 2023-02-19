package aa.weather.screens.location.plugin.header

import aa.weather.screens.location.plugin.api.PluginRenderer
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

internal class HeaderRenderer : PluginRenderer<HeaderUIModel> {
    @Composable
    override fun render(model: HeaderUIModel) {
        Column {
            Text(model.locationName)
            Text(model.temperature)
            Text(model.weatherConditions)
        }
    }
}
