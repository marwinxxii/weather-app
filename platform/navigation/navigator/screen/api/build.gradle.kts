plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "aa.weather.navigation.navigator.screen.api"
    defaultConfig {
        compileSdk = 33
        minSdk = 24
    }
}

dependencies {
    api(project(":platform:navigation:navigator:api"))
    api(project(":platform:component:plugin:api"))

    api("androidx.fragment:fragment-ktx:1.5.5")
}
