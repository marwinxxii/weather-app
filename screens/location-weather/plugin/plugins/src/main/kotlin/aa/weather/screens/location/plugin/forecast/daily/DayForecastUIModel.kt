package aa.weather.screens.location.plugin.forecast.daily

import aa.weather.screens.location.plugin.api.UIModel

internal data class DayForecastUIModel(
    val dayOfWeek: String,
    val temperatureMin: String,
    val temperatureMax: String,
    val weatherConditions: String,
    val showDivider: Boolean,
) : UIModel
