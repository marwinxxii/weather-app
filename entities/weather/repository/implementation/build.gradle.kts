plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("kotlin-kapt")
}

dependencies {
    api(project(":entities:weather:api"))
    implementation(project(":platform:component:plugin:api"))
    implementation(project(":platform:subscription:service:plugin:api"))
    implementation(project(":entities:location:api"))
    implementation(project(":platform:persisted-storage:api"))
    implementation(project(":platform:network:rest:api"))

    kapt("com.google.dagger:dagger-compiler:2.45")
    implementation("com.google.dagger:dagger:2.45")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0-RC")
}
