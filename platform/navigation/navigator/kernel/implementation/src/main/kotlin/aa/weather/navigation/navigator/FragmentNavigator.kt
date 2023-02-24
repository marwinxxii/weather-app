package aa.weather.navigation.navigator

import aa.weather.navigation.navigator.api.Destination
import aa.weather.navigation.navigator.api.Navigator
import aa.weather.navigation.plugin.api.NavigationPlugin
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias NavigationPlugins = @JvmSuppressWildcards Map<Class<out Destination>, NavigationPlugin>

class FragmentNavigator @Inject constructor(
    private val plugins: NavigationPlugins,
) : Navigator {
    private val navigationCommands = MutableStateFlow<Destination?>(value = null)

    override fun navigateTo(destination: Destination): Boolean {
        return plugins[destination.javaClass] != null &&
            navigationCommands.tryEmit(destination)
    }

    val currentScreen: Destination? get() = navigationCommands.value

    fun attach(
        fragmentManager: FragmentManager,
        @IdRes containerViewId: Int,
        startScreen: Destination?,
        lifecycleScope: CoroutineScope,
    ) {
        val hasOpenedScreen = startScreen != null && navigationCommands.value != null
        lifecycleScope.launch {
            navigationCommands
                .distinctUntilChanged { old, new -> old == new }
                .let { if (hasOpenedScreen) it.drop(1) else it }
                .collect {
                    if (it != null) {
                        showScreen(fragmentManager, containerViewId, it)
                    }
                }
        }
        if (!hasOpenedScreen) {
            startScreen?.also(::navigateTo)
        }
    }

    private fun showScreen(
        fragmentManager: FragmentManager,
        containerViewId: Int,
        destination: Destination,
    ) {
        val plugin = plugins[destination.javaClass]!!
        val fragment = plugin.destinationToFragment(destination)
        fragmentManager.beginTransaction()
            // no screen arguments for now
            .replace(containerViewId, fragment, null)
            .commit()
    }
}
