package me.tatarka.inject.api

import me.tatarka.inject.annotations.Provides
import okhttp3.Interceptor

interface VariantComponent {
    val none: Set<Interceptor>
        @Provides get() = emptySet()
}