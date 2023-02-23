plugins {
    kotlin("jvm")
}

dependencies {
    api(project(":platform:subscription:data:api"))
    implementation(project(":entities:location:api"))
}
