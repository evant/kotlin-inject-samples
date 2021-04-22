package me.tatarka.inject.ponyinject.detail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import me.tatarka.inject.annotations.Inject
import me.tatarka.inject.ponyinject.R
import me.tatarka.inject.ponyinject.databinding.DetailFragmentBinding
import me.tatarka.inject.ponyinject.viewModels

/**
 * Shows details for a single episode.
 */
@Inject
class DetailFragment(viewModel: (SavedStateHandle) -> DetailViewModel) :
    Fragment(R.layout.detail_fragment) {
    private val viewModel by viewModels(viewModel)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = DetailFragmentBinding.bind(view)
        viewModel.episodeDetail.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { episode -> binding.bind(episode) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }
}

/**
 * Binds a [EpisodeDetailViewData] to the ui, split out for ease of testing.
 */
fun DetailFragmentBinding.bind(episode: EpisodeDetailViewData?) {
    loading.isVisible = episode == null
    if (episode == null) return
    val res = root.resources
    title.text = episode.title
    airdate.text = episode.airDate
    if (episode.writtenBy != null) {
        writtenBy.text = res.getString(R.string.written_by, episode.writtenBy)
    } else {
        writtenBy.isVisible = false
    }
    if (episode.storyboard != null) {
        storyboard.text = res.getString(R.string.storyboard, episode.storyboard)
    } else {
        storyboard.isVisible = false
    }
    if (episode.songs != null) {
        songs.text = res.getString(R.string.songs, episode.songs)
    } else {
        songs.isVisible = false
    }
}