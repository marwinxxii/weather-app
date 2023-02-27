package aa.weather.app.test

import org.junit.Rule
import org.junit.Test

class FlowTest {
    @get:Rule
    val appRule = AppRule()

    @Test
    fun test_main_flow() {
        appRule.mockRule.setupMocks()
        appRule.launch()
        onLocationsScreen {
            selectCity("Berlin")
        }
        onWeatherScreen {
            assertWeatherIsShown(
                city = "Berlin",
                temperature = "0°",
                conditions = "Clear",
            )

            clickPreferences()
        }

        onLocationsScreen {
            selectCity("Stockholm")
        }

        onWeatherScreen {
            assertWeatherIsShown(
                city = "Stockholm",
                temperature = "2°",
                conditions = "Clear",
            )
        }
    }
}

private fun RESTMockRule.setupMocks() {
    addResponse(
        path = "/v1/current.json",
        queryParameters = mapOf(
            "key" to "TEST_API_KEY",
            "q" to "Berlin",
        ),
        responseFile = "mock_berlin_latest_weather.json",
    )
    addResponse(
        path = "/v1/forecast.json",
        queryParameters = mapOf(
            "key" to "TEST_API_KEY",
            "q" to "Berlin",
            "days" to "14",
        ),
        responseFile = "mock_berlin_forecast.json",
    )
    addResponse(
        path = "/v1/current.json",
        queryParameters = mapOf(
            "key" to "TEST_API_KEY",
            "q" to "Stockholm",
        ),
        responseFile = "mock_stockholm_latest_weather.json",
    )
    addResponse(
        path = "/v1/forecast.json",
        queryParameters = mapOf(
            "key" to "TEST_API_KEY",
            "q" to "Stockholm",
            "days" to "14",
        ),
        responseFile = "mock_stockholm_forecast.json",
    )
}
