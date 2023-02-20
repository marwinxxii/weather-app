plugins {
    id("com.android.library")
    id("kotlin-android")
    kotlin("plugin.serialization")
    id("kotlin-kapt")
}

android {
    namespace = "aa.weather.app.persisted_storage"
    defaultConfig {
        compileSdk = 33
        minSdk = 24
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    api(project(":platform:persisted-storage:api"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0-RC")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("com.google.dagger:dagger:2.45")
    kapt("com.google.dagger:dagger-compiler:2.45")
}
