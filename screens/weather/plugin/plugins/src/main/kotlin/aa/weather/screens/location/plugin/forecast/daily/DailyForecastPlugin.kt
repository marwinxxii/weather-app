package aa.weather.screens.location.plugin.forecast.daily

import aa.weather.repository.api.DataRepository
import aa.weather.repository.api.data.DailyForecastArguments
import aa.weather.screens.location.plugin.api.Plugin
import aa.weather.screens.location.plugin.api.PluginConfiguration
import aa.weather.screens.location.plugin.api.PluginRenderer
import aa.weather.screens.location.plugin.api.PluginUIStateProvider
import javax.inject.Inject

class DailyForecastPlugin @Inject internal constructor(
    private val repository: DataRepository,
) : Plugin<DailyForecastConfiguration?> {
    override fun createStateProvider(config: DailyForecastConfiguration?): PluginUIStateProvider =
        DailyForecastUIStateProvider(
            repository,
            config?.let { DailyForecastArguments(daysCount = it.daysCount) },
        )

    override fun createRenderer(config: DailyForecastConfiguration?): PluginRenderer<*> =
        DailyForecastRenderer()
}

data class DailyForecastConfiguration(
    val daysCount: Int,
) : PluginConfiguration
