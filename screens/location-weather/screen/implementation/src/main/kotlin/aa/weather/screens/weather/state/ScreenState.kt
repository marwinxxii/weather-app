package aa.weather.screens.weather.state

import aa.weather.screens.location.plugin.api.UIModel
import aa.weather.screens.weather.kernel.PluginKey

internal sealed interface ScreenState {
    object Loading : ScreenState

    data class Loaded(
        val items: List<ScreenUIModel>,
    ) : ScreenState
}

internal data class ScreenUIModel(
    val pluginKey: PluginKey,
    val itemKey: Any,
    val contentType: Any,
    val model: UIModel,
)
