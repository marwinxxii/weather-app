package aa.weather.screens.location.plugin.api

interface PluginUIState {
    val items: List<UIModel>
}

data class ItemsState(override val items: List<UIModel>) : PluginUIState
