package me.tatarka.inject

import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
class ErrorResponse(val status: Int, val error: String?)

@Serializable
class Response<T>(val status: Int = 200, val data: List<T>)

@Serializable
data class Episode(
    val id: Int,
    val name: String,
    val image: String,
    val season: Int?,
    val episode: Int?,
    val overall: Int?,
    @Serializable(with = LocalDateSerializer::class)
    val airdate: LocalDate,
    val writtenBy: String? = null,
    val storyboard: String? = null,
    val song: List<String>? = null,
)