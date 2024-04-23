import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.android.library)
  alias(libs.plugins.ksp)
}

kotlin {
  androidTarget()

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
    commonMain {
      dependencies {
        implementation(libs.kotlinInject.runtime)
      }
    }
    commonTest {
      dependencies {
        implementation(libs.kotlin.test)
      }
    }
  }
}

android {
  namespace = "com.fredporciuncula.inject.greeter"
  compileSdk = 34
  defaultConfig {
    minSdk = 24
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
}

tasks.withType<KotlinCompile>().configureEach {
  compilerOptions.jvmTarget = JvmTarget.JVM_11
}

dependencies {
  // KSP will eventually have better multiplatform support and we'll be able to simply have
  // `ksp libs.kotlinInject.compiler` in the dependencies block of each source set
  // https://github.com/google/ksp/pull/1021
  add("kspAndroid", libs.kotlinInject.compiler)
  add("kspIosX64", libs.kotlinInject.compiler)
  add("kspIosArm64", libs.kotlinInject.compiler)
  add("kspIosSimulatorArm64", libs.kotlinInject.compiler)
}
