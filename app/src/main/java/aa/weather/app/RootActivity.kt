package aa.weather.app

import aa.weather.navigation.navigator.NavigatorHolderViewModel
import aa.weather.navigation.navigator.api.Destination
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import javax.inject.Inject

private const val STATE_CURRENT_SCREEN = "currentScreen"

class RootActivity : FragmentActivity() {
    @Inject
    internal lateinit var vmFactory: ViewModelProvider.Factory

    private val viewModelProvider by lazy { ViewModelProvider(this, vmFactory) }

    private val navigator by lazy {
        viewModelProvider[NavigatorHolderViewModel::class.java].navigator
    }
    private val rootViewModel by lazy { viewModelProvider[RootViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as WeatherApp).component.inject(this)
        setContentView(R.layout.root_activity)
        navigator.attach(
            supportFragmentManager,
            containerViewId = android.R.id.content,
            startScreen = (savedInstanceState?.getParcelable(STATE_CURRENT_SCREEN) as? Destination),
            lifecycleScope = lifecycleScope,
        )
        if (savedInstanceState == null) {
            rootViewModel.attach(navigator)
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
