package aa.weather.app.test

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until

internal interface LocationsRobot {
    fun selectCity(city: String)
}

internal fun onLocationsScreen(actions: LocationsRobot.() -> Unit) {
    val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    device.wait(
        Until.hasObject(By.text("Select location:")),
        250
    )
    LocationsRobotImpl(device).actions()
}

private class LocationsRobotImpl(private val uiDevice: UiDevice) : LocationsRobot {
    override fun selectCity(city: String) {
        uiDevice.findObject(UiSelector().text(city))
            .click()
    }
}
