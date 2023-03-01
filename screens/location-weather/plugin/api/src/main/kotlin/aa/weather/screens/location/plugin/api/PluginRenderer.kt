package aa.weather.screens.location.plugin.api

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable

interface PluginRenderer<T : UIModel> {
    @SuppressLint("ComposableNaming")
    @Composable
    fun render(model: T)

    fun getContentType(model: T): Any? = null
}
