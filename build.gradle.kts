plugins {
    id("java")
    id("maven-publish")
    kotlin("jvm")
}

group = "com.tecknobit"
version = "1.0.2"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://repo.clojars.org")
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.2.3")
    implementation("org.springframework.boot:spring-boot-starter-web:3.2.3")
    implementation("org.springframework:spring-web:6.1.5")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.4")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.github.N7ghtm4r3:APIManager:2.2.3")
    implementation("org.json:json:20231013")
    implementation("com.github.N7ghtm4r3:Mantis:1.0.0")
    implementation("commons-validator:commons-validator:1.7")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = "com.tecknobit.equinox"
                artifactId = "Equinox"
                version = "1.0.2"
                from(components["java"])
            }
        }
    }
}

kotlin {
    jvmToolchain(18)
}