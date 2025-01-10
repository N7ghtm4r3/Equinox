## Requester

### Usage/Examples

#### Create your Requester

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

    // exec a GET request, this works also for other request methods available
    suspend fun sendYourRequest(): JSONObject {
        return execGet(
            endpoint = "yourEndpoint"
        )
    }

}
```

#### Manage the responses

```kotlin
fun main() {
    val requester = YourRequester(
        host = "host",
        userId = "userId",
        userToken = "userToken",
        connectionErrorMessage = "connectionErrorMessage",
        enableCertificatesValidation = true / false
    )

    // send the request and manage the response scenarios
    requester.sendRequest(
        request = {
            sendYourRequest()
        },
        onSuccess = {
            // manage a successful request
        },
        onFailure = {
            // manage a failed request
        },
        onConnectionError = {
            // manage a connection error
        }
    )

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

}
```

## Support

If you need help using the library or encounter any problems or bugs, please contact us via the following links:

- Support via <a href="mailto:infotecknobitcompany@gmail.com">email</a>
- Support via <a href="https://github.com/N7ghtm4r3/Equinox/issues/new">GitHub</a>

Thank you for your help!

## Badges

[![](https://img.shields.io/badge/Google_Play-414141?style=for-the-badge&logo=google-play&logoColor=white)](https://play.google.com/store/apps/developer?id=Tecknobit)
[![Twitter](https://img.shields.io/badge/Twitter-1DA1F2?style=for-the-badge&logo=twitter&logoColor=white)](https://twitter.com/tecknobit)

[![](https://img.shields.io/badge/Jetpack%20Compose-4285F4.svg?style=for-the-badge&logo=Jetpack-Compose&logoColor=white)](https://www.jetbrains.com/lp/compose-multiplatform/)
[![](https://img.shields.io/badge/Kotlin-B125EA?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)

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
