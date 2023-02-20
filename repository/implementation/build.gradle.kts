plugins {
    id("kotlin")
    id("kotlin-kapt")
}

dependencies {
    api(project(":repository:api"))
    api(project(":subscription:api"))
    implementation(project(":subscription:implementation"))
    implementation("com.google.dagger:dagger:2.45")
    kapt("com.google.dagger:dagger-compiler:2.45")

    testImplementation(kotlin("test"))
}
