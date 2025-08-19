This API allows you to create dynamic queries with filter parameters
leveraging [regex expressions](https://www.geeksforgeeks.org/dsa/write-regular-expressions/).

Under the hood, it uses the **CriteriaBuilder** API, making it particularly useful for building complex queries with
multiple
filters that can be easily applied.

## Implementation

### Create your own FilteredQuery instance

You need to create your own `FilteredQuery` instance by specifying the object (entity) on which the queries will operate

=== "Java"

    ```java
    public class CarsQuery extends FilteredQuery<Car> { // your entity

        public CarsQuery(Class<Car> entityType, EntityManager entityManager, Set<String> rawFilters) {
            super(entityType, entityManager, rawFilters);
        }
    
        ...
    
    }
    ```

=== "Kotlin"

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

### Create the regex for the filter you want to use

The regular expression will be used to filter the raw data and correctly assign each value to its corresponding category

=== "Java"

    ```java
    private static final String MODEL_REGEX = // the regex to use to extract from the raw filters the matching model names

    private static final Pattern MODEL_PATTERN = // pattern use to match the raw filters    
    ```

=== "Kotlin"

    ```kotlin
    companion object {
        private const val MODEL_REGEX: String = // the regex to use to extract from the raw filters the matching model names
    
        private val MODEL_PATTERN: Regex = Regex(MODEL_REGEX) // used to match the raw filters    
    }
    ```

### Override fillPredicates method

In this way you can invoke your custom filters methods

=== "Java"

    ```java
    @Override
    protected void fillPredicates() {
        addModelNameFilters();
    }
    ```

=== "Kotlin"

    ```kotlin
    override fun fillPredicates() {
        addModelNameFilters()
    }
    ```

### Use the custom filters

<h4>Extract the specific filters</h4>

Pass the `MODEL_PATTERN` to the provided `extractFiltersByPattern` method to extract the specific filters

=== "Java"

    ```java
    @FiltersExtractor
    private HashSet<String> getModelNameFilters() {
        return extractFiltersByPattern(MODEL_PATTERN);
    }
    ```

=== "Kotlin"

    ```kotlin
    @FiltersExtractor
    private fun getModelNameFilters() : HashSet<String> {
        return extractFiltersByPattern(MODEL_PATTERN)
    }
    ```

<h4>Add the extracted filters</h4>

Create a predicate with the extracted filter and add to the inherited `predicates` to correctly perform the query

=== "Java"

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

=== "Kotlin"

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

## Usage

You can execute the filtered query from a service as follows:

=== "Java"

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

=== "Kotlin"

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

## Overview

The completed implementation is the following:

=== "Java"

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

=== "Kotlin"

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