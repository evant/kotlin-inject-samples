package me.tatarka.inject.ponyinject.api

import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject
import me.tatarka.inject.ponyinject.ApplicationScope
import java.io.IOException

/**
 * Fetches the list of episodes using [paging v3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview)
 * You can then also get a single cached episode with a given id.
 */
@Inject
@ApplicationScope
class EpisodesRepository(private val service: ApiService) {

    private val episodeCache = MutableStateFlow(emptyMap<Int, Episode>())

    /**
     * A paged list of all episodes.
     */
    val episodes: Flow<PagingData<Episode>> = Pager(PagingConfig(pageSize = 20), 0) {
        LimitOffsetPagingSource { offset, limit ->
            service.episodes(offset, limit).data
                .also { episodes -> updateCache(episodes) }
        }
    }.flow

    /**
     * A single cached episode with the given id. Will return null if the episode has not yet been
     * loaded.
     */
    fun episode(id: Int): Flow<Episode?> = episodeCache.map { it[id] }

    private fun updateCache(episodes: List<Episode>) {
        val newCache = episodeCache.value.toMutableMap()
        for (episode in episodes) {
            newCache[episode.id] = episode
        }
        episodeCache.value = newCache
    }
}

/**
 * A [PagingSource] that fetches data using a limit and offset.
 */
private class LimitOffsetPagingSource<T : Any>(private val fetch: suspend (offset: Int, limit: Int) -> List<T>) :
    PagingSource<Int, T>() {
    override fun getRefreshKey(state: PagingState<Int, T>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val offset = params.key!!
        val limit = params.loadSize
        return try {
            val data = fetch(offset, limit)
            val next = if (data.isEmpty()) null else offset + data.size
            LoadResult.Page(data, null, next)
        } catch (e: IOException) {
            LoadResult.Error(e)
        }
    }
}