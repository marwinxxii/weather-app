package aa.weather.screens.location.plugin.api

import androidx.compose.runtime.Composable

interface PluginRenderer<T : PluginUIState> {
    @Composable
    fun render(state: T)
}