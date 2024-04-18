pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
    }
    plugins {
        id("com.android.application") version "8.2.0"
        kotlin("android") version "1.9.23"
        id("com.google.devtools.ksp") version "1.9.23-1.0.20"
        id("androidx.navigation.safeargs.kotlin") version "2.7.7"
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        google()
    }
}
rootProject.name = "PonyInject"
include(":app")
 