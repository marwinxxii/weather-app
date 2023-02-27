package aa.weather.screens.location.plugin.api

import androidx.compose.runtime.Composable

interface PluginUIStateProvider {
    @Composable
    fun getState(): PluginUIState?
}
