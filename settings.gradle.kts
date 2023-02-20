pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Weather app"
include(":repository:api")
include(":repository:implementation")
include(":screens:location-weather:plugin:api")
include(":screens:location-weather:plugin:plugins")
include(":screens:location-weather:screen")
include(":subscription:api")
include(":subscription:implementation")
include(":app")
