package me.tatarka.inject.ponyinject.episodes

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.ponyinject.R
import me.tatarka.inject.ponyinject.TestApplicationComponent
import me.tatarka.inject.ponyinject.create
import me.tatarka.inject.ponyinject.main.InjectFragmentFactory
import org.hamcrest.Matchers.allOf
import org.junit.Test
import org.junit.runner.RunWith

/**
 * An example of a self-contained fragment test, using test fakes.
 */
@RunWith(AndroidJUnit4::class)
class EpisodesFragmentTest {
    @Test
    fun selects_item_in_list() {
        val component = TestComponent::class.create()
        launchFragmentInContainer<EpisodesFragment>(
            themeResId = R.style.Theme_PonyInject,
            factory = component.fragmentFactory
        )

        onView(withText("Friendship is Magic, part 2")).perform(click())
        onView(
            allOf(
                withId(R.id.title),
                withParent(withId(R.id.detail))
            )
        ).check(matches(withText("Friendship is Magic, part 2")))
    }

    @Component
    abstract class TestComponent(@Component val parent: TestApplicationComponent = TestApplicationComponent::class.create()) {
        abstract val fragmentFactory: InjectFragmentFactory
    }
}