package com.fredporciuncula.inject.greeter

import android.content.Context
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
abstract class ApplicationComponent(
  @get:Provides val context: Context,
  @get:Provides val version: Version,
) : PlatformComponent
