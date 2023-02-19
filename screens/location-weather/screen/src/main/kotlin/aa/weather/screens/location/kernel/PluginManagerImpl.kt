package aa.weather.screens.location.kernel

import aa.weather.screens.location.plugin.api.Plugin
import aa.weather.screens.location.plugin.api.PluginConfiguration
import aa.weather.screens.location.plugin.api.PluginRenderer
import aa.weather.screens.location.plugin.api.PluginUIStateProvider
import aa.weather.screens.location.plugin.api.UIModel
import aa.weather.screens.location.plugin.forecast.daily.DailyForecastPlugin
import aa.weather.screens.location.plugin.header.HeaderPlugin
import javax.inject.Inject

class PluginManagerImpl @Inject constructor(
    private val plugins: @JvmSuppressWildcards Set<Plugin<*>>,
) : PluginManager {
    private val ordered = listOf(
        "header" to plugins.first { it is HeaderPlugin } as Plugin<PluginConfiguration?>,
        "daily forecast" to plugins.first { it is DailyForecastPlugin } as Plugin<PluginConfiguration?>,
    )

    override val stateProviders: List<Pair<PluginKey, PluginUIStateProvider>> =
        ordered.map { (key, plugin) ->
            key to plugin.createStateProvider(null)
        }

    override fun getRenderer(pluginKey: PluginKey): PluginRenderer<UIModel> {
        return (ordered.first { it.first == pluginKey })
            .second
            .createRenderer(null)
            .let { it as PluginRenderer<UIModel> }
    }
}
