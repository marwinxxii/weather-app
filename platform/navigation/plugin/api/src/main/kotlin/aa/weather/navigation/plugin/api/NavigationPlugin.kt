package aa.weather.navigation.plugin.api

import aa.weather.navigation.navigator.api.Destination
import androidx.fragment.app.Fragment

interface NavigationPlugin {
    fun destinationToFragment(destination: Destination): Class<out Fragment>
}
