package com.tecknobit.equinoxmisc.lazypaginationcomposeops

import com.tecknobit.equinoxcore.mergeIfNotContained
import com.tecknobit.equinoxcore.retainAndAdd
import io.github.ahmad_hamwi.compose.pagination.ExperimentalPaginationApi
import io.github.ahmad_hamwi.compose.pagination.PaginationState

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