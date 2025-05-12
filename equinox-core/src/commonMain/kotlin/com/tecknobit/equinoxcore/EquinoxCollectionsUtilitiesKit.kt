package com.tecknobit.equinoxcore

/**
 * Method split in two phases used to first remove all the items from the main collection that are not present in the
 * [supportCollection], then add all the items present in the same support collection but not in the main one.
 *
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
 *
 * @param supportCollection The collection from add the elements not present in the original collection
 */
fun <T> MutableCollection<T>.retainAndAdd(
    supportCollection: Collection<T>,
) {
    val supportCollectionSet = supportCollection.toHashSet()
    retainAll(supportCollectionSet)
    addAll(
        supportCollectionSet.filter { supportItem ->
            supportItem !in this
        }
    )
}

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
 *
 * @param addFrom The starting index from which to add elements from the [supportCollection], following the same constraints
 * @param supportCollection The collection from add the elements not present in the original collection
 */
fun <T> MutableList<T>.retainAndAdd(
    addFrom: Int = lastIndex,
    supportCollection: Collection<T>,
) {
    val originalSize = this.size
    val supportCollectionSet = supportCollection.toHashSet()
    retainAll(supportCollectionSet)
    addAll(
        index = if (addFrom < 0 || addFrom >= originalSize)
            0
        else
            addFrom,
        elements = supportCollectionSet.filter { supportItem ->
            supportItem !in this
        }
    )
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

/**
 * Method used to add an element to a [MutableCollection] if it is not already contained or remove that element if already
 * contained by the collection. The default behavior of the method is checking the existence in the collection of the
 * element to toggle, but it can be used also to dynamically insert or remove an element from the collection from example
 * with checkbox selection, button clicking, etc...
 *
 * @return `true` if the element has been added, `false` if the element has been removed
 *
 * For example:
 * ```kotlin
 * val mainCollection = mutableListOf(1, 2, 3)
 *
 * // not contained element
 * val element = 4
 *
 * mainCollection.toggle(
 *   element = element
 * )
 *
 * println(mainCollection) // the result printed will be 1, 2, 3, 4
 *
 * // contained element
 * val containedElement = 1
 *
 * mainCollection.toggle(
 *   element = containedElement
 * )
 *
 * println(mainCollection) // the result printed will be 2, 3, 4
 * ```
 */
fun <T> MutableCollection<T>.toggle(
    element: T,
    add: Boolean = !contains(element),
): Boolean {
    return if (add) {
        add(element)
        true
    } else {
        remove(element)
        false
    }
}