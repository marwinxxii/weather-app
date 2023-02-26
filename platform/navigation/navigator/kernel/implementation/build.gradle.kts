plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    namespace = "aa.weather.navigator"

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
    api(project(":platform:navigation:navigator:screen:api"))
    api(project(":platform:navigation:plugin:api"))
    api(project(":platform:component:plugin:api"))

    kapt("com.google.dagger:dagger-compiler:2.45")
    implementation("com.google.dagger:dagger:2.45")

    implementation("androidx.fragment:fragment-ktx:1.5.5")
}
