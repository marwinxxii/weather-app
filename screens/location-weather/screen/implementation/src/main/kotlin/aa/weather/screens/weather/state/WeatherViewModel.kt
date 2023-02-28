package aa.weather.screens.weather.state

import aa.weather.navigation.navigator.api.Navigator
import aa.weather.screens.weather.PreferencesDestination
import aa.weather.screens.weather.kernel.PluginManager
import androidx.compose.ui.platform.AndroidUiDispatcher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.molecule.RecompositionClock
import app.cash.molecule.launchMolecule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

internal class WeatherViewModel @Inject constructor(
    private val pluginManager: PluginManager,
    private val navigator: Navigator,
) : ViewModel() {
    private val scope = CoroutineScope(viewModelScope.coroutineContext + AndroidUiDispatcher.Main)

    val state: StateFlow<ScreenState> by lazy(LazyThreadSafetyMode.NONE) {
        val stateProviders = pluginManager.stateProviders

        scope.launchMolecule(RecompositionClock.ContextClock) {
            stateProviders
                .mapNotNull { (key, provider) -> provider.getState()?.let { key to it } }
                .fold(mutableListOf<ScreenUIModel>()) { result, (key, state) ->
                    state.items.mapTo(result) { model ->
                        ScreenUIModel(
                            pluginKey = key,
                            itemKey = model.key ?: result.size,
                            contentType = pluginManager.getOrCreateRenderer(key)
                                .getContentType(model) ?: model::class.java,
                            model = model,
                        )
                    }
                    result
                }
                .let {
                    if (it.isEmpty()) {
                        ScreenState.Loading
                    } else {
                        ScreenState.Loaded(it)
                    }
                }
        }
    }

    fun getRenderer(model: ScreenUIModel) = pluginManager.getOrCreateRenderer(model.pluginKey)

    fun onShowPreferences() {
        navigator.navigateTo(PreferencesDestination)
    }

    override fun onCleared() {
        scope.cancel()
    }
}
