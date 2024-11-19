import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinJvm
import com.vanniktech.maven.publish.SonatypeHost

plugins {
    id("java")
    kotlin("jvm")
    id("org.springframework.boot") version "3.2.3"
    id("com.vanniktech.maven.publish") version "0.30.0"
}

apply(plugin = "io.spring.dependency-management")

group = "com.tecknobit.equinox"
version = "1.0.5"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://repo.clojars.org")
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.2.3")
    implementation("org.springframework.boot:spring-boot-starter-web:3.2.3")
    implementation("org.springframework:spring-web:6.1.12")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.4")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.github.N7ghtm4r3:APIManager:2.2.4")
    implementation("org.json:json:20240303")
    implementation("com.github.N7ghtm4r3:Mantis:1.0.0")
    implementation("commons-validator:commons-validator:1.7")
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
        version = "1.0.5"
    )
    pom {
        name.set("Equinox")
        description.set("Utilities for backend services based on Springboot framework. Is a support library to implement some utilities both for backend and for client also who comunicate with that Springboot backend")
        inceptionYear.set("2024")
        url.set("https://github.com/N7ghtm4r3/Equinox")

        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/license/mit")
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

/*tasks.withType<DokkaTask>().configureEach {
    outputDirectory.set(layout.projectDirectory.dir("docs"))
    dokkaSourceSets.configureEach {
        sourceRoots.from(file("src/main/kotlin"))
        sourceRoots.from(file("src/main/java"))
        includeNonPublic.set(true)
        documentedVisibilities.set(setOf(PUBLIC, PROTECTED, PRIVATE))
    }
    pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
        footerMessage = "(c) 2024 Tecknobit"
    }
}*/