package aa.weather.screens.location.plugin.header

import aa.weather.screens.location.plugin.api.PluginUIState

internal data class HeaderUIState(
    val city: String,
    val temperature: String,
) : PluginUIState {
    override val items: List<Any>
        get() = listOf()
}