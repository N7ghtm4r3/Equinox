package com.tecknobit.equinoxcore

/**
 * Method split in two phases used to first remove all the items from the main collection that are not present in the
 * [supportCollection], then add all the items present in the same support collection but not in the main one.
 * For example:
 * ```kotlin
 * val mainCollection = mutableListOf(1, 2, 3)
 *
 * val supportCollection = listOf(2, 3, 4)
 *
 * // apply the retainAndAdd method
 * mainCollection.retainAndAdd(
 *   supportCollection = supportCollection
 * )
 *
 * println(mainCollection) // the result printed will be 2, 3, 4
 *
 * ```
 *
 * The duplicate values will be considered as one element, so will be merged
 */
fun <T> MutableCollection<T>.retainAndAdd(
    supportCollection: Collection<T>,
) {
    val supportCollectionSet = supportCollection.toHashSet()
    this.run {
        retainAll(supportCollectionSet)
        addAll(
            supportCollectionSet.filter { supportItem ->
                supportItem !in this
            }
        )
    }
}

/**
 * Method used to merge items from the [collectionToMerge] collection that are not present in the main collection.
 * For example:
 * ```kotlin
 * val mainCollection = mutableListOf(1, 2, 3)
 *
 * val collectionToMerge = listOf(2, 3, 4)
 *
 * // apply the retainAndAdd method
 * mainCollection.mergeIfNotContained(
 *   collectionToMerge = collectionToMerge
 * )
 *
 * println(mainCollection) // the result printed will be 1, 2, 3, 4
 * ```
 *
 * The duplicate values will be considered as one element, so will be merged
 */
fun <T> MutableCollection<T>.mergeIfNotContained(
    collectionToMerge: Collection<T>,
) {
    val supportSet = this.toHashSet()
    collectionToMerge.forEach { element ->
        if (!supportSet.contains(element))
            this.add(element)
    }
}