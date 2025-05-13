package com.tecknobit.equinoxmisc.lazypaginationcomposeops

import com.tecknobit.equinoxcore.mergeIfNotContained
import com.tecknobit.equinoxcore.retainAndAdd
import com.tecknobit.equinoxcore.toggle
import io.github.ahmad_hamwi.compose.pagination.ExperimentalPaginationApi
import io.github.ahmad_hamwi.compose.pagination.PaginationState

/**
 * Method used to add in-place a new item to the current [PaginationState.allItems] list
 *
 * @param item The item to add
 * @param nextPageKey The key of the next page to request
 * @param isLastPage Whether is the last page to request
 */
@ExperimentalPaginationApi
fun <KEY, T> PaginationState<KEY, T>.addInPlace(
    item: T,
    nextPageKey: KEY,
    isLastPage: Boolean = false,
) {
    val tmpItems = allItems.orEmpty().toMutableList()
    tmpItems.add(item)
    appendPageWithUpdates(
        allItems = tmpItems,
        nextPageKey = nextPageKey,
        isLastPage = isLastPage
    )
}

/**
 * Method used to add in-place a collection of items to the current [PaginationState.allItems] list
 *
 * @param items The items collection to add
 * @param nextPageKey The key of the next page to request
 * @param isLastPage Whether is the last page to request
 */
@ExperimentalPaginationApi
fun <KEY, T> PaginationState<KEY, T>.addAllInPlace(
    items: Collection<T>?,
    nextPageKey: KEY,
    isLastPage: Boolean = false,
) {
    val tmpItems = allItems.orEmpty().toMutableList()
    tmpItems.addAll(items.orEmpty())
    appendPageWithUpdates(
        allItems = tmpItems,
        nextPageKey = nextPageKey,
        isLastPage = isLastPage
    )
}

/**
 * Method used to remove in-place an item from the current [PaginationState.allItems] list
 *
 * @param item The item to remove
 * @param nextPageKey The key of the next page to request
 * @param isLastPage Whether is the last page to request
 */
@ExperimentalPaginationApi
fun <KEY, T> PaginationState<KEY, T>.removeInPlace(
    item: T,
    nextPageKey: KEY,
    isLastPage: Boolean = false,
) {
    val tmpItems = allItems.orEmpty().toMutableList()
    tmpItems.remove(item)
    appendPageWithUpdates(
        allItems = tmpItems,
        nextPageKey = nextPageKey,
        isLastPage = isLastPage
    )
}

/**
 * Method used to remove in-place a collection of items from the current [PaginationState.allItems] list
 *
 * @param items The items collection to remove
 * @param nextPageKey The key of the next page to request
 * @param isLastPage Whether is the last page to request
 */
@ExperimentalPaginationApi
fun <KEY, T> PaginationState<KEY, T>.removeAllInPlace(
    items: Collection<T>?,
    nextPageKey: KEY,
    isLastPage: Boolean = false,
) {
    val tmpItems = allItems.orEmpty().toMutableList()
    tmpItems.removeAll(items.orEmpty())
    appendPageWithUpdates(
        allItems = tmpItems,
        nextPageKey = nextPageKey,
        isLastPage = isLastPage
    )
}

/**
 * Method used to update in-place the current [PaginationState.allItems] list
 *
 * @param updateCondition The predicate to use to find the items to update
 * @param updateAction The update action which update an item in the list
 */
@ExperimentalPaginationApi
fun <KEY, T> PaginationState<KEY, T>.updateInPlace(
    updateCondition: (T) -> Boolean,
    updateAction: (T) -> Unit,
) {
    allItems.orEmpty().forEach { itemInList ->
        if (updateCondition(itemInList))
            updateAction(itemInList)
    }
}

