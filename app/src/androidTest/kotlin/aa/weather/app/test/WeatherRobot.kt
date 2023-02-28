package aa.weather.app.test

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiScrollable
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import org.junit.Assert.assertTrue

interface WeatherRobot {
    fun assertWeatherIsShown(city: String, temperature: String, conditions: String)

    fun clickPreferences()
}

private const val CONTENT_DESC_PREFERENCES = "Preferences"

internal fun onWeatherScreen(actions: WeatherRobot.() -> Unit) {
    val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    device.wait(
        Until.hasObject(
            By
                .clazz("android.widget.Toolbar")
                .hasDescendant(By.desc(CONTENT_DESC_PREFERENCES))
        ),
        2000
    )
    WeatherRobotImpl(device).actions()
}

private class WeatherRobotImpl(private val device: UiDevice) : WeatherRobot {
    override fun assertWeatherIsShown(city: String, temperature: String, conditions: String) {
        val scrollable = UiScrollable(UiSelector().scrollable(true))

        scrollable
            .getChild(UiSelector().text(city))
            .also { assertTrue("City view is shown with text $city", it.exists()) }

        scrollable
            .getChild(UiSelector().text(temperature))
            .also {
                assertTrue("Temperature view is shown with text $temperature", it.exists())
            }

        scrollable
            .getChild(UiSelector().text(conditions))
            .also {
                assertTrue("Conditions view is shown with text $conditions", it.exists())
            }
    }

    override fun clickPreferences() {
        device.findObject(UiSelector().description(CONTENT_DESC_PREFERENCES))
            .click()
    }
}
