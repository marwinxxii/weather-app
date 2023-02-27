package aa.weather.app

import aa.weather.entities.location.UserLocations
import aa.weather.navigation.navigator.api.Navigator
import aa.weather.screens.locations.LocationsScreen
import aa.weather.screens.weather.WeatherScreen
import aa.weather.subscription.api.Subscription
import aa.weather.subscription.service.api.SubscriptionService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class RootViewModel @Inject constructor(
    private val subscriptionService: SubscriptionService,
    private val navigator: Navigator,
) : ViewModel() {
    fun attach() {
        viewModelScope.launch {
            subscriptionService.observe(Subscription(UserLocations::class.java))
                .collect {
                    if (it.current != null) {
                        navigator.navigateTo(WeatherScreen.Destination)
                    } else {
                        navigator.navigateTo(LocationsScreen.Destination)
                    }
                }
        }
    }
}
