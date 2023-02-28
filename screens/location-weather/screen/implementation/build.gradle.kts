plugins {
    id("com.android.library")
    id("kotlin-android")
    id("app.cash.molecule")
    id("kotlin-kapt")
}

android {
    namespace = "aa.weather.screens.weather"
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

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    api(project(":platform:navigation:plugin:api"))
    implementation(project(":entities:location:api"))
    implementation(project(":entities:weather:api"))
    implementation(project(":platform:screen:api"))
    implementation(project(":platform:subscription:service:api"))
    implementation(project(":platform:navigation:navigator:screen:api"))

    implementation("com.google.dagger:dagger:2.45")
    kapt("com.google.dagger:dagger-compiler:2.45")
    implementation("androidx.fragment:fragment-ktx:1.5.5")
    implementation(project(":screens:location-weather:plugin:plugins"))
    val composeBom = platform("androidx.compose:compose-bom:2023.01.00")
    implementation(composeBom)
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.0-beta01")

    testImplementation(kotlin("test"))
    //testImplementation("androidx.compose.ui:ui-test:1.3.3")
}

//tasks.test {
//    useJUnitPlatform()
//}
