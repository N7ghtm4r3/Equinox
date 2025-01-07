pluginManagement {
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
        // TODO: REMOVE IN THE NEXT VERSION
        maven("https://jitpack.io")
        // TODO: REMOVE IN THE NEXT VERSION
        maven("https://repo.clojars.org")
    }
}

rootProject.name = "Equinox"
include("equinox-core")
include("equinox-compose")
include("equinox-backend")
