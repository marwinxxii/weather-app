package aa.weather.screens.location.plugin.api

interface PluginKey

interface PluginConfiguration {
    val key: PluginKey

    val state: Lazy<PluginUIStateProvider>

    val ui: Lazy<PluginRenderer<*>>
}
