---
title: "FilteredQuery"
slug: "backend/APIs/FilteredQuery"
---

This API allows to create dynamic queries with filter parameters
leveraging [regex expressions](https://www.geeksforgeeks.org/dsa/write-regular-expressions/).

Under the hood, it uses the **CriteriaBuilder** API, making it particularly useful for building complex queries with
multiple
filters that can be easily applied.

The [FiltersExtractor](/Equinox/backend/Annotations/filters/FiltersExtractor/) and
[FiltersAdder](/Equinox/backend/Annotations/filters/FiltersAdder/) annotations belong to the
`com.tecknobit.equinoxbackend.annotations.filters` package. Use them to identify methods that extract
filters and add them to a query.

## Implementation

### Create your own FilteredQuery instance

You need to create your own `FilteredQuery` instance by specifying the object (entity) on which the queries will operate


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


```java
public class CarsQuery extends FilteredQuery<Car> { // your entity

    public CarsQuery(Class<Car> entityType, EntityManager entityManager, Set<String> rawFilters) {
        super(entityType, entityManager, rawFilters);
    }

    ...

}
```

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```kotlin
class CarsQuery(
    entityType: Class<Car>,
    entityManager: EntityManager,
    rawFilters: Set<String>
) : FilteredQuery<Car>( // your entity
    entityType, 
    entityManager,
    rawFilters
) {
    ...
}
```

</div>

</div>

### Create the regex for the filter you want to use

The regular expression will be used to filter the raw data and correctly assign each value to its corresponding category


<div class="equinox-tabs" id="equinox-tabs-1">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-1-tab-0" aria-controls="equinox-tabs-1-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-1-tab-1" aria-controls="equinox-tabs-1-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-1-panel-0" aria-labelledby="equinox-tabs-1-tab-0" tabindex="0">


```java
private static final String MODEL_REGEX = // the regex to use to extract from the raw filters the matching model names

private static final Pattern MODEL_PATTERN = // pattern use to match the raw filters    
```

</div>

<div role="tabpanel" id="equinox-tabs-1-panel-1" aria-labelledby="equinox-tabs-1-tab-1" tabindex="0">


```kotlin
companion object {
    private const val MODEL_REGEX: String = // the regex to use to extract from the raw filters the matching model names

    private val MODEL_PATTERN: Regex = Regex(MODEL_REGEX) // used to match the raw filters    
}
```

</div>

</div>

### Override fillPredicates method

In this way you can invoke your custom filters methods


<div class="equinox-tabs" id="equinox-tabs-2">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-2-tab-0" aria-controls="equinox-tabs-2-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-2-tab-1" aria-controls="equinox-tabs-2-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-2-panel-0" aria-labelledby="equinox-tabs-2-tab-0" tabindex="0">


```java
@Override
protected void fillPredicates() {
    addModelNameFilters();
}
```

</div>

<div role="tabpanel" id="equinox-tabs-2-panel-1" aria-labelledby="equinox-tabs-2-tab-1" tabindex="0">


```kotlin
override fun fillPredicates() {
    addModelNameFilters()
}
```

</div>

</div>

### Use the custom filters

<h4>Extract the specific filters</h4>

Pass the `MODEL_PATTERN` to the provided `extractFiltersByPattern` method to extract the specific filters


<div class="equinox-tabs" id="equinox-tabs-3">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-3-tab-0" aria-controls="equinox-tabs-3-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-3-tab-1" aria-controls="equinox-tabs-3-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-3-panel-0" aria-labelledby="equinox-tabs-3-tab-0" tabindex="0">


```java
@FiltersExtractor
private HashSet<String> getModelNameFilters() {
    return extractFiltersByPattern(MODEL_PATTERN);
}
```

</div>

<div role="tabpanel" id="equinox-tabs-3-panel-1" aria-labelledby="equinox-tabs-3-tab-1" tabindex="0">


```kotlin
@FiltersExtractor
private fun getModelNameFilters() : HashSet<String> {
    return extractFiltersByPattern(MODEL_PATTERN)
}
```

</div>

</div>

<h4>Add the extracted filters</h4>

Create a predicate with the extracted filter and add to the inherited `predicates` to correctly perform the query


<div class="equinox-tabs" id="equinox-tabs-4">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-4-tab-0" aria-controls="equinox-tabs-4-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-4-tab-1" aria-controls="equinox-tabs-4-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-4-panel-0" aria-labelledby="equinox-tabs-4-tab-0" tabindex="0">


```java
@FiltersAdder
private void addModelNameFilters() {
    Set<String> names = getModelNameFilters();
    if (names != null) {
        Predicate nameIn = root.get("model").in(names);
        predicates.add(nameIn); // add the created predicate to the predicates list
    }
}
```

</div>

<div role="tabpanel" id="equinox-tabs-4-panel-1" aria-labelledby="equinox-tabs-4-tab-1" tabindex="0">


```kotlin
@FiltersAdder
fun addModelNameFilters() {
    val names: Set<String>? = getModelNameFilters()
    names?.let {
        val nameIn = root.get<String>("model").`in`(it)
        predicates.add(nameIn) // add the created predicate to the predicates list
    }
}
```

</div>

</div>

## Usage

You can execute the filtered query from a service as follows:


<div class="equinox-tabs" id="equinox-tabs-5">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-5-tab-0" aria-controls="equinox-tabs-5-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-5-tab-1" aria-controls="equinox-tabs-5-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-5-panel-0" aria-labelledby="equinox-tabs-5-tab-0" tabindex="0">


```java
@Service
@Transactional // required
public class CarsService {

    // instantiate a custom entity manager
    @PersistenceContext
    protected EntityManager entityManager;

    public List<Car> filterCars(Set<String> rawFilters) {
        CarsQuery carsQuery = new CarsQuery(
                Car.class,
                entityManager,
                rawFilters
        );
        return carsQuery.getEntities(); // execute the query and return the results
    }

}
```

</div>

<div role="tabpanel" id="equinox-tabs-5-panel-1" aria-labelledby="equinox-tabs-5-tab-1" tabindex="0">


```kotlin
@Service
@Transactional // Required for transactional operations
class CarsService {

    // Inject a custom entity manager
    @PersistenceContext
    protected lateinit var entityManager: EntityManager

    fun filterCars(rawFilters: Set<String>): List<Car> {
        val carsQuery = CarsQuery(
            Car::class.java,
            entityManager,
            rawFilters
        )
        return carsQuery.entities // Execute the query and return the results
    }

}
```

</div>

</div>

## Overview

The completed implementation is the following:


<div class="equinox-tabs" id="equinox-tabs-6">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-6-tab-0" aria-controls="equinox-tabs-6-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-6-tab-1" aria-controls="equinox-tabs-6-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-6-panel-0" aria-labelledby="equinox-tabs-6-tab-0" tabindex="0">


```java
public class CarsQuery extends FilteredQuery<Car> { // your entity

    private static final String MODEL_REGEX = // the regex to use to extract from the raw filters the matching model names

    private static final Pattern MODEL_PATTERN = // pattern use to match the raw filters   

    public CarsQuery(Class<Car> entityType, EntityManager entityManager, Set<String> rawFilters) {
        super(entityType, entityManager, rawFilters);
    }

    @Override
    protected void fillPredicates() {
        addModelNameFilters();
    }

    @FiltersExtractor
    private HashSet<String> getModelNameFilters() {
        return extractFiltersByPattern(MODEL_PATTERN);
    }

    @FiltersAdder
    private void addModelNameFilters() {
        HashSet<String> names = getModelNameFilters();
        if (names != null) {
            Predicate nameIn = root.get("model").in(names);
            predicates.add(nameIn); // add the created predicate to the predicates list
        }
    }

}
```

</div>

<div role="tabpanel" id="equinox-tabs-6-panel-1" aria-labelledby="equinox-tabs-6-tab-1" tabindex="0">


```kotlin
class CarsQuery(
    entityType: Class<Car>,
    entityManager: EntityManager,
    rawFilters: Set<String>
) : FilteredQuery<Car>( // your entity
    entityType, 
    entityManager,
    rawFilters
) {
    
    companion object {
        private const val MODEL_REGEX: String = // the regex to use to extract from the raw filters the matching model names
    
        private val MODEL_PATTERN: Regex = Regex(MODEL_REGEX) // used to match the raw filters    
    }   

    override fun fillPredicates() {
        addModelNameFilters()
    }

    @FiltersExtractor
    private fun getModelNameFilters() : HashSet<String> {
        return extractFiltersByPattern(MODEL_PATTERN)
    }

    @FiltersAdder
    fun addModelNameFilters() {
        val names: Set<String>? = getModelNameFilters()
        names?.let {
            val nameIn = root.get<String>("model").`in`(it)
            predicates.add(nameIn) // add the created predicate to the predicates list
        }
    }

}
```

</div>

</div>
