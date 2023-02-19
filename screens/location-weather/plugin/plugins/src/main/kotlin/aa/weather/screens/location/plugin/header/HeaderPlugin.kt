package aa.weather.screens.location.plugin.header

import aa.weather.screens.location.plugin.api.Plugin
import aa.weather.screens.location.plugin.api.PluginConfiguration
import aa.weather.screens.location.plugin.api.PluginRenderer
import aa.weather.screens.location.plugin.api.PluginUIStateProvider
import javax.inject.Inject
import javax.inject.Provider

class HeaderPlugin @Inject internal constructor(
    private val headerStateProvider: Provider<HeaderUIStateProvider>,
) : Plugin<PluginConfiguration?> {
    override fun createRenderer(config: PluginConfiguration?): PluginRenderer<*> =
        HeaderRenderer()

    override fun createStateProvider(config: PluginConfiguration?): PluginUIStateProvider =
        headerStateProvider.get()
}
