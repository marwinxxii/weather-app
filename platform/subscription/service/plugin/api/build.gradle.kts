plugins {
    kotlin("jvm")
}

dependencies {
    api(project(":platform:subscription:data:api"))
    api(project(":platform:component:plugin:api"))

    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    implementation("com.google.dagger:dagger:2.45")
}
