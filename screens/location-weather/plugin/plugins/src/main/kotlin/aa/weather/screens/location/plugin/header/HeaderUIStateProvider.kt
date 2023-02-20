package aa.weather.screens.location.plugin.header

import aa.weather.repository.api.data.LatestWeather
import aa.weather.screens.location.plugin.api.ItemsState
import aa.weather.screens.location.plugin.api.PluginUIState
import aa.weather.screens.location.plugin.api.PluginUIStateProvider
import aa.weather.subscription.api.Subscription
import aa.weather.subscription.api.SubscriptionService
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
                    locationName = it.location.name,
                    temperature = it.currentTemperature.formatted,
                    weatherConditions = it.weather.toString(),
                )
            }
            ?.let { ItemsState(items = listOf(it)) }
    }
}
