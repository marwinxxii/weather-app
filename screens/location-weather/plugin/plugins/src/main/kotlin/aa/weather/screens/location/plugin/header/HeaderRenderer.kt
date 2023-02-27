package aa.weather.screens.location.plugin.header

import aa.weather.screens.location.plugin.api.PluginRenderer
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

internal class HeaderRenderer : PluginRenderer<HeaderUIModel> {
    @Composable
    override fun render(model: HeaderUIModel) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
        ) {
            Text(
                model.locationName,
                fontSize = 32.sp,
            )
            Text(
                model.temperature,
                fontSize = 48.sp,
            )
            Text(
                model.weatherConditions,
                fontSize = 24.sp,
            )
        }
    }
}
