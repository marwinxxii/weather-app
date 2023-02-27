plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "aa.weather.navigation.navigator.plugin.api"

    defaultConfig {
        compileSdk = 33
        minSdk = 24
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    api(project(":platform:navigation:navigator:api"))

    implementation("androidx.fragment:fragment-ktx:1.5.5")
}
