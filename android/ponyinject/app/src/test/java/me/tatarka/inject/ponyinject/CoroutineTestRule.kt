package me.tatarka.inject.ponyinject

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * Replaces [Dispatchers.Main] for test so that they can run without robolectric.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class CoroutineTestRule(private val dispatcher: CoroutineDispatcher = Dispatchers.Unconfined) :
    TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
        dispatcher.cancel()
    }
}

