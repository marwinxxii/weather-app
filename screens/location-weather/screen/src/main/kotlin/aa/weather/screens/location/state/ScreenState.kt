package aa.weather.screens.location.state

import aa.weather.screens.location.plugin.api.PluginUIState
import aa.weather.screens.location.plugin.api.PluginUIStateProvider
import androidx.compose.runtime.Composable

data class ScreenState(
    val locationId: String,
    val items: List<PluginUIState>,
)

@Composable
internal fun content(
    providers: List<PluginUIStateProvider>
): List<PluginUIState> {
    return providers.mapNotNull { it.getState() }
}
