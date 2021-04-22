package me.tatarka.inject.ponyinject.api

import android.app.Application
import com.squareup.moshi.Moshi
import me.tatarka.inject.annotations.Provides
import me.tatarka.inject.ponyinject.ApplicationScope
import okhttp3.Cache
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.io.File
import java.time.LocalDate

/**
 * Api-specific setup.
 */
interface ApiComponent {

    @Provides
    @ApplicationScope
    fun okhttpClient(
        application: Application,
        interceptors: Set<Interceptor>,
    ): OkHttpClient = OkHttpClient.Builder()
        .cache(Cache(File(application.cacheDir, "http"), maxSize = 50L * 1024L * 1024L /*50 MiB*/))
        .apply { interceptors.forEach { addInterceptor(it) } }
        .build()

    @Provides
    fun moshi(): Moshi = Moshi.Builder()
        .add(LocalDate::class.java, LocalDateAdapter())
        .build()

    @Provides
    @ApplicationScope
    fun retrofit(
        // we use a lazy OkHttpClient so it can be initialized off the main thread,
        // this speeds up launch times.
        client: Lazy<OkHttpClient>,
        baseUrl: HttpUrl,
        moshi: Moshi,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .callFactory { client.value.newCall(it) }
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val Retrofit.apiService: ApiService
        @Provides @ApplicationScope get() = create()
}