package me.tatarka.inject

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.extracting
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.engine.*
import io.ktor.server.testing.*
import kotlinx.serialization.ExperimentalSerializationApi
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.assert.hasStatusCode
import me.tatarka.inject.assert.isJson
import me.tatarka.inject.db.Episodes
import me.tatarka.inject.db.Images
import me.tatarka.inject.db.Songs
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.Test
import java.time.LocalDate

/**
 * An example of an end-to-end test using an in-memory sqlite db.
 */
@OptIn(ExperimentalSerializationApi::class)
class EndToEndTest {

    @Test
    fun `returns a page of all episodes`() = testApplication {
        application {
            val component = TestComponent::class.create()
            prePopulateDb(component.db)
            component.app(this)
        }

        val response = client.get("/episode/all")

        assertAll {
            assertThat(response).hasStatusCode(200)
            assertThat(response.bodyAsText()).isJson<Response<Episode>>()
                .prop(Response<Episode>::data).extracting(Episode::name).containsExactly(
                    "Friendship is Magic, part 1",
                    "Friendship is Magic, part 2",
                )
        }
    }

    @Test
    fun `returns a 400 on invalid query param type`() = testApplication {
        application {
            val component = TestComponent::class.create()
            component.app(this)
        }

        val response = client.get("/episode/all?offset=bad")

        assertAll {
            assertThat(response).hasStatusCode(400)
            assertThat(response.bodyAsText()).isJson<ErrorResponse>()
                .prop(ErrorResponse::error).isEqualTo("Request parameter offset couldn't be parsed/converted to Int")
        }
    }

    @Test
    fun `returns a single episode by id`() = testApplication {
        application {
            val component = TestComponent::class.create()
            prePopulateDb(component.db)
            component.app(this)
        }

        val response = client.get("/episode/1")

        assertAll {
            assertThat(response).hasStatusCode(200)
            assertThat(response.bodyAsText()).isJson<Response<Episode>>()
                .prop(Response<Episode>::data).extracting(Episode::id, Episode::name).containsExactly(
                    1 to "Friendship is Magic, part 1",
                )
        }
    }

    @Test
    fun `returns a single episode by overall number`() = testApplication {
        application {
            val component = TestComponent::class.create()
            prePopulateDb(component.db)
            component.app(this)
        }

        val response = client.get("/episode/by-overall/1")

        assertAll {
            assertThat(response).hasStatusCode(200)
            assertThat(response.bodyAsText()).isJson<Response<Episode>>()
                .prop(Response<Episode>::data).extracting(Episode::overall, Episode::name).containsExactly(
                    1 to "Friendship is Magic, part 1",
                )
        }
    }

    @Test
    fun `returns a single episode by season`() = testApplication {
        application {
            val component = TestComponent::class.create()
            prePopulateDb(component.db)
            component.app(this)
        }

        val response = client.get("/episode/by-season/1/1")

        assertAll {
            assertThat(response).hasStatusCode(200)
            assertThat(response.bodyAsText()).isJson<Response<Episode>>()
                .prop(Response<Episode>::data).extracting(Episode::season, Episode::episode, Episode::name)
                .containsExactly(
                    Triple(1, 1, "Friendship is Magic, part 1")
                )
        }
    }

    @Component
    abstract class TestComponent(@Component val parent: ApplicationComponent = ApplicationComponent::class.create("jdbc:sqlite::memory:")) {
        abstract val db: Database
        abstract val app: app
    }

    /**
     * Populates the in-memory db with some test data.
     */
    private fun prePopulateDb(db: Database) {
        transaction(db) {
            SchemaUtils.create(Episodes, Images, Songs)
            run {
                val newImageId = Images.insertAndGetId {
                    it[url] =
                        "https://vignette.wikia.nocookie.net/mlp/images/9/9f/Twilight_looks_up_at_the_moon_S1E01.png/revision/latest?cb=20121209043547"
                }
                Episodes.insert {
                    it[name] = "Friendship is Magic, part 1"
                    it[imageId] = newImageId
                    it[season] = 1
                    it[episode] = 1
                    it[overallEpisode] = 1
                    it[airdate] = LocalDate.parse("2010-10-10")
                    it[writtenBy] = "Lauren Faust"
                    it[storyboard] = "Tom Sales\nMike West\nSherann Johnson\nSam To"
                }
            }
            run {
                val newImageId = Images.insertAndGetId {
                    it[url] =
                        "https://vignette.wikia.nocookie.net/mlp/images/9/9f/Twilight_looks_up_at_the_moon_S1E01.png/revision/latest?cb=20121209043547"
                }
                val newEpisodeId = Episodes.insertAndGetId {
                    it[name] = "Friendship is Magic, part 2"
                    it[imageId] = newImageId
                    it[season] = 1
                    it[episode] = 2
                    it[overallEpisode] = 2
                    it[airdate] = LocalDate.parse("2010-10-22")
                    it[writtenBy] = "Lauren Faust"
                    it[storyboard] = "Tom Sales\nMike West\nSherann Johnson\nSam To"
                }
                Songs.insert {
                    it[episodeId] = newEpisodeId
                    it[name] = "Laughter Song"
                }
            }
        }
    }
}