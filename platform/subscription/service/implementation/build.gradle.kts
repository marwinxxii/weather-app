plugins {
    kotlin("jvm")
    id("kotlin-kapt")
}

dependencies {
    api(project(":platform:subscription:service:api"))
    implementation("com.google.dagger:dagger:2.45")
    kapt("com.google.dagger:dagger-compiler:2.45")

    testImplementation(kotlin("test"))
}
