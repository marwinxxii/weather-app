package aa.weather.screens.weather.state

import aa.weather.i18n.api.TranslationProvider
import aa.weather.navigation.navigator.api.Navigator
import aa.weather.screens.location.plugin.api.PluginUIStateProvider
import aa.weather.screens.weather.PreferencesDestination
import aa.weather.screens.weather.R
import aa.weather.screens.weather.kernel.PluginKey
import aa.weather.screens.weather.kernel.PluginManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.AndroidUiDispatcher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.molecule.RecompositionClock
import app.cash.molecule.launchMolecule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlin.time.DurationUnit
import kotlin.time.toDuration

internal class WeatherViewModel @Inject constructor(
    private val pluginManager: PluginManager,
    private val navigator: Navigator,
    private val translationProvider: TranslationProvider,
) : ViewModel() {
    private val scope = CoroutineScope(viewModelScope.coroutineContext + AndroidUiDispatcher.Main)
    private val retries = Channel<Unit> { }

    val state: StateFlow<ScreenState> by lazy(LazyThreadSafetyMode.NONE) {
        val stateProviders = pluginManager.stateProviders

        scope.launchMolecule(RecompositionClock.ContextClock) {
            var state by remember { mutableStateOf<ScreenState>(ScreenState.Loading) }

            if (state !is ScreenState.Error) {
                state = createStateFromPlugins(stateProviders)
            }

            LaunchedEffect(state) {
                if (state is ScreenState.Loading) {
                    delay(5.toDuration(DurationUnit.SECONDS))
                    state = ScreenState.Error(
                        message = translationProvider.getTranslation(
                            R.string.screen_weather_error_message
                        ),
                    )
                }
            }

            LaunchedEffect(state) {
                if (state is ScreenState.Error) {
                    retries.receive()
                    state = ScreenState.Loading
                }
            }

            state
        }
    }

    @Composable
    private fun createStateFromPlugins(
        stateProviders: List<Pair<PluginKey, PluginUIStateProvider>>,
    ): ScreenState =
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

    fun getRenderer(model: ScreenUIModel) = pluginManager.getOrCreateRenderer(model.pluginKey)

    fun onShowPreferences() {
        navigator.navigateTo(PreferencesDestination)
    }

    fun onRetry() {
        retries.trySend(Unit)
    }

    override fun onCleared() {
        scope.cancel()
    }
}
