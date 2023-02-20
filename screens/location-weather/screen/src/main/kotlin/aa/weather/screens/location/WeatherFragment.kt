package aa.weather.screens.location

import aa.weather.entities.weather.Location
import aa.weather.screens.location.state.LocationBoundSubscriptionService
import aa.weather.screens.location.kernel.PluginManager
import aa.weather.screens.location.state.ScreenState
import aa.weather.screens.location.state.ScreenUIModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
        savedInstanceState: Bundle?,
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
        if (!::vmFactory.isInitialized) {
            DaggerWeatherFragmentComponent.factory()
                .create(PersistenceModule(requireContext().applicationContext))
                .inject(this)
        }
        (view as ComposeView).setContent {
            MaterialTheme {
                val s by vm.state.collectAsStateWithLifecycle()
                renderItems(s)
            }
        }
        vm.setLocation(Location(name = "auto:ip"))
    }

    @Composable
    private fun renderItems(s: ScreenState) {
        LazyColumn {
            items(
                s.items,
                key = { it.itemKey },
                contentType = { it.contentType },
            ) { vm.getRenderer(it).render(it.model) }
        }
    }

    private fun renderState(
        view: View,
        state: ScreenState,
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
    private val pluginManager: PluginManager,
    private val service: LocationBoundSubscriptionService,
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

    fun setLocation(location: Location) {
        service.setLocation(location)
    }

    override fun onCleared() {
        scope.cancel()
    }
}
