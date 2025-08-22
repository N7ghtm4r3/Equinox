This API allows to send API requests with your clients providing the basic methods to build
your own customized requester following the **Equinox**'s philosophy. It is based on top of [Ktor](https://ktor.io/) to
cover all the available platforms

## Implementation

Extending the `Requester` can be created a tailored instance to use following your requirements

```kotlin
class YourRequester(
    host: String,
    userId: String? = null,
    userToken: String? = null,
    connectionErrorMessage: String,
    enableCertificatesValidation: Boolean = false
): Requester( // extends the Requester to inherit the base methods
    host = host,
    userId = userId,
    userToken = userToken,
    connectionErrorMessage = connectionErrorMessage,
    enableCertificatesValidation = enableCertificatesValidation
) {

    // add the posssibility to send a custom GET request
    suspend fun sendYourRequest(): JsonObject {
        return execGet(
            endpoint = "yourEndpoint"
        )
    }

}
```

## Usage

### Initialize Requester instance

```kotlin
val requester = YourRequester(
    host = "host",
    userId = "userId",
    userToken = "userToken",
    connectionErrorMessage = "connectionErrorMessage",
    enableCertificatesValidation = true / false
)
```

### Send requests

To improve performance and follow best practices, the core request methods are marked as `suspend` by default, leaving 
their execution to be handled with `coroutines` such `viewModelScope` or similar

#### Normal request

Send a normal request and handle its response as follows:

```kotlin
requester.sendRequest(
    request = { sendYourRequest() },
    onSuccess = {
        // handle a successful request
    },
    onFailure = {
        // handle a failed request
    },
    onConnectionError = {
        // handle a connection error
    }
)
```

#### Paginated request

Leveraging the 

```kotlin
// send a request with a paginated formatted response
requester.sendPaginatedRequest(
    request = {
        sendYourRequest()
    },
    serialiazr = Home.serializer(),
    onSuccess = { page ->
        // use the page formatted from the response
        println(page.data) // list of homes instantiated with the supplier lambda function
    },
    onFailure = {
        // manage a failed request
    },
    onConnectionError = {
        // manage a connection error
    }
)
```