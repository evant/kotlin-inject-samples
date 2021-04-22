package me.tatarka.inject.ponyinject.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import me.tatarka.inject.annotations.Inject
import me.tatarka.inject.ponyinject.api.EpisodesRepository

/**
 * view model for the episodes page.
 */
@Inject
class EpisodesViewModel(repository: EpisodesRepository) : ViewModel() {
    val episodes = repository.episodes.cachedIn(viewModelScope)
}