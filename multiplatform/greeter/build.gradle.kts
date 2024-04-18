plugins {
  alias(libs.plugins.kotlin.multiplatform).apply(false)
  alias(libs.plugins.kotlin.android).apply(false)
  alias(libs.plugins.android.application).apply(false)
  alias(libs.plugins.android.library).apply(false)
}

tasks.wrapper {
  jarFile = file("../../gradle/wrapper/gradle-wrapper.jar")
}
