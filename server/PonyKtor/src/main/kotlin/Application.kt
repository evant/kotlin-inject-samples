package me.tatarka.inject

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.BadRequestException
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.routing
import io.ktor.serialization.json
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

fun main() {
    val applicationComponent = ApplicationComponent::class.create(db = "jdbc:sqlite:ponydb.sqlite")
    embeddedServer(Netty, 8080) { applicationComponent.app(this) }.start(wait = true)
}

typealias app = (Application) -> Unit

/**
 * Sets up the application.
 */
@Inject
fun app(router: router, @Assisted app: Application) {
    app.apply {
        install(ContentNegotiation) {
            json(Json { encodeDefaults = true })
        }
        install(StatusPages) {
            exception<BadRequestException> { cause ->
                call.respond(HttpStatusCode.BadRequest, ErrorResponse(400, cause.message))
            }
        }
        routing { router(this) }
    }
}