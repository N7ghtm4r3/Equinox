These are high-level extension functions on `Collection` which provides additional behavior to collection objects

### retainAndAdd

Method split in two phases used to first remove all the items from the main collection that are not present in the
`supportCollection`, then add all the items present in the same support collection but not in the main one

#### Usage

```kotlin
val mainCollection = mutableListOf(1, 2, 3)

val supportCollection = listOf(2, 3, 4)

// apply the retainAndAdd method
mainCollection.retainAndAdd(
    supportCollection = supportCollection
)

println(mainCollection) // the result printed will be 2, 3, 4
```

For `MutableList` objects is available additional `addFrom` parameter to allow to add the items from the
`supportCollection`
from an arbitrary index

```kotlin
val mainCollection = mutableListOf(1, 2, 3)

val supportCollection = listOf(2, 3, 4)

// apply the retainAndAdd method
mainCollection.retainAndAdd(
    addFrom = 1,
    supportCollection = supportCollection
)

println(mainCollection) // the result printed will be 2, 4, 3
```

### mergeIfNotContained

Method used to merge items from the `collectionToMerge` collection that are not present in the main collection

#### Usage

```kotlin
val mainCollection = mutableListOf(1, 2, 3)

val collectionToMerge = listOf(2, 3, 4)

// apply the retainAndAdd method
mainCollection.mergeIfNotContained(
    collectionToMerge = collectionToMerge
)

println(mainCollection) // the result printed will be 1, 2, 3, 4
```

### toggle

Method used to add an element to a `MutableCollection` if it is not already contained or remove that element if already
contained by the collection. The default behavior of the method is checking the existence in the collection of the
element
to toggle, but it can be used also to dynamically insert or remove an element from the collection for example with
checkbox
selection, button clicking, etc...

#### Usage

```kotlin
val mainCollection = mutableListOf(1, 2, 3)

// not contained element
val element = 4

mainCollection.toggle(
    element = element
)

println(mainCollection) // the result printed will be 1, 2, 3, 4

// contained element
val containedElement = 1

mainCollection.toggle(
    element = containedElement
)

println(mainCollection) // the result printed will be 2, 3, 4
```

### contentEquals

Adapter for the `Array.contentEquals` method for a `Collection` object. This method wraps the conversion of a collection
into a `toTypedArray`.

Checks if the two specified arrays are **structurally** equal to one another, two arrays are considered structurally
equal
if they have the same size, and elements at corresponding indices are equal

#### Usage

```kotlin
val list = listOf(1, 2, 3)
val o1 = listOf(1, 2, 3)

// also with infix annotation list contentEquals o1
println(list.contentEquals(o1))  // true

val o2 = listOf(1, 5, 3)

// also with infix annotation list contentEquals o2
println(list.contentEquals(o2)) // false
```

### contentDeepEquals

Adapter for the `Array.contentDeepEquals` method for a `Collection` object. This method wraps the conversion of a
collection
into a `toTypedArray`.

Checks if the two specified arrays are **deeply** equal to one another, two arrays are considered deeply equal if they
have
the same size, and elements at corresponding indices are deeply equal

#### Usage

```kotlin
val list = listOf(1, 2, 3)
val o1 = listOf(1, 2, 3)

// also with infix annotation list contentEquals o1
println(list.contentDeepEquals(o1)) // true

val o2 = listOf(1, 3, 2)

// also with infix annotation list contentDeepEquals o1
println(list.contentDeepEquals(o2))  // false
```