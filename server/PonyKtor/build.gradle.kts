import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.32"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.4.32"
    kotlin("kapt") version "1.4.32"
}

group = "me.tatarka.inject"
version = "1.0-SNAPSHOT"

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation("me.tatarka.inject:kotlin-inject-runtime:0.3.3")
    kapt("me.tatarka.inject:kotlin-inject-compiler-kapt:0.3.3")

    implementation("io.ktor:ktor-server-netty:1.5.3")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("io.ktor:ktor-serialization:1.5.3")

    implementation("org.jetbrains.exposed:exposed-core:0.30.2")
    implementation("org.jetbrains.exposed:exposed-dao:0.30.2")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.30.2")
    implementation("org.jetbrains.exposed:exposed-java-time:0.30.2")
    implementation("org.xerial:sqlite-jdbc:3.34.0")
    implementation( "com.zaxxer:HikariCP:3.4.5")

    kaptTest("me.tatarka.inject:kotlin-inject-compiler-kapt:0.3.3")
    testImplementation("junit:junit:4.13.2")
    testImplementation("io.ktor:ktor-server-test-host:1.5.3")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.23.1")
}