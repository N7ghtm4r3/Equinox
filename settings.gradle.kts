rootProject.name = "Equinox"

pluginManagement {
    plugins {
        kotlin("jvm") version "2.0.0"
        kotlin("multiplatform") version "2.0.0"
    }
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

include("equinox-core")
include("equinox-backend")
