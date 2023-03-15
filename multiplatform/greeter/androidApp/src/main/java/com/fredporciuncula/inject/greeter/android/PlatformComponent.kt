package com.fredporciuncula.inject.greeter.android

import com.fredporciuncula.inject.greeter.Platform
import com.fredporciuncula.inject.greeter.Version

interface PlatformComponent {
  fun providePlatform(): Platform = Platform.Android
  fun provideVersion(): Version = Version(BuildConfig.VERSION_NAME)
}
