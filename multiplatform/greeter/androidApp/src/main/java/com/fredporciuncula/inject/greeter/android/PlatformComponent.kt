package com.fredporciuncula.inject.greeter.android

import com.fredporciuncula.inject.greeter.AndroidGreeter
import com.fredporciuncula.inject.greeter.Greeter
import com.fredporciuncula.inject.greeter.Platform
import com.fredporciuncula.inject.greeter.Version
import me.tatarka.inject.annotations.Provides

interface PlatformComponent {
  @Provides fun providePlatform(): Platform = Platform.Android
  @Provides fun provideVersion(): Version = Version(BuildConfig.VERSION_NAME)
  @Provides fun AndroidGreeter.bind(): Greeter = this
}
