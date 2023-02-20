package aa.weather.screens.location.plugin.forecast.daily

import aa.weather.entities.weather.DailyForecastArguments
import aa.weather.screens.location.plugin.api.Plugin
import aa.weather.screens.location.plugin.api.PluginConfiguration
import aa.weather.screens.location.plugin.api.PluginRenderer
import aa.weather.screens.location.plugin.api.PluginUIStateProvider
import aa.weather.subscription.service.api.SubscriptionService
import javax.inject.Inject

class DailyForecastPlugin @Inject internal constructor(
    private val subscriptionService: SubscriptionService,
) : Plugin<DailyForecastConfiguration> {
    override fun createStateProvider(config: DailyForecastConfiguration): PluginUIStateProvider =
        DailyForecastUIStateProvider(
            subscriptionService,
            DailyForecastArguments(daysCount = config.daysCount),
        )

    override fun createRenderer(config: DailyForecastConfiguration): PluginRenderer<*> =
        DailyForecastRenderer()
}

data class DailyForecastConfiguration(
    val daysCount: Int,
) : PluginConfiguration
