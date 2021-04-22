plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.devtools.ksp") version "1.4.32-1.0.0-alpha08"
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = 30
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "me.tatarka.inject.ponyinject"
        minSdk = 21
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "BASE_URL", "\"https://ponyweb.ml/\"")
    }

    buildTypes {
        get("release").apply {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
    }
    buildFeatures {
        viewBinding = true
    }

    sourceSets {
        // allows android studio to see ksp generated code
        get("debug").java {
            srcDir("build/generated/ksp/debug/kotlin")
        }
        get("release").java {
            srcDir("build/generated/ksp/release/kotlin")
        }
        get("testDebug").java {
            srcDir("build/generated/ksp/debugUnitTest/kotlin")
        }
        get("androidTestDebug").java {
            srcDir("build/generated/ksp/debugAndroidTest/kotlin")
        }
        // share code between unit and android tests
        get("test").java {
            srcDir("src/commonTest/java")
        }
        get("androidTest").java {
            srcDir("src/commonTest/java")
        }
    }
}

dependencies {
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")

    implementation("me.tatarka.inject:kotlin-inject-runtime:0.3.3")
    ksp("me.tatarka.inject:kotlin-inject-compiler-ksp:0.3.3")

    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    debugImplementation("com.squareup.okhttp3:logging-interceptor:4.9.1")
    implementation("com.squareup.moshi:moshi:1.12.0")
    ksp("dev.zacsweers.moshix:moshi-ksp:0.10.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

    implementation("io.coil-kt:coil:1.2.0")

    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0-alpha01")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.fragment:fragment-ktx:1.3.2")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.slidingpanelayout:slidingpanelayout:1.2.0-alpha01")
    debugImplementation("androidx.fragment:fragment-testing:1.3.2")
    implementation("androidx.paging:paging-runtime-ktx:3.0.0-beta03")

    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.3")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.23.1")

    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    androidTestImplementation("com.squareup.okhttp3:mockwebserver:4.9.1")
    androidTestImplementation("com.jakewharton.espresso:okhttp3-idling-resource:1.0.0")
    androidTestImplementation("com.willowtreeapps.assertk:assertk-jvm:0.23.1")
}