package me.tatarka.inject.ponyinject.detail

import android.view.LayoutInflater
import androidx.test.platform.app.InstrumentationRegistry
import assertk.all
import assertk.assertThat
import assertk.assertions.prop
import me.tatarka.inject.ponyinject.assert.hasText
import me.tatarka.inject.ponyinject.databinding.DetailFragmentBinding
import org.junit.Test

class DetailFragmentTest {
    @Test
    fun binds_episode_view_data() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val binding = DetailFragmentBinding.inflate(LayoutInflater.from(context))
        binding.bind(
            EpisodeDetailViewData(
                title = "Friendship is Magic, part 2",
                airDate = "Oct 22, 2010",
                writtenBy = "Lauren Faust",
                storyboard = "Tom Sales, Mike West, Sherann Johnson, Sam To",
                songs = "Laughter Song",
            )
        )

        assertThat(binding).all {
            prop(DetailFragmentBinding::title).hasText("Friendship is Magic, part 2")
            prop(DetailFragmentBinding::airdate).hasText("Oct 22, 2010")
            prop(DetailFragmentBinding::writtenBy).hasText("Written By: Lauren Faust")
            prop(DetailFragmentBinding::storyboard).hasText("Storyboard: Tom Sales, Mike West, Sherann Johnson, Sam To")
            prop(DetailFragmentBinding::songs).hasText("Songs: Laughter Song")
        }

    }
}