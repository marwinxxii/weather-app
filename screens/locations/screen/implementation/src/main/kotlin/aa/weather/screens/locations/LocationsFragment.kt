package aa.weather.screens.locations

import aa.weather.entities.location.LocationsService
import aa.weather.screen.api.InjectableScreen
import aa.weather.screen.api.ScreenDependenciesLocator
import aa.weather.subscription.service.api.SubscriptionService
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import javax.inject.Inject

internal class LocationsFragment : Fragment(), InjectableScreen {
    @Inject
    internal lateinit var vmFactory: ViewModelProvider.Factory

    private val vm by viewModels<LocationsViewModel>(factoryProducer = ::vmFactory)

    override fun injectDependencies(locator: ScreenDependenciesLocator) {
        DaggerLocationsFragmentComponent.factory()
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
        ComposeView(container?.context ?: inflater.context).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (view as ComposeView).setContent {
            MaterialTheme {
                val state by vm.state.collectAsStateWithLifecycle()
                renderState(state)
            }
        }
    }

    @Composable
    private fun renderState(state: ScreenState) {
        when (state) {
            is ScreenState.Loading -> Loading()
            is ScreenState.Loaded -> Loaded(state, vm::onLocationSelected)
        }
    }
}

@Composable
private fun Loading() {
    Box(contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun Loaded(state: ScreenState.Loaded, selectionCallback: (LocationUIModel) -> Unit) {
    LazyColumn {
        item(key = "header", contentType = "header") { Header() }
        itemsIndexed(
            items = state.locations,
            key = { _, model -> model.id.value },
            contentType = { _, _ -> LocationUIModel::class.java }
        ) { index, model ->
            Location(model = model, onClick = selectionCallback)
            if (index < state.locations.size - 1) {
                Divider()
            }
        }
    }
}

private val defaultPadding = 16.dp

@Composable
private fun Header() {
    Text(
        text = stringResource(R.string.screen_locations_select),
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(defaultPadding),
    )
}

@Composable
private fun Location(model: LocationUIModel, onClick: (LocationUIModel) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(model) },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(defaultPadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = model.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            Image(
                painterResource(R.drawable.arrow_forward),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.DarkGray),
                modifier = Modifier.size(24.dp),
            )
        }
    }
}
