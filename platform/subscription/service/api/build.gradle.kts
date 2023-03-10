plugins {
    kotlin("jvm")
}

dependencies {
    api(project(":platform:component:plugin:api"))
    api(project(":platform:subscription:data:api"))
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
}
