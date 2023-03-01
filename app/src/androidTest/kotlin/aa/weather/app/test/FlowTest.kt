package aa.weather.app.test

import org.junit.Rule
import org.junit.Test

private object BerlinWeather {
    const val city = "Berlin"
    const val temperature = "2°"
    const val conditions = "Clear"
}

private val QUERY_API_KEY = "key" to "TEST_API_KEY"
private val QUERY_LANGUAGE = "lang" to "en"
private val QUERY_DAYS = "days" to "14"

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
                city = BerlinWeather.city,
                temperature = BerlinWeather.temperature,
                conditions = BerlinWeather.conditions,
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

    @Test
    fun test_retry_flow() {
        appRule.mockRule
            .addResponse("/v1/current.json", httpCode = 500)
            .addResponse("/v1/forecast.json", httpCode = 500)
        appRule.launch()
        onLocationsScreen {
            selectCity("Berlin")
        }
        onWeatherScreen {
            assertErrorIsShown(message = "Could not load weather data")

            appRule.mockRule
                .removeAllMocks()
                .mockSuccessfulBerlinResponses()
            clickRetry()

            assertWeatherIsShown(
                city = BerlinWeather.city,
                temperature = BerlinWeather.temperature,
                conditions = BerlinWeather.conditions,
            )
        }
    }
}

private fun RESTMockRule.setupMocks() {
    mockSuccessfulBerlinResponses()
    addResponse(
        path = "/v1/current.json",
        queryParameters = mapOf(
            QUERY_API_KEY,
            "q" to "Stockholm",
            QUERY_LANGUAGE,
        ),
        responseFile = "mock_stockholm_latest_weather.json",
    )
    addResponse(
        path = "/v1/forecast.json",
        queryParameters = mapOf(
            QUERY_API_KEY,
            "q" to "Stockholm",
            QUERY_DAYS,
            QUERY_LANGUAGE,
        ),
        responseFile = "mock_stockholm_forecast.json",
    )
}

private fun RESTMockRule.mockSuccessfulBerlinResponses() {
    addResponse(
        path = "/v1/current.json",
        queryParameters = mapOf(
            QUERY_API_KEY,
            "q" to "Berlin",
            QUERY_LANGUAGE,
        ),
        responseFile = "mock_berlin_latest_weather.json",
    )
    addResponse(
        path = "/v1/forecast.json",
        queryParameters = mapOf(
            QUERY_API_KEY,
            "q" to "Berlin",
            QUERY_DAYS,
            QUERY_LANGUAGE,
        ),
        responseFile = "mock_berlin_forecast.json",
    )
}
