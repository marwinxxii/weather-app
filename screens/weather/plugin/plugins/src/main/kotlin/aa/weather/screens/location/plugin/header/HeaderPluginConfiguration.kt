package aa.weather.screens.location.plugin.header

import aa.weather.screens.location.plugin.api.PluginConfiguration
import aa.weather.screens.location.plugin.api.PluginKey
import aa.weather.screens.location.plugin.api.PluginRenderer
import aa.weather.screens.location.plugin.api.PluginUIStateProvider
import javax.inject.Inject

class HeaderPluginConfiguration @Inject internal constructor(
    headerStateProvider: dagger.Lazy<HeaderUIStateProvider>,
) : PluginConfiguration {
    override val key: PluginKey
        get() = TODO("Not yet implemented")
    override val state: Lazy<PluginUIStateProvider> =
        lazy(LazyThreadSafetyMode.NONE, headerStateProvider::get)
    override val ui: Lazy<PluginRenderer<*>> = lazy(::HeaderRenderer)
}
