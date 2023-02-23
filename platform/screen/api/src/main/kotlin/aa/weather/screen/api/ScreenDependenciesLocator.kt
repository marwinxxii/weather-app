package aa.weather.screen.api

interface ScreenDependenciesLocator {
    fun <T: Any> getOrCreateDependency(dependencyClass: Class<T>): T
}
