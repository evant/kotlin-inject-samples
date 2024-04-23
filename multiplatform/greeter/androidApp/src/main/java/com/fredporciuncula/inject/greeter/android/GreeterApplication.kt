package com.fredporciuncula.inject.greeter.android

import android.app.Application
import com.fredporciuncula.inject.greeter.ApplicationComponent
import com.fredporciuncula.inject.greeter.Version
import com.fredporciuncula.inject.greeter.create

class GreeterApplication : Application(), ApplicationComponentProvider {
  override val component by lazy(LazyThreadSafetyMode.NONE) {
    ApplicationComponent::class.create(applicationContext, Version(BuildConfig.VERSION_NAME))
  }
}
