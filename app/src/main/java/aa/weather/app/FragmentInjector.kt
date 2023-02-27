package aa.weather.app

import aa.weather.screen.api.InjectableScreen
import aa.weather.screen.api.ScreenDependenciesLocator
import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

internal class FragmentInjector(
    private val locator: ScreenDependenciesLocator,
) : Application.ActivityLifecycleCallbacks, FragmentManager.FragmentLifecycleCallbacks() {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        (activity as? FragmentActivity)
            ?.supportFragmentManager
            ?.registerFragmentLifecycleCallbacks(this, true)
    }

    override fun onActivityStarted(p0: Activity) {
    }

    override fun onActivityResumed(p0: Activity) {
    }

    override fun onActivityPaused(p0: Activity) {
    }

    override fun onActivityStopped(p0: Activity) {
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
    }

    override fun onActivityDestroyed(p0: Activity) {
    }

    override fun onFragmentPreAttached(fm: FragmentManager, f: Fragment, context: Context) {
        if (f is InjectableScreen) {
            f.injectDependencies(locator)
        }
    }

    fun registerSelf(application: Application) {
        application.registerActivityLifecycleCallbacks(this)
    }
}
