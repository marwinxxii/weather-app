package aa.weather.navigation.navigator.screen.api

import aa.weather.component.di.AppPlugin
import aa.weather.navigation.navigator.api.Navigator
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

interface ScreenNavigatorProvider : AppPlugin {
    fun getOrCreateNavigator(activity: FragmentActivity): Navigator

    fun getOrCreateNavigator(fragment: Fragment): Navigator
}
