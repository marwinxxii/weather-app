plugins {
    id("com.android.library")
    id("kotlin-android")
    kotlin("plugin.serialization")
    id("kotlin-kapt")
}

android {
    namespace = "aa.weather.persisted.storage"
    defaultConfig {
        compileSdk = 33
        minSdk = 24
    }
}

dependencies {
    api(project(":platform:persisted-storage:api"))
    implementation(project(":platform:component:plugin:api"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0-RC")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    kapt("com.google.dagger:dagger-compiler:2.45")
    implementation("com.google.dagger:dagger:2.45")
}
