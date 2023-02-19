package aa.weather.screens.location.plugin.api

interface Plugin<T : PluginConfiguration?> {
    fun createStateProvider(config: T): PluginUIStateProvider

    fun createRenderer(config: T): PluginRenderer<*>
}
