plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("kotlin-kapt")
}

dependencies {
    api(project(":entities:weather:api"))
    api(project(":platform:subscription:service:api"))
    implementation(project(":platform:persisted-storage:api"))
    implementation("com.google.dagger:dagger:2.45")
    kapt("com.google.dagger:dagger-compiler:2.45")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0-RC")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")

    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.10.0"))
    implementation("com.squareup.okhttp3:okhttp")

    testImplementation(kotlin("test"))
}
