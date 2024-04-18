package me.tatarka.inject.ponyinject.main

import androidx.fragment.app.Fragment
import me.tatarka.inject.annotations.Inject
import me.tatarka.inject.ponyinject.R

/**
 * The root of the app. Hosts a navigation graph.
 */
@Inject
class MainFragment : Fragment(R.layout.main_fragment)