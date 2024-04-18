package me.tatarka.inject.api

import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

interface VariantComponent {
    val logger: Interceptor
        @Provides @IntoSet get() = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
}