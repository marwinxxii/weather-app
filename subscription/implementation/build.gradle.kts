plugins {
    id("kotlin")
    id("kotlin-kapt")
}

dependencies {
    api(project(":subscription:api"))
    implementation("com.google.dagger:dagger:2.45")
    kapt("com.google.dagger:dagger-compiler:2.45")

    testImplementation(kotlin("test"))
}
