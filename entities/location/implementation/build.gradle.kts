plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("kotlin-kapt")
}

dependencies {
    api(project(":entities:location:api"))
    implementation(project(":platform:persisted-storage:api"))
    implementation(project(":platform:subscription:service:plugin:api"))

    kapt("com.google.dagger:dagger-compiler:2.45")
    implementation("com.google.dagger:dagger:2.45")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0-RC")
}
