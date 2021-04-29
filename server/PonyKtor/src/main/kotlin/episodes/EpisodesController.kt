package me.tatarka.inject.episodes

import me.tatarka.inject.Episode
import me.tatarka.inject.QueryParameters
import me.tatarka.inject.Response
import me.tatarka.inject.annotations.Inject
import me.tatarka.inject.asInt
import me.tatarka.inject.getAsInt

/**
 * A controller that handles episodes routes.
 */
@Inject
class EpisodesController(private val repo: EpisodesRepository) {

    /**
     * Returns all episodes within the given limit & offset.
     */
    fun allEpisodes(queryParams: QueryParameters): Response<Episode> {
        val offset = queryParams.getAsInt("offset", 0)
        val limit = queryParams.getAsInt("limit", 50)
        val episodes = repo.episodes(offset, limit)
        return Response(data = episodes)
    }

    /**
     * Returns a single episode with the given id, or none if one cannot be found.
     */
    fun episode(id: String): Response<Episode> {
        val id = id.asInt("id")
        val episode = repo.episode(id)
        return Response(data = listOfNotNull(episode))
    }

    /**
     * Returns a singe episode with the given episode number, or none if one cannot be found.
     */
    fun episodeByOverall(number: String): Response<Episode> {
        val number = number.asInt("number")
        val episode = repo.episodeByOverall(number)
        return Response(data = listOfNotNull(episode))
    }

    /**
     * Returns a single episode with the given season and episode number, or none if one cannot be found.
     */
    fun episodeBySeason(season: String, episode: String): Response<Episode> {
        val season = season.asInt("season")
        val episodeNumber = episode.asInt(episode)
        val episode = repo.episodeBySeason(season, episodeNumber)
        return Response(data = listOfNotNull(episode))
    }
}