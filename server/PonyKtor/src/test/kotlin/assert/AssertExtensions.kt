package me.tatarka.inject.assert

import assertk.Assert
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import assertk.assertions.support.expected
import assertk.assertions.support.show
import io.ktor.server.testing.TestApplicationResponse
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

fun Assert<TestApplicationResponse>.hasStatusCode(statusCode: Int) =
    prop("statusCode") { it.status()?.value }.isEqualTo(statusCode)

fun Assert<TestApplicationResponse>.body() = prop("body") { it.content }

inline fun <reified T> Assert<String>.isJson(): Assert<T> = transform { given ->
    try {
        Json.decodeFromString(given)
    } catch (e: SerializationException) {
        expected("json but got:${show(given)}")
    }
}