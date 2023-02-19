package aa.weather.screens.location.kernel

import aa.weather.screens.location.plugin.api.PluginRenderer
import aa.weather.screens.location.plugin.api.PluginUIStateProvider
import aa.weather.screens.location.plugin.api.UIModel

interface PluginManager {
    val stateProviders: List<Pair<PluginKey, PluginUIStateProvider>>

    fun getRenderer(pluginKey: PluginKey): PluginRenderer<UIModel>
}

internal typealias PluginKey = Any
