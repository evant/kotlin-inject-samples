import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "2.0.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
    id("com.google.devtools.ksp") version "2.0.0-1.0.22"
}

group = "me.tatarka.inject"
version = "1.0-SNAPSHOT"

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_11
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation("me.tatarka.inject:kotlin-inject-runtime:0.7.1")
    ksp("me.tatarka.inject:kotlin-inject-compiler-ksp:0.7.1")

    implementation("io.ktor:ktor-server-netty:2.3.11")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.11")
    implementation("io.ktor:ktor-server-content-negotiation:2.3.11")
    implementation("io.ktor:ktor-server-status-pages:2.3.11")
    implementation("ch.qos.logback:logback-classic:1.4.14")

    implementation("org.jetbrains.exposed:exposed-core:0.30.2")
    implementation("org.jetbrains.exposed:exposed-dao:0.30.2")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.30.2")
    implementation("org.jetbrains.exposed:exposed-java-time:0.30.2")
    implementation("org.xerial:sqlite-jdbc:3.41.2.2")
    implementation( "com.zaxxer:HikariCP:3.4.5")

    kspTest("me.tatarka.inject:kotlin-inject-compiler-ksp:0.7.1")
    testImplementation("junit:junit:4.13.2")
    testImplementation("io.ktor:ktor-server-test-host:2.3.11")
    testImplementation("com.willowtreeapps.assertk:assertk:0.28.1")
}

tasks.wrapper {
    jarFile = file("../../gradle/wrapper/gradle-wrapper.jar")
}