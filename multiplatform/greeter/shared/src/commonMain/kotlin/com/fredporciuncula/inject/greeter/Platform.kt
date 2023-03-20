package com.fredporciuncula.inject.greeter

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform