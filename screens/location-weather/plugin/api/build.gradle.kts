plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "aa.weather.screens.weather.plugin.api"
    defaultConfig {
        compileSdk = 33
        minSdk = 24
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.1"
    }
}

dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2023.01.00")
    implementation(composeBom)
    api("androidx.compose.runtime:runtime")
}