package aa.weather.screens.location.kernel

import aa.weather.screens.location.plugin.api.Plugin
import aa.weather.screens.location.plugin.api.PluginConfiguration

internal class ConfiguredPlugin<T : PluginConfiguration?>(
    val key: PluginKey,
    private val plugin: Plugin<T>,
    private val configuration: T,
) {
    val stateProvider by lazy { plugin.createStateProvider(configuration) }

    val renderer by lazy { plugin.createRenderer(configuration) }
}
