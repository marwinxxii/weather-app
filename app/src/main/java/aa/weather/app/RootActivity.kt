package aa.weather.app

import aa.weather.navigation.navigator.FragmentNavigator
import aa.weather.navigation.navigator.api.Destination
import aa.weather.navigation.navigator.screen.api.ScreenNavigatorProvider
import aa.weather.screen.api.ScreenDependenciesLocator
import aa.weather.subscription.service.api.SubscriptionService
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.ViewModelInitializer

private const val STATE_CURRENT_SCREEN = "currentScreen"

class RootActivity : FragmentActivity() {
    private lateinit var viewModel: RootViewModel
    private lateinit var navigator: FragmentNavigator

    private fun injectDependencies(locator: ScreenDependenciesLocator) {
        val fragmentNavigator = locator.getOrCreateDependency(ScreenNavigatorProvider::class.java)
            .getOrCreateNavigator(this@RootActivity) as FragmentNavigator
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.Factory.from(
                ViewModelInitializer(RootViewModel::class.java) {
                    RootViewModel(
                        locator.getOrCreateDependency(SubscriptionService::class.java),
                        fragmentNavigator,
                    )
                },
            )
        )[RootViewModel::class.java]
        navigator = fragmentNavigator
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies(application as WeatherApp)
        setContentView(R.layout.root_activity)
        navigator.attach(
            supportFragmentManager,
            containerViewId = android.R.id.content,
            startScreen = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                savedInstanceState?.getParcelable(STATE_CURRENT_SCREEN, Destination::class.java)
            } else {
                @Suppress("DEPRECATION")
                (savedInstanceState?.getParcelable(STATE_CURRENT_SCREEN) as? Destination)
            },
            lifecycleScope = lifecycleScope,
        )
        if (savedInstanceState == null) {
            viewModel.attach()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // move to the view model perhaps
        navigator.currentScreen
            ?.let { it as? Parcelable }
            ?.also { outState.putParcelable(STATE_CURRENT_SCREEN, it) }
    }
}
