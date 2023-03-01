package aa.weather.app.test

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiScrollable
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import org.junit.Assert.assertTrue

interface WeatherRobot {
    fun assertWeatherIsShown(city: String, temperature: String, conditions: String)

    fun clickPreferences()

    fun assertErrorIsShown(message: String)

    fun clickRetry()
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
    private val retryButton = UiSelector().text("Retry")

    override fun assertWeatherIsShown(city: String, temperature: String, conditions: String) {
        val scrollable = UiScrollable(UiSelector().scrollable(true))

        scrollable
            .getChild(UiSelector().text(city))
            .assertExists("City view with text $city does not exist")

        scrollable
            .getChild(UiSelector().text(temperature))
            .assertExists("Temperature view is shown with text $temperature")

        scrollable
            .getChild(UiSelector().text(conditions))
            .assertExists("Conditions view with text $conditions does not exist")
    }

    override fun clickPreferences() {
        device.findObject(UiSelector().description(CONTENT_DESC_PREFERENCES))
            .click()
    }

    override fun assertErrorIsShown(message: String) {
        // 5s is timeout for loading data on the screen
        device.findObject(UiSelector().resourceId("ErrorRetry"))
            .waitForExists(5000)

        device.findObject(UiSelector().text(message))
            .assertExists("Error text view with text '$message' does not exist")

        device.findObject(retryButton)
            .assertExists("Retry button does not exist")
    }

    override fun clickRetry() {
        device.findObject(retryButton).click()
    }
}

private fun UiObject.assertExists(message: String) {
    assertTrue(message, exists())
}
