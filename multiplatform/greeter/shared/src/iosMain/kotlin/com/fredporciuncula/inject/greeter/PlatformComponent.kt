package com.fredporciuncula.inject.greeter

import platform.Foundation.NSBundle

interface PlatformComponent {
  fun providePlatform(): Platform = Platform.Ios
  fun provideVersion(): Version = Version(
    NSBundle.mainBundle.objectForInfoDictionaryKey("CFBundleShortVersionString") as String
  )
}
