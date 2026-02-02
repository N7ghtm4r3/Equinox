plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.androidKotlinMultiplatformLibrary) apply false
    alias(libs.plugins.vanniktech.mavenPublish) apply false
}

buildscript {
    dependencies {
        classpath(libs.dokka.base)
    }
}

// TODO: TO SETUP DOKKA
//subprojects {
//    apply(plugin = "org.jetbrains.dokka")
//    tasks.withType<DokkaTaskPartial>().configureEach {
//        dokkaSourceSets.configureEach {
//            includeNonPublic.set(true)
//            documentedVisibilities.set(setOf(PUBLIC, PROTECTED, PRIVATE))
//        }
//    }
//}
//
//tasks.withType<DokkaMultiModuleTask> {
//    outputDirectory.set(layout.projectDirectory.dir("doks"))
//    pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
//        version = "1.1.9"
//        footerMessage = "(c) 2025 Tecknobit"
//    }
//}
