package aa.weather.screens.locations

import aa.weather.entities.location.Location
import aa.weather.entities.location.LocationID
import aa.weather.entities.location.LocationsService
import aa.weather.entities.location.UserLocations
import aa.weather.subscription.api.Subscription
import aa.weather.subscription.service.api.SubscriptionService
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.AndroidUiDispatcher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.molecule.RecompositionClock
import app.cash.molecule.launchMolecule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

internal class LocationsViewModel @Inject constructor(
    private val subscriptionService: SubscriptionService,
    private val locationsService: LocationsService,
) : ViewModel() {
    private val scope = CoroutineScope(viewModelScope.coroutineContext + AndroidUiDispatcher.Main)

    val state: StateFlow<ScreenState> by lazy(LazyThreadSafetyMode.NONE) {
        scope.launchMolecule(RecompositionClock.ContextClock) {
            composeState(subscriptionService.observe(Subscription(UserLocations::class.java)))
        }
    }

    // weatherapi.com resolves lat & lon coordinates incorrectly =\
    private fun defaultLocations(): List<Location> =
        listOf(
            Location(
                id = LocationID("Berlin"),
                name = "Berlin"
            ),
            Location(
                id = LocationID("Stockholm"),
                name = "Stockholm"
            ),
            Location(
                id = LocationID("New York"),
                name = "New York"
            ),
        )

    @Composable
    private fun composeState(userLocations: Flow<UserLocations>): ScreenState {
        val currentUserLocations by userLocations.collectAsState(initial = null)
        return currentUserLocations
            ?.let {
                ScreenState.Loaded(
                    locations = (it.all.takeIf { it.isNotEmpty() } ?: defaultLocations()).map {
                        LocationUIModel(name = it.name, id = it.id)
                    },
                )
            }
            ?: ScreenState.Loading
    }

    fun onLocationSelected(model: LocationUIModel) {
        locationsService.setCurrentlySelectedLocation(model.id)
    }
}
