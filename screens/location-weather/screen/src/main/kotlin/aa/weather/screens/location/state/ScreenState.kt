package aa.weather.screens.location.state

import aa.weather.screens.location.kernel.PluginKey
import aa.weather.screens.location.plugin.api.UIModel

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
