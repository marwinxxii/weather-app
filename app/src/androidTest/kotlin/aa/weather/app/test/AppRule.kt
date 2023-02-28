package aa.weather.app.test

import aa.weather.app.WeatherApp
import aa.weather.network.rest.api.APIConfiguration
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.rules.ExternalResource
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.util.Locale

class AppRule : TestRule {
    val mockRule = RESTMockRule()

    private val rules = RuleChain
        .outerRule(
            object : ExternalResource() {
                override fun before() {
                    setAnimationsScale(0)
                }

                override fun after() {
                    setAnimationsScale(1)
                }
            }
        )
        .around(mockRule)

    override fun apply(base: Statement, description: Description): Statement =
        rules.apply(base, description)

    fun launch() {
        val app = ApplicationProvider.getApplicationContext<WeatherApp>()
        app.delegate.injectDependencies(
            apiConfiguration = APIConfiguration(
                key = "TEST_API_KEY",
                baseUrl = mockRule.baseUrl,
            ),
            locale = Locale.ENGLISH,
        )
        val packageName = app.packageName
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val device = UiDevice.getInstance(instrumentation)
        val intent = app.packageManager
            .getLaunchIntentForPackage(packageName)
            ?.apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK) }
        app.startActivity(intent)

        // Wait for the app to appear
        device.wait(
            Until.hasObject(By.pkg(packageName).depth(0)),
            200
        )
    }

    private fun setAnimationsScale(scale: Int) {
        setOf(
            "transition_animation_scale",
            "window_animation_scale",
            "animator_duration_scale",
        ).forEach {
            InstrumentationRegistry.getInstrumentation()
                .uiAutomation
                .executeShellCommand("settings put global $it $scale")
                .use { }
        }
    }
}
