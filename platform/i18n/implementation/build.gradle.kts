plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    namespace = "aa.weather.i18n"
    defaultConfig {
        compileSdk = 33
        minSdk = 24
    }
}

dependencies {
    api(project(":platform:i18n:api"))

    kapt("com.google.dagger:dagger-compiler:2.45")
    implementation("com.google.dagger:dagger:2.45")
}
