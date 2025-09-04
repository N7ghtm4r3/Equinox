This annotation is used to make the request path, path parameters, query parameters,
and body parameters more readable and easier to manage in API request methods. It supports multiple
types of parameters including path, query, and body parameters, helping to organize the API request
construction in a more structured and understandable way

## Usage

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
    @RequestPath(
        path = "path_of_the_request",
        method = GET
    )
    suspend fun sendYourRequest(): JsonObject {
        return execGet(
            endpoint = "yourEndpoint"
        )
    }

}
```