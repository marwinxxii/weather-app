package aa.weather.screen.api

import aa.weather.component.di.AppPlugin

interface ScreenDependenciesLocator {
    fun <T: AppPlugin> getOrCreateDependency(dependencyClass: Class<T>): T
}
