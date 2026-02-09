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

group = "com.tecknobit.equinoxmisc.lazypaginationcomposeops"
version = "1.0.3"

repositories {
    google()
    mavenCentral()
}

kotlin {
    androidLibrary {
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        namespace = "com.tecknobit.equinoxmisc.lazypaginationcomposeops"
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
        // macosX64(),
        macosArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "equinoxmisc-lazy-pagination-compose-ops"
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
            }
        }

        val commonMain by getting {
            dependencies {
                implementation(compose.components.resources)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(libs.kotlinx.serialization.json)
                implementation(project(":equinox-core"))
                implementation(libs.lazy.pagination.compose)
            }
        }

        val jvmMain by getting {
            dependencies {
            }
        }

        val appleMain by getting {
            dependencies {
            }
        }

        val webMain by getting {
            dependencies {
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
        platform = KotlinMultiplatform(
            javadocJar = JavadocJar.Dokka("dokkaGenerate"),
            androidVariantsToPublish = listOf("release"),
        )
    )
    coordinates(
        groupId = "io.github.n7ghtm4r3",
        artifactId = "equinoxmisc-lazy-pagination-compose-ops",
        version = "1.0.3"
    )
    pom {
        name.set("lazy-pagination-compose-ops")
        description.set("Integration of the lazy-pagination-compose library with the in-place operations")
        inceptionYear.set("2026")
        url.set("https://github.com/N7ghtm4r3/Equinox")

        licenses {
            license {
                name.set("APACHE2")
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