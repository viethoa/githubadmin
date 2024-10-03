package hoa.kv.githubadmin.systemdesign.lazycolumn

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

/**
 * [LazyColumnPaging] A custom version of [LazyColumn] which support [loadMore] function.
 *
 * @param buffer buffer item/s to trigger load more when user scroll to bottom, default value is 1.
 * E.g: list is 10 items, buffer is 1, [loadMore] will be triggered when user scroll to item 8.
 * @param items the list item to generate with lazy column
 * @param itemContent the content of each item from [items]
 * @param firstItemContent for specific content of first item in the list
 * @param loadMore will be triggered when user scroll to bottom of the list
 */
@Composable
fun <T> LazyColumnPaging(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    buffer: Int = 1,
    items: List<T>,
    itemContent: @Composable (T) -> Unit,
    firstItemContent: (@Composable (T) -> Unit)? = null,
    loadMore: () -> Unit
) {
    val reachToBottom: Boolean by remember {
        derivedStateOf { listState.reachToBottom(buffer) }
    }

    // Load more when scroll to bottom
    LaunchedEffect(reachToBottom) {
        if (reachToBottom) {
            loadMore()
        }
    }

    LazyColumn(modifier = modifier, state = listState) {
        items(items = items) { item ->
            if (firstItemContent != null && item == items.first()) {
                firstItemContent(item)
            } else {
                itemContent(item)
            }
        }
    }
}

private fun LazyListState.reachToBottom(buffer: Int): Boolean {
    val lastVisibleItem = this.layoutInfo.visibleItemsInfo.lastOrNull()
    val totalCountWithBuffer = this.layoutInfo.totalItemsCount - buffer
    return lastVisibleItem?.index != 0 && lastVisibleItem?.index == totalCountWithBuffer
}