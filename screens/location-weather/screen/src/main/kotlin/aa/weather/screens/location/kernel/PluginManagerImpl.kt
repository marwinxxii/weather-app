package aa.weather.screens.location.kernel

import aa.weather.screens.location.plugin.api.PluginRenderer
import aa.weather.screens.location.plugin.api.PluginUIStateProvider
import aa.weather.screens.location.plugin.api.UIModel

internal class PluginManagerImpl(
    private val plugins: List<ConfiguredPlugin<*>>,
) : PluginManager {
    private val renderers = mutableMapOf<PluginKey, PluginRenderer<*>>()

    override val stateProviders: List<Pair<PluginKey, PluginUIStateProvider>> =
        plugins.map { it.key to it.stateProvider }

    override fun getOrCreateRenderer(pluginKey: PluginKey): PluginRenderer<UIModel> {
        val renderer = renderers.getOrPut(pluginKey) {
            plugins.first { it.key == pluginKey }.renderer
        }
        @Suppress("UNCHECKED_CAST")
        return renderer as PluginRenderer<UIModel>
    }
}
