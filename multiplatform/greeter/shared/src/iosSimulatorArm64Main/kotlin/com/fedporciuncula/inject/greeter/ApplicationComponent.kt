package com.fredporciuncula.inject.greeter

import me.tatarka.inject.annotations.Component

@Component
abstract class ApplicationComponent : PlatformComponent {
  abstract val greeter: CommonGreeter

  companion object {
    fun create() = ApplicationComponent::class.create()
  }
}
