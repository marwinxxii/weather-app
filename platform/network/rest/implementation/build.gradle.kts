plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    namespace = "aa.weather.rest"
    defaultConfig {
        compileSdk = 33
        minSdk = 24
    }
}

dependencies {
    api(project(":platform:network:rest:api"))
    implementation(project(":platform:component:plugin:api"))

    kapt("com.google.dagger:dagger-compiler:2.45")
    implementation("com.google.dagger:dagger:2.45")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.10.0"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0-RC")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
}
