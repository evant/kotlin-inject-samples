package com.fredporciuncula.inject.greeter

actual fun ApplicationComponent.Companion.create(): ApplicationComponent =
  ApplicationComponent::class.create()
