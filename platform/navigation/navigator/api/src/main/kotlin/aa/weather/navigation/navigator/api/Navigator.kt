package aa.weather.navigation.navigator.api

interface Navigator {
    fun navigateTo(destination: Destination): Boolean
}
