package com.tecknobit.equinoxcore.util

import kotlin.collections.contentDeepEquals

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
    addFrom: Int? = null,
    supportCollection: Collection<T>,
) {
    val supportCollectionSet = supportCollection.toHashSet()
    retainAll(supportCollectionSet)
    val collectionSetToAdd = supportCollectionSet.filter { supportItem ->
        supportItem !in this
    }
    if (addFrom != null) {
        addAll(
            index = addFrom,
            elements = collectionSetToAdd
        )
    } else {
        addAll(
            elements = collectionSetToAdd
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

/**
 * Method used to add an element to a [MutableCollection] if it is not already contained or remove that element if already
 * contained by the collection. The default behavior of the method is checking the existence in the collection of the
 * element to toggle, but it can be used also to dynamically insert or remove an element from the collection for example
 * with checkbox selection, button clicking, etc...
 *
 * @param element The element to toggle
 * @param add The predicated used to determine if add or remove the element
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

/**
 * Adapter for the [Array.contentEquals] method for a [Collection] object. This method wraps the conversion of a collection
 * into a [toTypedArray]. The following documentation is the same of the original method.
 *
 * Checks if the two specified arrays are *structurally* equal to one another.
 *
 * Two arrays are considered structurally equal if they have the same size, and elements at corresponding indices are equal.
 * Elements are compared for equality using the [equals][Any.equals] function.
 * For floating point numbers, this means `NaN` is equal to itself and `-0.0` is not equal to `0.0`.
 *
 * The arrays are also considered structurally equal if both are `null`.
 *
 * If the arrays contain nested arrays, use [contentDeepEquals] to recursively compare their elements
 *
 * For example:
 * ```kotlin
 * val list = listOf(1, 2, 3)
 * val o1 = listOf(1, 2, 3)
 *
 * // also with infix annotation list contentEquals o1
 * println(list.contentEquals(o1))  // true
 *
 * val o2 = listOf(1, 5, 3)
 *
 * // also with infix annotation list contentEquals o2
 * println(list.contentEquals(o2)) // false
 * ```
 *
 * @param other the array to compare with this array
 *
 * @return `true` if the two arrays are structurally equal, `false` otherwise as [Boolean]
 *
 * @since 1.1.9
 */
@ExperimentalStdlibApi
inline infix fun <reified T> Collection<T>.contentEquals(
    other: Collection<T>,
): Boolean {
    return this.toTypedArray() contentEquals other.toTypedArray()
}

/**
 * Adapter for the [Array.contentDeepEquals] method for a [Collection] object. This method wraps the conversion of a collection
 * into a [toTypedArray]. The following documentation is the same of the original method.
 *
 * Checks if the two specified arrays are *deeply* equal to one another.
 *
 * Two arrays are considered deeply equal if they have the same size, and elements at corresponding indices are deeply equal.
 * That is, if two corresponding elements are nested arrays, they are also compared deeply.
 * Elements of other types are compared for equality using the [equals][Any.equals] function.
 * For floating point numbers, this means `NaN` is equal to itself and `-0.0` is not equal to `0.0`.
 *
 * The arrays are also considered deeply equal if both are `null`.
 *
 * If any of the arrays contain themselves at any nesting level, the behavior is undefined.
 *
 * For example:
 * ```kotlin
 * val list = listOf(1, 2, 3)
 * val o1 = listOf(1, 2, 3)
 *
 * // also with infix annotation list contentEquals o1
 * println(list.contentDeepEquals(o1)) // true
 *
 * val o2 = listOf(1, 3, 2)
 *
 * // also with infix annotation list contentDeepEquals o1
 * println(list.contentDeepEquals(o2))  // false
 * ```
 *
 * @param other the array to compare deeply with this array
 *
 * @return `true` if the two arrays are deeply equal, `false` otherwise as [Boolean]
 */
@ExperimentalStdlibApi
inline infix fun <reified T> Collection<T>.contentDeepEquals(
    other: Collection<T>,
): Boolean {
    return this.toTypedArray() contentDeepEquals other.toTypedArray()
}