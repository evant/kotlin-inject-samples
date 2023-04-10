package com.fredporciuncula.inject.greeter

import kotlin.jvm.JvmInline

enum class Platform(val displayName: String) {
  Android("Android"), Ios("iOS")
}

@JvmInline value class Version(val value: String)
