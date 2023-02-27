plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "aa.weather.app"

    defaultConfig {
        applicationId = "aa.weather.app"
        compileSdk = 33
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "WEATHER_API_KEY", "\"" + (findProperty("WEATHER_API_KEY") ?: "") + "\"")
        buildConfigField("String", "API_BASE_URL", "\"" + (findProperty("API_BASE_URL") ?: "https://api.weatherapi.com/") + "\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":platform:screen:api"))
    implementation(project(":platform:component:plugin:api"))
    implementation(project(":platform:navigation:navigator:kernel:implementation"))
    implementation(project(":platform:network:rest:implementation"))
    implementation(project(":platform:persisted-storage:implementation"))
    implementation(project(":platform:subscription:service:kernel:implementation"))
    implementation(project(":entities:location:implementation"))
    implementation(project(":entities:weather:repository:implementation"))
    implementation(project(":screens:location-weather:screen"))
    implementation(project(":screens:locations:screen:implementation"))

    implementation("com.google.dagger:dagger:2.45")
    kapt("com.google.dagger:dagger-compiler:2.45")

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.2")

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.0-beta01")

    androidTestImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test.uiautomator:uiautomator:2.3.0-alpha02")
    androidTestImplementation("com.squareup.okhttp3:mockwebserver:4.10.0")
}

tasks.register("runE2ETests") {
    doFirst {
        val appId = "aa.weather.app"
        exec {
            executable = "adb"
            args = listOf("shell", "am", "force-stop", appId)
            isIgnoreExitValue = false
        }
        exec {
            executable = "adb"
            args = listOf("shell", "pm", "clear", appId)
            isIgnoreExitValue = false
        }
    }
    finalizedBy("connectedDebugAndroidTest")
}
