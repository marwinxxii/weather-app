package aa.weather.screens.location.plugin.forecast.daily

import aa.weather.screens.location.plugin.api.PluginRenderer
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

internal object DailyForecastRenderer : PluginRenderer<DayForecastUIModel> {
    @SuppressLint("ComposableNaming")
    @Suppress("MagicNumber")
    @Composable
    override fun render(model: DayForecastUIModel) {
        Column(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 8.dp,
                    top = 8.dp,
                )
                .fillMaxWidth(),
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                val fontSize = 24.sp
                Text(
                    text = model.dayOfWeek,
                    fontSize = fontSize,
                    modifier = Modifier.weight(0.2f),
                    textAlign = TextAlign.Start,
                )
                Text(
                    text = model.temperatureMin,
                    fontSize = fontSize,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.weight(0.25f),
                    textAlign = TextAlign.End,
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = model.temperatureMax,
                    fontSize = fontSize,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.weight(0.25f),
                    textAlign = TextAlign.End,
                )
                Spacer(modifier = Modifier.weight(0.1f, fill = true))
            }
            Text(
                text = model.weatherConditions,
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                color = Color.DarkGray,
            )
        }
        if (model.showDivider) {
            Divider()
        }
    }
}
