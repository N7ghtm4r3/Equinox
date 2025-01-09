# FilteredQuery

Allows you to create dynamic queries with filters parameters.

Under the hood works with the **CriteriaBuilder** api, and it is particular useful to create complex queries with
multiple
filters to use and apply

## Implementation

### Create your own custom class

```java
public class CarsQuery extends FilteredQuery<Car> { // your entity

    public CarsQuery(Class<Home> entityType, EntityManager entityManager, Set<String> rawFilters) {
        super(entityType, entityManager, rawFilters);
    }

    ...

}
```

### Create the regex of the filter you want to use

```java
private static final String MODEL_REGEX = // the regex to use to extract from the raw filters the matching model names

private static final Pattern MODEL_PATTERN = // pattern use to match the raw filters
```

### Override the fillPredicatesMethod

```java
@Override
protected void fillPredicates() {
    addModelNameFilters(); // invoke your custom filters methods
}
```

### Add the custom filters

```java
private void addModelNameFilters() {
    HashSet<String> names = getModelNameFilters(); // extract the specific filters from the rawFilters list
    if (names != null) {
        Predicate nameIn = root.get("model").in(names);
        predicates.add(nameIn); // add the created predicate to the predicates list
    }
}

@Wrapper
private HashSet<String> getModelNameFilters() {
    return extractFiltersByPattern(MODEL_PATTERN);
}
```

### Execute the query from a service

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
        return carsQuery.getEntities(); // execute the query and return the resuls
    }

}
```

## Support

If you need help using the library or encounter any problems or bugs, please contact us via the following links:

- Support via <a href="mailto:infotecknobitcompany@gmail.com">email</a>
- Support via <a href="https://github.com/N7ghtm4r3/Equinox/issues/new">GitHub</a>

Thank you for your help!

## Badges

[![](https://img.shields.io/badge/Google_Play-414141?style=for-the-badge&logo=google-play&logoColor=white)](https://play.google.com/store/apps/developer?id=Tecknobit)

[![](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)](https://spring.io/projects/spring-boot) [![](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/)

## Donations

If you want support project and developer

| Crypto                                                                                              | Address                                          | Network  |
|-----------------------------------------------------------------------------------------------------|--------------------------------------------------|----------|
| ![](https://img.shields.io/badge/Bitcoin-000000?style=for-the-badge&logo=bitcoin&logoColor=white)   | **3H3jyCzcRmnxroHthuXh22GXXSmizin2yp**           | Bitcoin  |
| ![](https://img.shields.io/badge/Ethereum-3C3C3D?style=for-the-badge&logo=Ethereum&logoColor=white) | **0x1b45bc41efeb3ed655b078f95086f25fc83345c4**   | Ethereum |
| ![](https://img.shields.io/badge/Solana-000?style=for-the-badge&logo=Solana&logoColor=9945FF)       | **AtPjUnxYFHw3a6Si9HinQtyPTqsdbfdKX3dJ1xiDjbrL** | Solana   |

If you want support project and developer
with <a href="https://www.paypal.com/donate/?hosted_button_id=5QMN5UQH7LDT4">PayPal</a>

Copyright © 2025 Tecknobit