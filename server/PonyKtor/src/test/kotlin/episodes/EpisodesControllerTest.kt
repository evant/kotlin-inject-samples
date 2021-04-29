package me.tatarka.inject.episodes

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.extracting
import assertk.assertions.isFailure
import assertk.assertions.isInstanceOf
import assertk.assertions.prop
import io.ktor.features.BadRequestException
import io.ktor.http.parametersOf
import me.tatarka.inject.Episode
import me.tatarka.inject.Response
import me.tatarka.inject.TestApplicationComponent
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.create
import org.junit.Test

/**
 * An example of an episodes controller test, using test fakes.
 */
class EpisodesControllerTest {

    @Test
    fun `returns a page off all episodes 2`() {
        val component = TestComponent::class.create()
        val controller = component.controller
        val response = controller.allEpisodes(parametersOf())

        assertThat(response).prop(Response<Episode>::data).extracting(Episode::name).containsExactly(
            "Friendship is Magic, part 1",
            "Friendship is Magic, part 2"
        )
    }

    @Test
    fun `throws a bad request on invalid query param type`() {
        val component = TestComponent::class.create()
        val controller = component.controller

        assertThat {
            controller.allEpisodes(parametersOf("limit" to listOf("bad")))
        }.isFailure().isInstanceOf(BadRequestException::class)
    }

    @Test
    fun `returns a single episode by id`() {
        val component = TestComponent::class.create()
        val controller = component.controller
        val response = controller.episode("1")

        assertThat(response).prop(Response<Episode>::data).extracting(Episode::id, Episode::name).containsExactly(
            1 to "Friendship is Magic, part 1"
        )
    }

    @Test
    fun `returns a single episode by overall number`() {
        val component = TestComponent::class.create()
        val controller = component.controller
        val response = controller.episodeByOverall("1")

        assertThat(response).prop(Response<Episode>::data).extracting(Episode::overall, Episode::name).containsExactly(
            1 to "Friendship is Magic, part 1"
        )
    }

    @Test
    fun `returns a single episode by season`() {
        val component = TestComponent::class.create()
        val controller = component.controller
        val response = controller.episodeBySeason("1", "1")

        assertThat(response).prop(Response<Episode>::data).extracting(Episode::season, Episode::episode, Episode::name)
            .containsExactly(
                Triple(1, 1, "Friendship is Magic, part 1")
            )
    }

    @Component
    abstract class TestComponent(@Component val parent: TestApplicationComponent = TestApplicationComponent::class.create()) {
        abstract val controller: EpisodesController
    }
}