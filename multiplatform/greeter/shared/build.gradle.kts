plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.android.library)
  alias(libs.plugins.ksp)
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
  targetHierarchy.default() // https://kotlinlang.org/docs/whatsnew1820.html#new-approach-to-source-set-hierarchy

  android()

  listOf(
    iosX64(),
    iosArm64(),
    iosSimulatorArm64()
  ).forEach {
    it.binaries.framework {
      baseName = "shared"
    }
  }

  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation(libs.kotlinInject.runtime)
      }
    }
    val commonTest by getting {
      dependencies {
        implementation(kotlin("test"))
      }
    }
  }
}

android {
  namespace = "com.fredporciuncula.inject.greeter"
  compileSdk = 33
  defaultConfig {
    minSdk = 24
    targetSdk = 33
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
}

dependencies {
  // KSP will eventually have better multiplatform support and we'll be able to simply have
  // `ksp libs.kotlinInject.compiler` in the dependencies block of each source set
  // https://github.com/google/ksp/pull/1021
  add("kspIosX64", libs.kotlinInject.compiler)
  add("kspIosArm64", libs.kotlinInject.compiler)
  add("kspIosSimulatorArm64", libs.kotlinInject.compiler)
}
