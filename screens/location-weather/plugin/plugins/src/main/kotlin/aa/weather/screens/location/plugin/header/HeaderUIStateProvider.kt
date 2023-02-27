package aa.weather.screens.location.plugin.header

import aa.weather.entities.weather.LatestWeather
import aa.weather.screens.location.plugin.api.ItemsState
import aa.weather.screens.location.plugin.api.PluginUIState
import aa.weather.screens.location.plugin.api.PluginUIStateProvider
import aa.weather.subscription.api.Subscription
import aa.weather.subscription.service.api.SubscriptionService
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class HeaderUIStateProvider @Inject constructor(
    private val subscriptionService: SubscriptionService,
) : PluginUIStateProvider {
    private val weather by lazy {
        subscriptionService.observe(Subscription(LatestWeather::class.java))
    }

    @Composable
    override fun getState() = subscribe(weather)

    @Composable
    private fun subscribe(weather: Flow<LatestWeather>): PluginUIState? {
        val locationWeather by weather.collectAsState(initial = null)
        return locationWeather
            ?.locations
            ?.firstOrNull()
            ?.let {
                HeaderUIModel(
                    locationName = it.location,
                    temperature = it.temperature.formatted,
                    weatherConditions = it.conditions,
                )
            }
            ?.let { ItemsState(items = listOf(it)) }
    }
}
