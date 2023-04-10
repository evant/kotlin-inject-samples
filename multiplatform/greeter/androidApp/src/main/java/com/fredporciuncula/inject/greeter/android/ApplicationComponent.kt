package com.fredporciuncula.inject.greeter.android

import android.content.Context
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
abstract class ApplicationComponent(
  @get:Provides val context: Context,
) : PlatformComponent

interface ApplicationComponentProvider {
  val component: ApplicationComponent
}

val Context.applicationComponent get() = (applicationContext as ApplicationComponentProvider).component
