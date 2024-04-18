package me.tatarka.inject.ponyinject.api

import com.squareup.moshi.JsonClass
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDate

interface ApiService {
    @GET("v1/episode/all")
    suspend fun episodes(@Query("offset") offset: Int?, @Query("limit") limit: Int?): Page<Episode>
}

@JsonClass(generateAdapter = true)
data class Page<T>(
    val data: List<T>
)

@JsonClass(generateAdapter = true)
data class Episode(
    val id: Int,
    val name: String,
    val image: String,
    val airdate: LocalDate,
    val writtenBy: String? = null,
    val storyboard: String? = null,
    val song: List<String>? = null,
)