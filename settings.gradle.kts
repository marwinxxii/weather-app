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
include(":entities:location:api")
include(":entities:location:implementation")
include(":entities:weather:api")
include(":entities:weather:repository:implementation")
include(":platform:component:plugin:api")
include(":platform:navigation:navigator:api")
include(":platform:navigation:navigator:kernel:implementation")
include(":platform:navigation:plugin:api")
include(":platform:persisted-storage:api")
include(":platform:persisted-storage:implementation")
include(":platform:screen:api")
include(":platform:subscription:data:api")
include(":platform:subscription:service:api")
include(":platform:subscription:service:kernel:implementation")
include(":platform:subscription:service:plugin:api")
include(":screens:locations:screen:implementation")
include(":screens:location-weather:plugin:api")
include(":screens:location-weather:plugin:plugins")
include(":screens:location-weather:screen")
