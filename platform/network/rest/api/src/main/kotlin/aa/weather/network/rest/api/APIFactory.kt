package aa.weather.network.rest.api

interface APIFactory {
    fun <T> createRESTAPI(apiClass: Class<T>): T
}
