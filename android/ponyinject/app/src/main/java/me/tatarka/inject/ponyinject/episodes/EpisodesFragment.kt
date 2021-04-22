package me.tatarka.inject.ponyinject.episodes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import me.tatarka.inject.ponyinject.R
import me.tatarka.inject.ponyinject.databinding.EpisodesFragmentBinding
import me.tatarka.inject.ponyinject.detail.DetailFragmentArgs
import me.tatarka.inject.ponyinject.ui.TwoPaneOnBackPressedCallback
import me.tatarka.inject.ponyinject.viewModels

/**
 * Shows a list of episodes and navigates to their detail page one one is clicked. This uses a
 * [SlidingPaneLayout] as described in [https://developer.android.com/guide/topics/ui/layout/twopane]
 */
@Inject
class EpisodesFragment(viewModel: () -> EpisodesViewModel) : Fragment(R.layout.episodes_fragment) {
    private val viewModel by viewModels(viewModel)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = EpisodesFragmentBinding.bind(view)
        val adapter = EpisodesAdapter { id -> binding.navigate(id) }
        binding.list.adapter = adapter

        // Connect the SlidingPaneLayout to the system back button.
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            TwoPaneOnBackPressedCallback(binding.root)
        )

        // hook up the adapter with paging data
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.episodes
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collectLatest { data -> adapter.submitData(data) }
        }
    }

    /**
     * Navigates to the detail page with the given id.
     */
    private fun EpisodesFragmentBinding.navigate(id: Int) {
        val navController =
            (childFragmentManager.findFragmentById(R.id.navDetail) as NavHostFragment).navController
        navController.navigate(
            R.id.detail,
            DetailFragmentArgs(episodeId = id).toBundle(),
            NavOptions.Builder()
                // Pop all destinations off the back stack.
                .setPopUpTo(navController.graph.startDestination, true)
                .apply {
                    // If we're already open and the detail pane is visible,
                    // crossfade between the destinations.
                    if (root.isOpen) {
                        setEnterAnim(R.animator.nav_default_enter_anim)
                        setExitAnim(R.animator.nav_default_exit_anim)
                    }
                }
                .build()
        )
        root.open()
    }
}