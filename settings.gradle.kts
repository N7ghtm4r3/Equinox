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
        maven("https://jitpack.io")
        maven("https://repo.clojars.org")
    }
}

rootProject.name = "Equinox"
include("equinox-core")
include("equinox-compose")
include("equinox-backend")
include("equinox-navigation")
include("equinox-miscellaneous:lazy-pagination-compose-ops")
include("equinox-miscellaneous:navigation-compose-util")
