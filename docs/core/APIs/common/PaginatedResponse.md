This API allows the pagination of backend responses, making data retrieval easier for clients.  
It leverages the [kotlinx serialization library](https://github.com/Kotlin/kotlinx.serialization/tree/master)
to efficiently serialize data and enable flexible communication between client and server

## Usage

In this example will be shown a backend based on **Spring Boot** and a **Compose Multiplatform** client application which
retrieves the data using a [Requester](Requester.md)

### Backend side

#### Creating the entity class

This class will be used by the backend to store the data related to the `Dummy` class, but will be used also to return 
the paginated data

```java
@Entity
public class Dummy extends EquinoxItem  {

    @Column
    private final String name;

    public Dummy(String id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
```

#### Creating the service

This is just an example of a possible service implementation, take a closer look just to the `getDummies` method

```java
@Service
public class DummyService  {

    @Autowired
    private final DummiesRepository dummiesRepository;

    public DummyService(DummiesRepository dummiesRepository) {
        this.dummiesRepository = dummiesRepository;
    }

    public PaginatedResponse<Dummy> getDummies(int page, int pageSize) {

        // count the total values
        long total = dummiesRepository.count();

        // create the pageable to use in the query
        Pageable pageable = PageRequest.of(page, pageSize);

        // a query example
        List<Dummy> dummies = dummiesRepository.getDummies(pageable);

        // return the paginated data
        return new PaginatedResponse<>(
                dummies,
                page, // if not specified default is 0
                pageSize, // if not specified default is 10
                total
        );

    }

}
```

The data retrieved from the database will be directly paginated for the requested list of Dummy, making it ready to be 
read by the client through the same API

### Client side

#### kotlinx-serialization implementation

To automatically serialize and then retrieve the data from the API, you have to implement the `kotlinx-serialization` library
and the related plugin as follows:

###### Plugin

```kotlin
plugins {
    kotlin("plugin.serialization") version "latest-version"
}
```

###### Library

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation "org.jetbrains.kotlinx:kotlinx-serialization-json:latest-version"
}
```

Check out the [official repository](https://github.com/Kotlin/kotlinx.serialization/tree/master) to find more information
or the latest version available

#### Creating the data class

This class will be used by the client to use the information retrieved from the backend

```kotlin
@Serializable // required annotation
data class Dummy(
    val id: String,
    val name: String
)
```

#### Usage

Using, for example, a `Requester` instance we can request and then retrieve the paginated response of our 
[Dummy class](#creating-the-data-class) as follows:

```kotlin
fun dummyRequest() {
    viewModelScope.launch {
        requester.sendPaginatedRequest(
            request = {
                getDummies(
                    page = page, // if not specified default is 0
                    page = pageSize, // if not specified default is 10
                )
            },
            serialiazer = Dummy.serializer(), // required
            onSuccess = { page ->
                // use the serialized page from the response
                println(page.data) // list of paged Dummy
            },
            onFailure = {
                // handle a failed request
            },
            onConnectionError = {
                // handle a connection error
            }
        )
    }
}
```