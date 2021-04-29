package me.tatarka.inject.db

import java.time.LocalDate

class FakeEpisodeEntity(
    override val episodeId: Int,
    override val name: String,
    override val imageUrl: String,
    override val season: Int,
    override val episode: Int,
    override val overallEpisode: Int,
    override val airdate: LocalDate,
    override val writtenBy: String? = null,
    override val storyboard: String? = null,
) : EpisodeEntity

val episodes = listOf(
    FakeEpisodeEntity(
        episodeId = 1,
        name = "Friendship is Magic, part 1",
        imageUrl = "https://vignette.wikia.nocookie.net/mlp/images/9/9f/Twilight_looks_up_at_the_moon_S1E01.png/revision/latest?cb=20121209043547",
        season = 1,
        episode = 1,
        overallEpisode = 1,
        airdate = LocalDate.parse("2010-10-10"),
        writtenBy = "Lauren Faust",
        storyboard = "Tom Sales\nMike West\nSherann Johnson\nSam To",
    ),
    FakeEpisodeEntity(
        episodeId = 2,
        name = "Friendship is Magic, part 2",
        imageUrl = "https://vignette.wikia.nocookie.net/mlp/images/2/2c/Main_ponies_activated_the_Elements_of_Harmony_S01E02.png/revision/latest?cb=20111205172131",
        season = 1,
        episode = 2,
        overallEpisode = 2,
        airdate = LocalDate.parse("2010-10-22"),
        writtenBy = "Lauren Faust",
        storyboard = "Tom Sales\nMike West\nSherann Johnson\nSam To",
    )
)

class FakeSongEntity(
    override val episodeId: Int,
    override val songId: Int,
    override val name: String
) : SongEntity

open class FakeEpisodesDao : EpisodesDao {
    override fun byId(id: Int): EpisodeEntity? {
        return episodes.find { it.episodeId == id }
    }

    override fun byOverall(number: Int): EpisodeEntity? {
        return episodes.find { it.overallEpisode == number }
    }

    override fun bySeason(season: Int, episode: Int): EpisodeEntity? {
        return episodes.find { it.season == season && it.episode == episode }
    }

    override fun all(offset: Int, limit: Int): List<EpisodeEntity> {
        val from = (offset).coerceAtMost(episodes.size)
        val to = (from + limit).coerceAtMost(episodes.size)
        return episodes.subList(from, to)
    }
}