/**
 * Method split in two phases used to first remove all the items from the current [PaginationState.allItems] that are not
 * present in the [items] collection, then add all the items present in the same items collection but not in the main one.
 * For example:
 * ```kotlin
 * val paginationState = PaginationState<Int, Int>(
 *     initialPageKey = 0,
 *     onRequestPage = { page ->
 *         appendPage(
 *            nextPageKey = page + 1,
 *            items = listOf(1, 2, 3)
 *         )
 *     }
 * )
 *
 * paginationState.retainAndAllInPlace(
 *     items = listOf(2, 3, 4),
 *     nextPageKey = 2,
 *     isLastPage = true
 * )
 *
 * // the paginationState will have: 2, 3, 4
 * ```
 *
 * The duplicate values will be considered as one element, so will be merged
 *
 * @param items The items collection used as support to retain and then add
 * @param nextPageKey The key of the next page to request
 * @param isLastPage Whether is the last page to request
 */
@ExperimentalPaginationApi
fun <KEY, T> PaginationState<KEY, T>.retainAndAllInPlace(
    items: Collection<T>?,
    nextPageKey: KEY,
    isLastPage: Boolean = false,
) {
    val mutableItems = allItems.orEmpty().toMutableList()
    mutableItems.retainAndAdd(
        supportCollection = items.orEmpty()
    )
    appendPageWithUpdates(
        allItems = mutableItems,
        nextPageKey = nextPageKey,
        isLastPage = isLastPage
    )
}

/**
 * Method used to merge items from the [items] collection that are not present in the [PaginationState.allItems].
 * For example:
 * ```kotlin
 * val paginationState = PaginationState<Int, Int>(
 *     initialPageKey = 0,
 *     onRequestPage = { page ->
 *         appendPage(
 *             nextPageKey = page + 1,
 *             items = listOf(1, 2, 3)
 *         )
 *     }
 * )
 *
 * paginationState.mergeIfNotContained(
 *     items = listOf(2, 3, 4),
 *     nextPageKey = 2,
 *     isLastPage = true
 * )
 *
 * // the paginationState will have: 1, 2, 3, 4
 * ```
 *
 * The duplicate values will be considered as one element, so will be merged
 *
 * @param items The items collection to merge
 * @param nextPageKey The key of the next page to request
 * @param isLastPage Whether is the last page to request
 */
@ExperimentalPaginationApi
fun <KEY, T> PaginationState<KEY, T>.mergeIfNotContained(
    items: Collection<T>?,
    nextPageKey: KEY,
    isLastPage: Boolean = false,
) {
    val mutableItems = allItems.orEmpty().toMutableList()
    mutableItems.mergeIfNotContained(
        collectionToMerge = items.orEmpty()
    )
    appendPageWithUpdates(
        allItems = mutableItems,
        nextPageKey = nextPageKey,
        isLastPage = isLastPage
    )
}

/**
 * Method used to add in-place an item to the current [PaginationState.allItems] if it is not already contained or remove
 * that item if already contained by the collection. The default behavior of the method is checking the existence in the
 * collection of the element to toggle, but it can be used also to dynamically insert or remove an element from the collection
 * for example with checkbox selection, button clicking, etc...
 *
 * For example:
 *
 * ```kotlin
 * val paginationState = PaginationState<Int, Int>(
 *     initialPageKey = 0,
 *     onRequestPage = { page ->
 *         appendPage(
 *             nextPageKey = page + 1,
 *             items = listOf(1, 2, 3)
 *         )
 *     }
 * )
 *
 * // not contained item
 * val item = 4
 *
 * paginationState.toggle(
 *    item = item
 * )
 *
 * // the paginationState will have: 1, 2, 3, 4
 *
 * // contained item
 * val containedItem = 1
 *
 * paginationState.toggle(
 *    item = containedItem
 * )
 *
 * // the paginationState will have: 2, 3, 4
 * ```
 *
 * @param item The element to toggle
 * @param nextPageKey The key of the next page to request
 * @param isLastPage Whether is the last page to request
 *
 * @return `true` if the item has been added, `false` if the item has been removed
 */
@ExperimentalPaginationApi
fun <KEY, T> PaginationState<KEY, T>.toggle(
    item: T,
    nextPageKey: KEY,
    isLastPage: Boolean = false,
): Boolean {
    val mutableItems = allItems.orEmpty().toMutableList()
    val result = mutableItems.toggle(
        element = item
    )
    appendPageWithUpdates(
        allItems = mutableItems,
        nextPageKey = nextPageKey,
        isLastPage = isLastPage
    )
    return result
}