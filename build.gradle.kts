
import org.jetbrains.dokka.DokkaConfiguration.Visibility.*
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
import org.jetbrains.dokka.gradle.DokkaTaskPartial

plugins {
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.vanniktech.mavenPublish) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.dokka)
}

buildscript {
    dependencies {
        classpath(libs.dokka.base)
    }
}

subprojects {
    apply(plugin = "org.jetbrains.dokka")
    tasks.withType<DokkaTaskPartial>().configureEach {
        dokkaSourceSets.configureEach {
            includeNonPublic.set(true)
            documentedVisibilities.set(setOf(PUBLIC, PROTECTED, PRIVATE))
        }
    }
}

tasks.withType<DokkaMultiModuleTask> {
    outputDirectory.set(layout.projectDirectory.dir("docs"))
    pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
        version = "1.0.6"
        footerMessage = "(c) 2025 Tecknobit"
    }
}
