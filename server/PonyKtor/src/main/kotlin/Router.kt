package me.tatarka.inject

import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.util.getOrFail
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import me.tatarka.inject.episodes.EpisodesController

typealias router = (Routing) -> Unit

/**
 * Sets up the app's routes by delegating to the relevant controller.
 */
@Inject
fun router(
    episodesController: EpisodesController,
    @Assisted routing: Routing,
) {
    routing.apply {
        get("episode/all") {
            call.respond(episodesController.allEpisodes(call.request.queryParameters))
        }
        get("episode/{id}") {
            call.respond(episodesController.episode(call.parameters.getOrFail("id")))
        }
        get("episode/by-overall/{number}") {
            call.respond(episodesController.episodeByOverall(call.parameters.getOrFail("number")))
        }
        get("episode/by-season/{season}/{episode}") {
            val p = call.parameters
            call.respond(episodesController.episodeBySeason(p.getOrFail("season"), p.getOrFail("episode")))
        }
    }
}