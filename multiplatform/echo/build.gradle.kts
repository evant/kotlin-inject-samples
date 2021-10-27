import com.google.devtools.ksp.gradle.KspTaskMetadata
import com.google.devtools.ksp.gradle.KspTaskNative
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeCompile

plugins {
    kotlin("multiplatform") version "1.5.30"
    id("com.google.devtools.ksp") version "1.5.30-1.0.0"
}

dependencies {
    ksp("me.tatarka.inject:kotlin-inject-compiler-ksp:0.3.7-SNAPSHOT")
}

val nativeTargets = arrayOf(
    "linuxX64",
    "macosX64",
)

kotlin {
    for (target in nativeTargets) {
        targets.add(presets.getByName(target).createTarget(target).apply {
            require(this is KotlinNativeTarget)
            binaries {
                executable()
            }
        })
    }
    sourceSets {
        commonMain {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
                implementation("me.tatarka.inject:kotlin-inject-runtime:0.3.7-SNAPSHOT")
                implementation("io.ktor:ktor-client-core:1.6.4")
                implementation("io.ktor:ktor-client-serialization:1.6.4")
            }
        }
    }
}

// Generate common code with ksp instead of per-platform, hopefully this won't be needed in the future.
// https://github.com/google/ksp/issues/567
kotlin.sourceSets.commonMain {
    kotlin.srcDir("build/generated/ksp/commonMain/kotlin")
}
tasks.withType<KspTaskNative>().configureEach {
    enabled = false
}
tasks.withType<KotlinNativeCompile>().configureEach {
    dependsOn(tasks.withType<KspTaskMetadata>())
}

tasks.wrapper {
    jarFile = file("../../gradle/wrapper/gradle-wrapper.jar")
}