@file:Suppress("UNCHECKED_CAST")

package me.tatarka.inject.ponyinject

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.ItemSnapshotList
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.concurrent.TimeUnit

/**
 * Helper to get a non-empty snapshot from [PagingData].
 */
suspend fun <T : Any> PagingData<T>.waitForSnapshot(
    timeoutMillis: Long = TimeUnit.SECONDS.toMillis(5)
): ItemSnapshotList<T> {
    val differ = AsyncPagingDataDiffer(
        diffCallback = NeverEqualDiffItemCallback as DiffUtil.ItemCallback<T>,
        updateCallback = EmptyListUpdateCallback,
        mainDispatcher = Dispatchers.Unconfined,
        workerDispatcher = Dispatchers.Unconfined,
    )
    return coroutineScope {
        val submit = async {
            differ.submitData(this@waitForSnapshot)
        }
        val snapshot = withTimeout(timeoutMillis) {
            var snapshot: ItemSnapshotList<T>
            do {
                snapshot = differ.snapshot()
                yield()
            } while (snapshot.isEmpty() && differ.loadStateFlow.first().refresh !is LoadState.Error)
            snapshot
        }
        submit.cancel()
        snapshot
    }
}

/**
 * Helper to get an error from a [PagingData].
 */
suspend fun <T : Any> PagingData<T>.waitForError(
    timeoutMillis: Long = TimeUnit.SECONDS.toMillis(5)
): Throwable {
    val differ = AsyncPagingDataDiffer(
        diffCallback = NeverEqualDiffItemCallback as DiffUtil.ItemCallback<T>,
        updateCallback = EmptyListUpdateCallback,
        mainDispatcher = Dispatchers.Unconfined,
        workerDispatcher = Dispatchers.Unconfined,
    )
    return coroutineScope {
        val submit = async {
            differ.submitData(this@waitForError)
        }
        val error = withTimeout(timeoutMillis) {
            var loadState: LoadState
            do {
                loadState = differ.loadStateFlow.first().refresh
                yield()
            } while (loadState !is LoadState.Error)
            loadState.error
        }
        submit.cancel()
        error
    }
}

private object NeverEqualDiffItemCallback : DiffUtil.ItemCallback<Any?>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any) = false
    override fun areContentsTheSame(oldItem: Any, newItem: Any) = false
}

private object EmptyListUpdateCallback : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {
    }

    override fun onRemoved(position: Int, count: Int) {
    }

    override fun onMoved(fromPosition: Int, toPosition: Int) {
    }

    override fun onChanged(position: Int, count: Int, payload: Any?) {
    }
}