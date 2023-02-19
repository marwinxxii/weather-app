package aa.weather.screens.location.plugin.header

import aa.weather.screens.location.plugin.api.PluginRenderer
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

internal class HeaderRenderer : PluginRenderer<HeaderUIState> {
    @Composable
    override fun render(state: HeaderUIState) {
        Column {
            Text(state.city)
            Text(state.temperature)
        }
    }
}