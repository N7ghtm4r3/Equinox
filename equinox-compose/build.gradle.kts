
import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.vanniktech.mavenPublish)
    alias(libs.plugins.compose.compiler)
    kotlin("plugin.serialization") version "2.1.0"
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

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "equinox-compose"
            isStatic = true
        }
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        binaries.executable()
        browser {
            webpackTask {
                dependencies {
                }
            }
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
                implementation(libs.kermit)
                //implementation(libs.kmpalette.core)
                implementation(libs.connectivity.core)
                implementation(libs.connectivity.compose)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.ktor.client.core)
                implementation(libs.kmprefs)
                implementation(libs.material3.window.size)
                implementation(project(":equinox-core"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(libs.connectivity.http)
                implementation(libs.connectivity.compose.http)
                implementation(libs.ktor.client.okhttp)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.startup.runtime)
                implementation(libs.connectivity.device)
                implementation(libs.connectivity.compose.device)
                implementation(libs.ktor.client.okhttp)
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(libs.connectivity.device)
                implementation(libs.connectivity.compose.device)
                implementation(libs.ktor.client.darwin)
            }
        }

        val wasmJsMain by getting {
            dependencies {
                implementation(libs.connectivity.http)
                implementation(libs.connectivity.compose.http)
                implementation(libs.ktor.client.js)
            }
        }

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
        artifactId = "equinox-compose",
        version = "1.0.6"
    )
    pom {
        name.set("Equinox Compose")
        description.set("Utilities for Kotlin Multiplatform applications such components, apis for Android, Desktop, iOS and Web platforms")
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

android {
    namespace = "com.tecknobit.equinoxcompose"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
}