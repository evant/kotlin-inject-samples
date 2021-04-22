package me.tatarka.inject.ponyinject.api

import java.time.LocalDate

open class FakeApiService : ApiService {

    private val episodes = listOf(
        Episode(
            id = 1,
            name = "Friendship is Magic, part 1",
            image = "https://vignette.wikia.nocookie.net/mlp/images/9/9f/Twilight_looks_up_at_the_moon_S1E01.png/revision/latest?cb=20121209043547",
            airdate = LocalDate.parse("2010-10-10"),
            writtenBy = "Lauren Faust",
            storyboard = "Tom Sales\nMike West\nSherann Johnson\nSam To",
        ),
        Episode(
            id = 2,
            name = "Friendship is Magic, part 2",
            image = "https://vignette.wikia.nocookie.net/mlp/images/2/2c/Main_ponies_activated_the_Elements_of_Harmony_S01E02.png/revision/latest?cb=20111205172131",
            airdate = LocalDate.parse("2010-10-22"),
            writtenBy = "Lauren Faust",
            storyboard = "Tom Sales\nMike West\nSherann Johnson\nSam To",
            song = listOf("Laughter Song"),
        )
    )


    override suspend fun episodes(offset: Int?, limit: Int?): Page<Episode> {
        val from = (offset ?: 0).coerceAtMost(episodes.size)
        val to = (from + (limit ?: 50)).coerceAtMost(episodes.size)
        return Page(data = episodes.subList(from, to))
    }
}