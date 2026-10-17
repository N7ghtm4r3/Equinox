plugins {
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.androidKotlinMultiplatformLibrary) apply false
    alias(libs.plugins.dokka) apply false
    alias(libs.plugins.vanniktech.mavenPublish) apply false
}

tasks.register<Exec>("serveMkDocs") {
    group = "documentation"
    description = "Start the MkDocs development server"

    workingDir(rootDir)
    commandLine(
        "docker", "run", "--rm",
        "-p", "8000:8000",
        "-v", "${rootDir.absolutePath}:/docs",
        "squidfunk/mkdocs-material"
    )
}