import pl.allegro.tech.build.axion.release.domain.VersionConfig
import pl.allegro.tech.build.axion.release.domain.properties.VersionProperties

plugins {
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.androidKotlinMultiplatformLibrary) apply false
    alias(libs.plugins.dokka) apply false
    alias(libs.plugins.vanniktech.mavenPublish) apply false
    alias(libs.plugins.axion)
}

allprojects {
    val module = this

    pluginManager.withPlugin("pl.allegro.tech.build.axion-release") {
        module.extensions.configure<VersionConfig> {
            tag.prefix.set(if (module == rootProject) "v" else module.name)
            tag.versionSeparator.set(if (module == rootProject) "" else "/")
            tag.initialVersion { _, position ->
                if (position.branch == "develop") "1.0.0-rc.1" else "1.0.0"
            }
            useHighestVersion.set(true)
            versionCreator("simple")
            versionIncrementer("incrementPrerelease", mapOf("initialPreReleaseIfNotOnPrerelease" to "rc.1"))
            branchVersionIncrementer.put("main", VersionProperties.Incrementer { context ->
                if (context.currentVersion.isPreRelease)
                    context.currentVersion.toStableVersion()
                else
                    context.currentVersion.nextPatchVersion()
            })
            releaseOnlyOnReleaseBranches.set(true)
            releaseBranchNames.set(setOf("main", "develop"))
            repository.pushTagsOnly.set(true)
            repository.customPassword.set(
                providers.environmentVariable("AXION_TOKEN").orElse(providers.gradleProperty("release.customPassword"))
            )
            monorepo {
                include(listOf("build.gradle.kts", "settings.gradle.kts", "gradle.properties", "gradle", "gradlew", "gradlew.bat"))
            }
        }

        module.version = module.extensions.getByType<VersionConfig>().version
    }
}

tasks.register<Exec>("serveMkDocs") {
    group = "documentation"
    description = "Start the MkDocs development server"

    workingDir(rootDir)
    commandLine(
        "docker", "run", "--rm",
        "-p", "8000:8000",
        "-v", "${rootDir.absolutePath}:/docs",
        "--entrypoint", "sh",
        "squidfunk/mkdocs-material",
        "-c", "pip install --no-cache-dir mkdocs-glightbox && exec mkdocs serve --dev-addr=0.0.0.0:8000"
    )
}
