package aa.weather.screens.location

import aa.weather.entities.location.LocationsService
import aa.weather.screen.api.InjectableScreen
import aa.weather.screen.api.ScreenDependenciesLocator
import aa.weather.screens.location.state.ScreenState
import aa.weather.screens.location.state.WeatherViewModel
import aa.weather.subscription.service.api.SubscriptionService
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
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
            )
            .inject(this)
    }

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
