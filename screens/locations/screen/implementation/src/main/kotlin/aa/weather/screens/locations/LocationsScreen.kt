package aa.weather.screens.locations

import androidx.fragment.app.Fragment
import aa.weather.navigation.navigator.api.Destination as DestinationAPI
import aa.weather.navigation.plugin.api.NavigationPlugin as NavigationPluginAPI

interface LocationsScreen {
    object Destination : DestinationAPI

    object NavigationPlugin : NavigationPluginAPI {
        override fun destinationToFragment(
            destination: DestinationAPI,
        ): Class<out Fragment> = LocationsFragment::class.java
    }
}
