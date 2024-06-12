package com.fredporciuncula.inject.greeter

import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.KmpComponentCreate

@Component
abstract class ApplicationComponent : PlatformComponent {
  abstract val greeter: CommonGreeter

  companion object
}

@KmpComponentCreate
expect fun ApplicationComponent.Companion.create(): ApplicationComponent
