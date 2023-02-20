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
include(":app")
include(":entities:weather:api")
include(":entities:weather:repository:implementation")
include(":platform:persisted-storage:api")
include(":platform:persisted-storage:implementation")
include(":platform:subscription:data:api")
include(":platform:subscription:service:api")
include(":platform:subscription:service:implementation")
include(":screens:location-weather:plugin:api")
include(":screens:location-weather:plugin:plugins")
include(":screens:location-weather:screen")
