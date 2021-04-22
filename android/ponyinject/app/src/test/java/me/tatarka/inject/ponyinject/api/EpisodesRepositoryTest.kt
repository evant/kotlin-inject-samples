package me.tatarka.inject.ponyinject.api

import assertk.assertThat
import assertk.assertions.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.ponyinject.*
import org.junit.Test
import java.io.IOException

/**
 * An example of a repository test using test-fakes.
 */
class EpisodesRepositoryTest {

    @Test
    fun fetches_episodes_from_the_api() = runBlocking {
        val component = TestComponent::class.create()
        val repository = component.episodesRepository
        val episodes = repository.episodes.first()

        assertThat(episodes.waitForSnapshot().items).extracting(Episode::name).containsExactly(
            "Friendship is Magic, part 1",
            "Friendship is Magic, part 2"
        )
    }

    @Test
    fun shows_error_if_fetch_fails() = runBlocking {
        val component = TestComponent::class.create(TestApplicationComponent::class.create(
            TestFakes(
                service = object : FakeApiService() {
                    override suspend fun episodes(offset: Int?, limit: Int?): Page<Episode> {
                        throw IOException("api call failed")
                    }
                }
            )))
        val repository = component.episodesRepository
        val episodes = repository.episodes.first()

        assertThat(episodes.waitForError())
            .isInstanceOf(IOException::class)
            .hasMessage("api call failed")
    }

    @Test
    fun fetches_a_single_episode_from_the_cache() = runBlocking {
        val component = TestComponent::class.create()
        val repository = component.episodesRepository
        repository.episodes.first().waitForSnapshot()
        val episode = repository.episode(1).first()

        assertThat(episode).isNotNull()
            .prop(Episode::name).isEqualTo("Friendship is Magic, part 1")
    }

    @Component
    abstract class TestComponent(@Component val parent: TestApplicationComponent = TestApplicationComponent::class.create()) {
        abstract val episodesRepository: EpisodesRepository
    }
}