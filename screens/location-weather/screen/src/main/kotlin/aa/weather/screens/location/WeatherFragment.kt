package aa.weather.screens.location

import aa.weather.app.screens.weather.R
import aa.weather.entities.location.LocationsService
import aa.weather.navigation.navigator.screen.api.ScreenNavigatorProvider
import aa.weather.screen.api.InjectableScreen
import aa.weather.screen.api.ScreenDependenciesLocator
import aa.weather.screens.location.plugin.api.PluginRenderer
import aa.weather.screens.location.plugin.api.UIModel
import aa.weather.screens.location.state.ScreenState
import aa.weather.screens.location.state.ScreenUIModel
import aa.weather.screens.location.state.WeatherViewModel
import aa.weather.subscription.service.api.SubscriptionService
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

internal class WeatherFragment : Fragment(), InjectableScreen {
    @Inject
    internal lateinit var vmFactory: ViewModelProvider.Factory

    private val vm by viewModels<WeatherViewModel>(factoryProducer = ::vmFactory)

    override fun injectDependencies(locator: ScreenDependenciesLocator) {
        DaggerWeatherFragmentComponent.factory()
            .create(
                locator.getOrCreateDependency(SubscriptionService::class.java),
                locator.getOrCreateDependency(LocationsService::class.java),
                locator.getOrCreateDependency(ScreenNavigatorProvider::class.java)
                    .getOrCreateNavigator(this),
            )
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View =
        ComposeView(container?.context ?: inflater.context).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (view as ComposeView).setContent {
            CompositionRoot(vm.state, vm::onShowPreferences, vm::getRenderer)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CompositionRoot(
    screenState: StateFlow<ScreenState>,
    onShowPreferences: () -> Unit,
    rendererProvider: (ScreenUIModel) -> PluginRenderer<UIModel>,
) {
    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {},
                    actions = {
                        IconButton(onClick = onShowPreferences) {
                            Icon(
                                Icons.Default.Settings,
                                contentDescription = stringResource(
                                    R.string.screen_weather_preferences_action_content_desc
                                ),
                            )
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = Color.Transparent,
                        actionIconContentColor = Color.DarkGray,
                    ),
                )
            },
        ) {
            val s by screenState.collectAsStateWithLifecycle()
            Content(s, it, rendererProvider)
        }
    }
}

@Composable
private fun Content(
    screenState: ScreenState,
    paddingValues: PaddingValues,
    rendererProvider: (ScreenUIModel) -> PluginRenderer<UIModel>,
) {
    when (screenState) {
        is ScreenState.Loading -> Loading(paddingValues)
        is ScreenState.Loaded -> Loaded(screenState.items, rendererProvider, paddingValues)
    }
}

@Composable
private fun Loading(paddingValues: PaddingValues) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
    ) { CircularProgressIndicator() }
}

@Composable
private fun Loaded(
    items: List<ScreenUIModel>,
    rendererProvider: (ScreenUIModel) -> PluginRenderer<UIModel>,
    paddingValues: PaddingValues,
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
    ) {
        items(
            items,
            key = { it.itemKey },
            contentType = { it.contentType },
        ) { rendererProvider(it).render(it.model) }
    }
}
