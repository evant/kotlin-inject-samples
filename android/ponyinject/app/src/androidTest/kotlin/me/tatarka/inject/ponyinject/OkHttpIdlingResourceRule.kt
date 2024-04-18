package me.tatarka.inject.ponyinject

import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import com.jakewharton.espresso.OkHttp3IdlingResource
import okhttp3.OkHttpClient
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * Adds an [IdlingResource] for okhttp to espresso.
 */
class OkHttpIdlingResourceRule(private val client: () -> OkHttpClient) : TestWatcher() {
    private var resource: IdlingResource? = null

    override fun starting(description: Description?) {
        resource = OkHttp3IdlingResource.create("OkHttp", client())
        IdlingRegistry.getInstance().register(resource)
    }

    override fun finished(description: Description?) {
        IdlingRegistry.getInstance().unregister(resource)
        resource = null
    }
}