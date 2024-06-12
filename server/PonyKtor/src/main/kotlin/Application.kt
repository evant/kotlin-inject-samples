package me.tatarka.inject

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
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
            exception<BadRequestException> { call, cause ->
                call.respond(HttpStatusCode.BadRequest, ErrorResponse(400, cause.message))
            }
        }
        routing { router(this) }
    }
}