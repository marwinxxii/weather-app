package aa.weather.screens.location.plugin.header

import aa.weather.screens.location.plugin.api.UIModel

internal data class HeaderUIModel(
    val locationName: String,
    val temperature: String,
    val weatherConditions: String,
) : UIModel
