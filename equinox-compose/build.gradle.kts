@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.vanniktech.mavenPublish)
    alias(libs.plugins.compose.compiler)
}

group = "com.tecknobit.equinoxcompose"
version = "1.0.6"

repositories {
    google()
    mavenCentral()
}

kotlin {
    jvm {
        compilations.all {
            this@jvm.compilerOptions {
                jvmTarget.set(JvmTarget.JVM_18)
            }
        }
    }
    androidTarget {
        publishLibraryVariants("release", "debug")
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_18)
        }
    }

    sourceSets {
        all {
            languageSettings {
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            }
        }
        val commonMain by getting {
            dependencies {
                implementation(compose.components.resources)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                implementation(libs.lifecycle.viewmodel.compose)
                // implementation(libs.apimanager) TODO: CHECK TO REMOVE
                implementation(libs.kermit)
                implementation(libs.kmpalette.core)
                implementation(project(":equinox-core"))
            }
        }
        val jvmMain by getting {
            dependencies {
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.connectivity.core)
                implementation(libs.connectivity.android)
                implementation(libs.connectivity.compose.device)
                implementation(libs.startup.runtime)
            }
        }
    }
}

android {
    namespace = "io.github.n7ghtm4r3"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
}

mavenPublishing {
    configure(
        KotlinMultiplatform(
            javadocJar = JavadocJar.Dokka("dokkaHtml"),
            sourcesJar = true,
            androidVariantsToPublish = listOf("release"),
        )
    )
    coordinates(
        groupId = "io.github.n7ghtm4r3",
        artifactId = "Equinox-Compose",
        version = "1.0.6"
    )
    pom {
        name.set("Equinox-Compose")
        description.set("Utilities for clients with an architecture based on SpringBoot and Kotlin Multiplatform frameworks. Is a support library to implement some utilities for the clients and some default composable such OutlinedTextField, AlertDialogs and different others")
        inceptionYear.set("2025")
        url.set("https://github.com/N7ghtm4r3/Equinox")

        licenses {
            license {
                name.set("Apache License, Version 2.0")
                url.set("https://opensource.org/license/apache-2-0")
            }
        }
        developers {
            developer {
                id.set("N7ghtm4r3")
                name.set("Manuel Maurizio")
                email.set("maurizio.manuel2003@gmail.com")
                url.set("https://github.com/N7ghtm4r3")
            }
        }
        scm {
            url.set("https://github.com/N7ghtm4r3/Equinox")
        }
    }
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.tecknobit.equinoxcompose.resources"
    generateResClass = always
}
