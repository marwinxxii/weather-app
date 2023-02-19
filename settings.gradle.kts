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
include(":screens:weather:plugin:api")
include(":screens:weather:plugin:plugins")
include(":screens:weather:screen")
include(":app")
