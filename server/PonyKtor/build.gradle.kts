import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.10"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10"
    id("com.google.devtools.ksp") version "1.9.10-1.0.13"
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
    implementation("me.tatarka.inject:kotlin-inject-runtime:0.6.1")
    ksp("me.tatarka.inject:kotlin-inject-compiler-ksp:0.6.1")

    implementation("io.ktor:ktor-server-netty:1.5.3")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("io.ktor:ktor-serialization:1.5.3")

    implementation("org.jetbrains.exposed:exposed-core:0.30.2")
    implementation("org.jetbrains.exposed:exposed-dao:0.30.2")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.30.2")
    implementation("org.jetbrains.exposed:exposed-java-time:0.30.2")
    implementation("org.xerial:sqlite-jdbc:3.34.0")
    implementation( "com.zaxxer:HikariCP:3.4.5")

    kspTest("me.tatarka.inject:kotlin-inject-compiler-ksp:0.6.1")
    testImplementation("junit:junit:4.13.2")
    testImplementation("io.ktor:ktor-server-test-host:1.5.3")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.24")
}

tasks.wrapper {
    jarFile = file("../../gradle/wrapper/gradle-wrapper.jar")
}