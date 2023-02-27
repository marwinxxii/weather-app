package aa.weather.screens.weather.kernel

import aa.weather.screens.location.plugin.api.Plugin
import aa.weather.screens.location.plugin.api.PluginConfiguration

internal class ScreenConfiguration private constructor() {
    private val plugins = mutableListOf<ConfiguredPlugin<*>>()

    fun <T : Plugin<C>, C : PluginConfiguration?> registerPlugin(
        key: PluginKey,
        plugin: T,
        configuration: C,
    ) = apply {
        plugins.add(
            ConfiguredPlugin(
                key = key,
                plugin = plugin,
                configuration = configuration,
            )
        )
    }

    fun assemblePlugins(): PluginManager = PluginManagerImpl(plugins.toList())

    companion object {
        fun builder(): ScreenConfiguration = ScreenConfiguration()
    }
}
