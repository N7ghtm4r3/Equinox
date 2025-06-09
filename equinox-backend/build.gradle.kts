import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinJvm
import com.vanniktech.maven.publish.SonatypeHost

plugins {
    id("java")
    kotlin("jvm")
    id("org.springframework.boot") version "3.2.3"
    alias(libs.plugins.vanniktech.mavenPublish)
}

apply(plugin = "io.spring.dependency-management")

group = "com.tecknobit.equinoxbackend"
version = "1.1.3"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://repo.clojars.org")
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.boot.spring.boot.starter.web)
    implementation(libs.jackson.databind)
    implementation(libs.apimanager)
    implementation(libs.json)
    implementation(project(":equinox-core"))
}

mavenPublishing {
    configure(
        platform = KotlinJvm(
            javadocJar = JavadocJar.Dokka("dokkaHtml"),
            sourcesJar = true
        )
    )
    coordinates(
        groupId = "io.github.n7ghtm4r3",
        artifactId = "equinox-backend",
        version = "1.1.3"
    )
    pom {
        name.set("Equinox")
        description.set("Utilities for backend services based on Springboot framework. Is a support library to implement some utilities both for backend and for client also who comunicate with that Springboot backend")
        inceptionYear.set("2025")
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
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()
}

kotlin {
    jvmToolchain(18)
}

java {
    sourceCompatibility = JavaVersion.VERSION_18
    targetCompatibility = JavaVersion.VERSION_18
}