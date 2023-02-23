package aa.weather.screen.api

interface InjectableScreen {
    fun injectDependencies(locator: ScreenDependenciesLocator)
}
