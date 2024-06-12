plugins {
    kotlin("multiplatform") version "1.9.23"
    id("com.google.devtools.ksp") version "1.9.23-1.0.20"
}

kotlin {
    listOf(
        linuxX64(),
        macosX64(), macosArm64(),
    ).forEach {
        it.binaries.executable()
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation("me.tatarka.inject:kotlin-inject-runtime-kmp:0.7.1")
            }
        }
    }
}

// KSP will eventually have better multiplatform support and we'll be able to simply have
// `ksp libs.kotlinInject.compiler` in the dependencies block of each source set
// https://github.com/google/ksp/pull/1021

dependencies {
    add("kspLinuxX64", "me.tatarka.inject:kotlin-inject-compiler-ksp:0.7.1")
    add("kspMacosX64", "me.tatarka.inject:kotlin-inject-compiler-ksp:0.7.1")
    add("kspMacosArm64", "me.tatarka.inject:kotlin-inject-compiler-ksp:0.7.1")
}

tasks.wrapper {
    jarFile = file("../../gradle/wrapper/gradle-wrapper.jar")
}