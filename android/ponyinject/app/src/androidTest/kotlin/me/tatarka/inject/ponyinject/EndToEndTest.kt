package me.tatarka.inject.ponyinject

import android.app.Application
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.ponyinject.main.InjectFragmentFactory
import me.tatarka.inject.ponyinject.main.MainFragment
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Buffer
import okio.buffer
import okio.source
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * An example of an end-to-end test using [MockWebServer] to mock the api.
 */
@RunWith(AndroidJUnit4::class)
class EndToEndTest {

    @get:Rule
    val mockServer = MockWebServer()

    private val component by lazy {
        val application =
            InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as Application
        TestComponent::class.create(
            ApplicationComponent::class.create(application, baseUrl = mockServer.url(""))
        )
    }

    @get:Rule
    val idlingResourceRule = OkHttpIdlingResourceRule { component.client }

    @Test
    fun selects_item_in_list_help() {
        mockServer.enqueue(MockResponse().setBody(asset("episode_all_1.json")))
        mockServer.enqueue(MockResponse().setBody(asset("episode_all_2.json")))
        launchFragmentInContainer<MainFragment>(
            themeResId = R.style.Theme_PonyInject,
            factory = component.fragmentFactory
        )

        onView(withText("Friendship is Magic, part 2"))
            .perform(click())
        onView(
            allOf(
                withId(R.id.title),
                withParent(withId(R.id.detail))
            )
        ).check(matches(withText("Friendship is Magic, part 2")))
    }

    @Component
    abstract class TestComponent(@Component val parent: ApplicationComponent) {
        abstract val fragmentFactory: InjectFragmentFactory
        abstract val client: OkHttpClient
    }
}

private fun asset(fileName: String): Buffer {
    val context = InstrumentationRegistry.getInstrumentation().context
    val buffer = Buffer()
    context.assets.open(fileName).source().buffer().use {
        buffer.writeAll(it)
    }
    return buffer
}