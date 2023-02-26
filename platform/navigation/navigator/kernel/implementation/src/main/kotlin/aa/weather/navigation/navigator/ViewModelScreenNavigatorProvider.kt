package aa.weather.navigation.navigator

import aa.weather.navigation.navigator.api.Navigator
import aa.weather.navigation.navigator.screen.api.ScreenNavigatorProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelInitializer
import javax.inject.Inject
import javax.inject.Provider

internal class ViewModelScreenNavigatorProvider @Inject constructor(
    private val navigatorProvider: Provider<FragmentNavigator>,
) : ScreenNavigatorProvider {
    override fun getOrCreateNavigator(activity: FragmentActivity): Navigator =
        ViewModelProvider(
            activity,
            ViewModelProvider.Factory.from(
                ViewModelInitializer(NavigatorHolderViewModel::class.java) {
                    NavigatorHolderViewModel(navigatorProvider.get())
                },
            )
        )[NavigatorHolderViewModel::class.java]
            .navigator

    override fun getOrCreateNavigator(fragment: Fragment): Navigator =
        getOrCreateNavigator(fragment.requireActivity())
}
