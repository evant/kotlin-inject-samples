package me.tatarka.inject.episodes

import me.tatarka.inject.ApplicationScope
import me.tatarka.inject.Episode
import me.tatarka.inject.annotations.Inject
import me.tatarka.inject.db.EpisodeEntity
import me.tatarka.inject.db.EpisodesDao
import me.tatarka.inject.db.SongEntity
import me.tatarka.inject.db.SongsDao

/**
 * A repository responsible for fetching episode data from the local db.
 */
@Inject
@ApplicationScope
class EpisodesRepository(
    private val episodesDao: EpisodesDao,
    private val songsDao: SongsDao,
) {

    /**
     * Returns an episode with the given id, or null if one cannot be found.
     */
    fun episode(id: Int): Episode? {
        val episode = episodesDao.byId(id) ?: return null
        return episode.withSongs()
    }

    /**
     * Returns an episode with the given overall episode number, or null if one cannot be found.
     */
    fun episodeByOverall(number: Int): Episode? {
        val episode = episodesDao.byOverall(number) ?: return null
        return episode.withSongs()
    }

    /**
     * Returns an episode with the given season and episode number, or null if one cannot be found.
     */
    fun episodeBySeason(season: Int, episode: Int): Episode? {
        val episode = episodesDao.bySeason(season, episode) ?: return null
        return episode.withSongs()
    }

    /**
     * Returns all episodes within the given limit & offset.
     */
    fun episodes(offset: Int, limit: Int): List<Episode> {
        val episodes = episodesDao.all(offset, limit)
        val songs = songsDao.forEpisodes(offset, limit)
        return episodes.map { it.withSongs(songs) }
    }

    private fun EpisodeEntity.withSongs() = withSongs(songsDao.forEpisode(episodeId))

    private fun EpisodeEntity.withSongs(songs: List<SongEntity>): Episode {
        return Episode(
            id = episodeId,
            name = name,
            image = imageUrl,
            season = season,
            episode = episode,
            overall = overallEpisode,
            airdate = airdate,
            writtenBy = writtenBy,
            storyboard = storyboard,
            song = songs.filter { it.episodeId == episodeId }.map { it.name }
        )
    }
}