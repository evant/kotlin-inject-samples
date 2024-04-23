package com.fredporciuncula.inject.greeter.android

import android.content.Context
import com.fredporciuncula.inject.greeter.ApplicationComponent

interface ApplicationComponentProvider {
  val component: ApplicationComponent
}

val Context.applicationComponent get() = (applicationContext as ApplicationComponentProvider).component
