package aa.weather.navigation.navigator

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class NavigatorHolderViewModel @Inject internal constructor(
    val navigator: FragmentNavigator,
) : ViewModel()
