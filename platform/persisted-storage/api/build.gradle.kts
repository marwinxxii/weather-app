plugins {
    kotlin("jvm")
}

dependencies {
    api(project(":platform:component:plugin:api"))
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0-RC")
}
