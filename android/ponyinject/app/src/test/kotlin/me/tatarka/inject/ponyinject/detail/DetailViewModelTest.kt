package me.tatarka.inject.ponyinject.detail

import androidx.lifecycle.SavedStateHandle
import androidx.paging.testing.asSnapshot
import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.prop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.ponyinject.CoroutineTestRule
import me.tatarka.inject.ponyinject.TestApplicationComponent
import me.tatarka.inject.ponyinject.api.EpisodesRepository
import me.tatarka.inject.ponyinject.create
import org.junit.Rule
import org.junit.Test

/**
 * An example of a view model test using test-fakes and [SavedStateHandle].
 */
class DetailViewModelTest {
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Test
    fun fetches_the_episode_with_the_given_id() = runTest {
        val component = TestComponent::class.create()
        val repository = component.repository
        val viewModel = component.viewModel(SavedStateHandle(mapOf("episodeId" to 2)))
        repository.episodes.asSnapshot()

        assertThat(viewModel.episodeDetail.first()).isNotNull().all {
            prop(EpisodeDetailViewData::title).isEqualTo("Friendship is Magic, part 2")
            prop(EpisodeDetailViewData::airDate).isEqualTo("Oct 22, 2010")
            prop(EpisodeDetailViewData::writtenBy).isEqualTo("Lauren Faust")
            prop(EpisodeDetailViewData::storyboard).isEqualTo("Tom Sales, Mike West, Sherann Johnson, Sam To")
            prop(EpisodeDetailViewData::songs).isEqualTo("Laughter Song")
        }
    }

    @Component
    abstract class TestComponent(@Component val parent: TestApplicationComponent = TestApplicationComponent::class.create()) {
        abstract val viewModel: (SavedStateHandle) -> DetailViewModel

        abstract val repository: EpisodesRepository
    }
}