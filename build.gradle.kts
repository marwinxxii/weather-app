plugins {
    val agpVersion = "7.4.1"
    id("com.android.application") version agpVersion apply false
    id("com.android.library") version agpVersion apply false
    val kotlinVersion = "1.8.0"
    id("org.jetbrains.kotlin.android") version kotlinVersion apply false
    id("org.jetbrains.kotlin.jvm") version kotlinVersion apply false
    id("org.jetbrains.kotlin.plugin.serialization") version kotlinVersion apply false
    id("app.cash.molecule") version "0.7.0" apply false
    id("io.gitlab.arturbosch.detekt") version "1.22.0"
}

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.allWarningsAsErrors = true
    }

    dependencies {
        detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.22.0")
    }

    detekt {
        autoCorrect = true
        buildUponDefaultConfig = true
        config = files("$rootDir/gradle/detekt-config.yaml")
    }
}
