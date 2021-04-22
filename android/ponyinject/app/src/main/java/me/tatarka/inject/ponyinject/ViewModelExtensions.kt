package me.tatarka.inject.ponyinject

import androidx.fragment.app.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * [viewModels] helper that allows you to pass a single factory function.
 */
inline fun <reified VM : ViewModel> Fragment.viewModels(crossinline factory: () -> VM): Lazy<VM> =
    viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T = factory() as T
        }
    }

/**
 * [viewModels] helper that allows you to pass a single factory function using a [SavedStateHandle].
 */
inline fun <reified VM : ViewModel> Fragment.viewModels(crossinline factory: (SavedStateHandle) -> VM): Lazy<VM> =
    viewModels {
        object : AbstractSavedStateViewModelFactory(this, arguments) {
            override fun <T : ViewModel?> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T = factory(handle) as T
        }
    }
