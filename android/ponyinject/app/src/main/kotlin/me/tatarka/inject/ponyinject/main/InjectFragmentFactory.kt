package me.tatarka.inject.ponyinject.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import me.tatarka.inject.annotations.Inject
import me.tatarka.inject.ponyinject.detail.DetailFragment
import me.tatarka.inject.ponyinject.episodes.EpisodesFragment

/**
 * Allows us to use [Inject] constructors on fragments. When you add a new fragment you need to add
 * it here.
 */
@Inject
class InjectFragmentFactory(
    private val mainFragment: () -> MainFragment,
    private val episodesFragment: () -> EpisodesFragment,
    private val detailFragment: () -> DetailFragment
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            name<MainFragment>() -> mainFragment()
            name<EpisodesFragment>() -> episodesFragment()
            name<DetailFragment>() -> detailFragment()
            // fall-back to default no-args construction so we can handle 3rd-party fragments.
            else -> super.instantiate(classLoader, className)
        }
    }

    private inline fun <reified T> name() = T::class.qualifiedName
}