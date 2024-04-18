package me.tatarka.inject.ponyinject.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import me.tatarka.inject.ponyinject.api.Episode
import me.tatarka.inject.ponyinject.api.EpisodesRepository
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

private val DATE_FORMATTER = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)

/**
 * view model for the episode details page.
 */
@Inject
class DetailViewModel(repository: EpisodesRepository, @Assisted handle: SavedStateHandle) :
    ViewModel() {
    private val episodeId: Int = handle["episodeId"]!!

    val episodeDetail = repository.episode(episodeId).map { it?.toViewData() }

    private fun Episode.toViewData() = EpisodeDetailViewData(
        title = name,
        airDate = airdate.format(DATE_FORMATTER),
        writtenBy = writtenBy,
        storyboard = storyboard?.replace("\n", ", "),
        songs = song?.joinToString(", "),
    )
}

data class EpisodeDetailViewData(
    val title: String,
    val airDate: String,
    val writtenBy: String?,
    val storyboard: String?,
    val songs: String?,
)