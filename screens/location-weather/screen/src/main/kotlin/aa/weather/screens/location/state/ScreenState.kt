package aa.weather.screens.location.state

import aa.weather.screens.location.kernel.PluginKey
import aa.weather.screens.location.plugin.api.UIModel

data class ScreenState(
    val locationId: String,
    val items: List<ScreenUIModel>,
)

data class ScreenUIModel(
    val pluginKey: PluginKey,
    val itemKey: Any,
    val contentType: Any,
    val model: UIModel,
)
