package aa.weather.screens.location

import aa.weather.screens.location.plugin.api.Plugin
import aa.weather.screens.location.plugin.api.PluginConfiguration
import aa.weather.screens.location.plugin.api.PluginRenderer
import aa.weather.screens.location.plugin.api.PluginUIState
import aa.weather.screens.location.plugin.forecast.daily.DailyForecastPlugin
import aa.weather.screens.location.plugin.header.HeaderPlugin
import aa.weather.screens.location.state.ScreenState
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.AndroidUiDispatcher
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import app.cash.molecule.RecompositionClock
import app.cash.molecule.launchMolecule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class WeatherFragment : Fragment() {
    @Inject
    internal lateinit var vmFactory: ViewModelProvider.Factory

    private val vm by viewModels<WeatherViewModel>(factoryProducer = ::vmFactory)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
//        LinearLayout(container?.context ?: inflater.context).apply {
//            layoutParams = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT
//            )
//            orientation = LinearLayout.VERTICAL
//        }
        ComposeView(container?.context ?: inflater.context).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DaggerWeatherFragmentComponent.create().inject(this)
        (view as ComposeView).setContent {
            MaterialTheme {
                val s by vm.state.collectAsStateWithLifecycle()
                renderItems(s)
            }
        }
    }

    @Composable
    private fun renderItems(s: ScreenState) {
        LazyColumn {
            s.items.lastOrNull()?.also { df ->
                item(key = df::class.java, contentType = df::class.java) {
                    vm.renderer(df).render(df)
                }
            }
        }
    }

    private fun renderState(
        view: View,
        state: ScreenState
    ) {
        (view as LinearLayout).apply {
            removeAllViews()
            for (i in state.items) {
                addView(
                    TextView(view.context).apply {
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        text = i.toString()
                    }
                )
            }
        }
    }
}

internal class WeatherViewModel @Inject constructor(
    private val plugins: @JvmSuppressWildcards Set<Plugin<*>>,
) : ViewModel() {
    private val scope = CoroutineScope(viewModelScope.coroutineContext + AndroidUiDispatcher.Main)
    private val ordered = listOf(
        plugins.first { it is HeaderPlugin },
        plugins.first { it is DailyForecastPlugin },
    )

    val state: StateFlow<ScreenState> by lazy(LazyThreadSafetyMode.NONE) {
        val stateProviders = ordered
            .map { (it as Plugin<PluginConfiguration?>).createStateProvider(null) }

        scope.launchMolecule(RecompositionClock.ContextClock) {
            stateProviders
                .mapNotNull { it.getState() }
                .let { ScreenState("", it) }
        }
    }

    fun renderer(state: PluginUIState) =
        (ordered.last() as Plugin<PluginConfiguration?>)
            .createRenderer(null)
            .let { it as PluginRenderer<PluginUIState> }

    override fun onCleared() {
        scope.cancel()
    }
}
