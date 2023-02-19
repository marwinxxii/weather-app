package aa.weather.screens.location.kernel

import aa.weather.screens.location.plugin.api.PluginRenderer
import aa.weather.screens.location.plugin.api.PluginUIStateProvider
import aa.weather.screens.location.plugin.api.UIModel

internal interface PluginManager {
    val stateProviders: List<Pair<PluginKey, PluginUIStateProvider>>

    fun getOrCreateRenderer(pluginKey: PluginKey): PluginRenderer<UIModel>
}

internal typealias PluginKey = Any
