package aa.weather.screens.location.plugin.api

import androidx.compose.runtime.Composable

interface PluginRenderer<T : UIModel> {
    @Composable
    fun render(model: T)

    fun getContentType(model: T): Any? = null
}
