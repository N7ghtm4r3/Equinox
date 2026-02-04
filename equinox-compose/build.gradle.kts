import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.dokka)
    alias(libs.plugins.vanniktech.mavenPublish)
}

group = "com.tecknobit.equinoxcompose"
version = "1.1.9"

repositories {
    google()
    mavenCentral()
}

kotlin {
    androidLibrary {
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        namespace = "com.tecknobit.equinoxcompose"
        experimentalProperties["android.experimental.kmp.enableAndroidResources"] = true

        compilations {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_18)
            }
        }

    }

    jvm {
        compilations.all {
            this@jvm.compilerOptions {
                jvmTarget.set(JvmTarget.JVM_18)
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
        macosX64(),
        macosArm64()
    ).forEach { appleTarget ->
        compilerOptions {
            freeCompilerArgs.add("-Xklib-duplicated-unique-name-strategy=allow-all-with-warning")
        }
        appleTarget.binaries.framework {
            baseName = "equinox-compose"
            isStatic = true
        }
    }

    js {
        browser()
        binaries.library()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        binaries.library()
        browser {
            webpackTask {

            }
        }
    }

    sourceSets {
        applyDefaultHierarchyTemplate()

        val androidMain by getting {
            dependencies {
                implementation(libs.connectivity.compose)
                implementation(libs.connectivity.device)
                implementation(libs.connectivity.compose.device)
                implementation(libs.ktor.client.okhttp)
            }
        }

        val commonMain by getting {
            dependencies {
                implementation(compose.components.resources)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                implementation(libs.lifecycle.runtime.compose)
                implementation(libs.lifecycle.viewmodel.compose)
                implementation(libs.kermit)
                //implementation(libs.kmpalette.core)
                implementation(libs.connectivity.core)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.ktor.client.core)
                implementation(libs.kmprefs)
                implementation(libs.material3.window.size)
                implementation(project(":equinox-core"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(libs.connectivity.compose)
                implementation(libs.connectivity.http)
                implementation(libs.connectivity.compose.http)
                implementation(libs.ktor.client.okhttp)
            }
        }

        val appleMain by getting {
            dependencies {
                implementation(libs.ktor.client.cio)
            }
        }

        val iosMain by getting {
            dependencies {
                implementation(libs.connectivity.compose)
                implementation(libs.connectivity.device)
                implementation(libs.connectivity.compose.device)
            }
        }

        val macosMain by getting {
            dependencies {
                implementation(libs.connectivity.http)
            }
        }

        val webMain by getting {
            dependencies {
                implementation(libs.connectivity.compose)
                implementation(libs.connectivity.http)
                implementation(libs.connectivity.compose.http)
                implementation(libs.ktor.client.js)
                implementation(libs.kotlin.browser)
            }
        }

        val jsMain by getting {
            dependencies {
            }
        }

        val wasmJsMain by getting {
            dependencies {
            }
        }
    }

    jvmToolchain(18)
}

mavenPublishing {
    configure(
        KotlinMultiplatform(
            javadocJar = JavadocJar.Dokka("dokkaGenerate"),
            androidVariantsToPublish = listOf("release"),
        )
    )
    coordinates(
        groupId = "io.github.n7ghtm4r3",
        artifactId = "equinox-compose",
        version = "1.1.9"
    )
    pom {
        name.set("Equinox Compose")
        description.set("Utilities for Kotlin Multiplatform applications such components, apis for Android, Desktop, iOS and Web platforms")
        inceptionYear.set("2026")
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
    publishToMavenCentral()
    signAllPublications()
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.tecknobit.equinoxcompose.resources"
    generateResClass = always
}