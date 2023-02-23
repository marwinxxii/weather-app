package aa.weather.screens.location.state

import aa.weather.entities.location.LocationID
import aa.weather.entities.location.LocationsService
import aa.weather.screens.location.kernel.PluginManager
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
    private val locationsService: LocationsService,
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
                .let { ScreenState("", it) }
        }
    }

    fun getRenderer(model: ScreenUIModel) = pluginManager.getOrCreateRenderer(model.pluginKey)

    fun setLocation(location: LocationID) {
        locationsService.setCurrentlySelectedLocation(location)
    }

    override fun onCleared() {
        scope.cancel()
    }
}
