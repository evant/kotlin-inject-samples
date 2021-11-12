import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform") version "1.5.31"
    id("com.google.devtools.ksp") version "1.5.31-1.0.1"
}

dependencies {
    add("kspMetadata", "me.tatarka.inject:kotlin-inject-compiler-ksp:0.4.0")
}

val nativeTargets = arrayOf(
    "linuxX64",
    "macosX64", "macosArm64"
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
                implementation("me.tatarka.inject:kotlin-inject-runtime:0.4.0")
            }
        }
    }
}

// Generate common code with ksp instead of per-platform, hopefully this won't be needed in the future.
// https://github.com/google/ksp/issues/567
kotlin.sourceSets.commonMain {
    kotlin.srcDir("build/generated/ksp/commonMain/kotlin")
}
tasks.withType<org.jetbrains.kotlin.gradle.dsl.KotlinCompile<*>>().all {
    if (name != "kspKotlinMetadata") {
        dependsOn("kspKotlinMetadata")
    }
}

tasks.wrapper {
    jarFile = file("../../gradle/wrapper/gradle-wrapper.jar")
}