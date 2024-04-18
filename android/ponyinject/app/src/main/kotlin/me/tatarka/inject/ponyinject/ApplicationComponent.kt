package me.tatarka.inject.ponyinject

import android.app.Application
import android.content.Context
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import me.tatarka.inject.annotations.Scope
import me.tatarka.inject.api.VariantComponent
import me.tatarka.inject.ponyinject.api.ApiComponent
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl

/**
 * The application-level scope. There will only be one instance of anything annotated with this.
 */
@Scope
annotation class ApplicationScope

/**
 * The main application component. Use [getInstance] to ensure the same instance is shared.
 */
@Component
@ApplicationScope
abstract class ApplicationComponent(
    @get:Provides val application: Application,
    @get:Provides val baseUrl: HttpUrl
) : ApiComponent, VariantComponent {
    companion object {
        private var instance: ApplicationComponent? = null

        /**
         * Get a singleton instance of [ApplicationComponent].
         */
        fun getInstance(context: Context) = instance ?: ApplicationComponent::class.create(
            context.applicationContext as Application, BuildConfig.BASE_URL.toHttpUrl()
        ).also { instance = it }
    }
}