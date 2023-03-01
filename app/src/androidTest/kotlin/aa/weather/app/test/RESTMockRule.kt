package aa.weather.app.test

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.rules.ExternalResource

class RESTMockRule : ExternalResource() {
    private val mockWebServer by lazy { MockWebServer() }
    private val dispatcher = MockDispatcher()

    override fun before() {
        mockWebServer.dispatcher = dispatcher
        mockWebServer.start()
    }

    override fun after() {
        mockWebServer.shutdown()
    }

    val baseUrl: String get() = mockWebServer.url("/").toString()

    fun addResponse(
        path: String,
        queryParameters: Map<String, String>,
        responseFile: String,
    ) = apply {
        val response = MockResponse()
            .setResponseCode(200)
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .addHeader("Cache-Control", "no-cache")
            .setBody(readResource(responseFile))
        dispatcher.addResponse(ExpectedRequest(path, queryParameters), response)
    }

    fun removeAllMocks() = apply {
        dispatcher.removeAllMocks()
    }

    fun addResponse(path: String, httpCode: Int) = apply {
        val response = MockResponse()
            .setResponseCode(httpCode)
        dispatcher.addResponse(ExpectedRequest(path, queryParameters = null), response)
    }

    private fun readResource(fileName: String): String =
        javaClass.classLoader!!
            .getResourceAsStream(fileName)
            .reader()
            .readText()
}

private data class ExpectedRequest(
    val path: String,
    val queryParameters: Map<String, String>?,
)

private class MockDispatcher : Dispatcher() {
    private val mocks = linkedMapOf<ExpectedRequest, MockResponse>()

    override fun dispatch(request: RecordedRequest): MockResponse {
        val requestQueryParameters = request.queryParameters
        return mocks.entries.firstOrNull { (expected, _) ->
            expected.path == request.requestUrl?.encodedPath &&
                (expected.queryParameters?.let { it == requestQueryParameters } ?: true)
        }
            ?.value
            ?: error("No mock found for $request in $mocks")
    }

    fun addResponse(request: ExpectedRequest, response: MockResponse) = apply {
        mocks[request] = response
    }

    fun removeAllMocks() {
        mocks.keys.clear()
    }
}

private val RecordedRequest.queryParameters: Map<String, String>
    get() =
        requestUrl
            ?.queryParameterNames
            ?.mapNotNull { name -> requestUrl?.queryParameter(name)?.let { name to it } }
            ?.toMap()
            ?: emptyMap()
