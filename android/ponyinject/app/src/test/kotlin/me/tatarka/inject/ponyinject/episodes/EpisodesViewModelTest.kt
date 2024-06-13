package me.tatarka.inject.ponyinject.episodes

import androidx.paging.testing.asSnapshot
import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.extracting
import kotlinx.coroutines.test.runTest
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.ponyinject.CoroutineTestRule
import me.tatarka.inject.ponyinject.TestApplicationComponent
import me.tatarka.inject.ponyinject.api.Episode
import me.tatarka.inject.ponyinject.create
import org.junit.Rule
import org.junit.Test

/**
 * An example of a view model test using test-fakes.
 */
class EpisodesViewModelTest {
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Test
    fun fetches_episodes() = runTest {
        val component = TestComponent::class.create()
        val viewModel = component.viewModel
        val episodes = viewModel.episodes.asSnapshot()

        assertThat(episodes).extracting(Episode::name).containsExactly(
            "Friendship is Magic, part 1",
            "Friendship is Magic, part 2"
        )
    }

    @Component
    abstract class TestComponent(@Component val parent: TestApplicationComponent = TestApplicationComponent::class.create()) {
        abstract val viewModel: EpisodesViewModel
    }
}