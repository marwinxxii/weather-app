plugins {
    kotlin("jvm")
    id("kotlin-kapt")
}

dependencies {
    api(project(":platform:subscription:service:api"))
    api(project(":platform:subscription:service:plugin:api"))
    implementation(project(":platform:component:plugin:api"))

    kapt("com.google.dagger:dagger-compiler:2.45")
    implementation("com.google.dagger:dagger:2.45")

    testImplementation(kotlin("test"))
}
