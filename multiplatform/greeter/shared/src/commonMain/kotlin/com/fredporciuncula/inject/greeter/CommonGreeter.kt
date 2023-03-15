package com.fredporciuncula.inject.greeter

class CommonGreeter(
  private val greeter: Greeter,
  private val platform: Platform,
  private val version: Version,
) {
  fun greet() {
    greeter.greet("Hello from $platform! [${version.value}]")
  }
}
