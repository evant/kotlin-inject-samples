package com.fredporciuncula.inject.greeter

import me.tatarka.inject.annotations.Provides
import platform.Foundation.NSBundle

interface PlatformComponent {
  @Provides fun providePlatform(): Platform = Platform.Ios
  @Provides fun provideVersion(): Version = Version(
    NSBundle.mainBundle.objectForInfoDictionaryKey("CFBundleShortVersionString") as String
  )
  @Provides fun IosGreeter.bind(): Greeter = this
}
