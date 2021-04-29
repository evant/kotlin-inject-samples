package me.tatarka.inject.db

private val songs = listOf(
    FakeSongEntity(
        episodeId = 2,
        songId = 1,
        name = "Laughter Song"
    )
)

open class FakeSongsDao : SongsDao {
    override fun forEpisode(id: Int): List<SongEntity> {
        return songs.filter { it.episodeId == id }
    }

    override fun forEpisodes(offset: Int, limit: Int): List<SongEntity> {
        val from = (offset).coerceAtMost(episodes.size)
        val to = (from + limit).coerceAtMost(episodes.size)
        val filteredEpisodes = episodes.subList(from, to)
        return songs.filter { song -> filteredEpisodes.any { it.episodeId == song.episodeId } }
    }
}