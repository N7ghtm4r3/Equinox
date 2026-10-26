import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinJvm
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.repositories

plugins {
    id("java")
    kotlin("jvm")
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.dokka)
    alias(libs.plugins.vanniktech.mavenPublish)
}

group = "com.tecknobit.equinoxmisc.backendmembership"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation(project(":equinox-backend"))
    implementation(project(":equinox-core"))
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.boot.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.jackson.databind)
}

mavenPublishing {
    configure(
        platform = KotlinJvm(
            javadocJar = JavadocJar.Dokka("dokkaGenerate")
        )
    )
    coordinates(
        groupId = "io.github.n7ghtm4r3",
        artifactId = "equinoxmisc-backend-membership",
        version = "1.0.0"
    )
    pom {
        name.set("backend-membership")
        description.set("Member management extension for Spring Boot backends built with Equinox Backend")
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

kotlin {
    jvmToolchain(21)
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}
