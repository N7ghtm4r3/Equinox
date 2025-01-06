## FetcherManager

### Usage/Examples

#### Use directly the FetcherManager

```kotlin
class ExampleClass {

  private val refreshRoutine = CoroutineScope(Dispatchers.Default)

  private val fetcherManager = FetcherManager(refreshRoutine)

  fun example() { 
      fetcherManager.execute(
         currentContext = this::class.java,
         routine = {
           // your routine
         },
         repeatRoutine = repeatRoutine,
         refreshDelay = delay
      )
  }

  fun example1() { 
      fetcherManager.suspend()
  }

}
```

#### Use the wrapper interface 

```kotlin
// the super class from which the other classes will be inherited
abstract class AbstractClass : FetcherManagerWrapper {

    protected val refreshRoutine = CoroutineScope(Dispatchers.Default)

    private val fetcherManager = FetcherManager(refreshRoutine)

    override fun canRefresherStart(): Boolean {
        return fetcherManager.canStart()
    }

    override fun suspendRefresher() {
        fetcherManager.suspend()
    }

    override fun restartRefresher() {
        fetcherManager.restart()
    }

    override fun continueToFetch(currentContext: Class<>): Boolean {
        return fetcherManager.continueToFetch(currentContext)
    }

    override fun execRefreshingRoutine(
        currentContext: Class<*>,
        routine: () -> Unit,
        repeatRoutine: Boolean,
        refreshDelay: Long
    ) {
        fetcherManager.execute(
            currentContext = currentContext,
            routine = routine,
            repeatRoutine = repeatRoutine,
            refreshDelay = delay
        ) 
    }
}

// an inherit class example
class ExampleClass : AbstractClass() {

    fun example() {
        execRefreshingRoutine(
            currentContext = this::class.java,
            routine = {
                // your routine
            }
        )
    }

    fun example1() {
        suspendRefresher()
    }

}
```


The other apis will be gradually released

## Authors

- [@N7ghtm4r3](https://www.github.com/N7ghtm4r3)

## Support

If you need help using the library or encounter any problems or bugs, please contact us via the following links:

- Support via <a href="mailto:infotecknobitcompany@gmail.com">email</a>
- Support via <a href="https://github.com/N7ghtm4r3/Equinox/issues/new">GitHub</a>

Thank you for your help!

## Badges

[![](https://img.shields.io/badge/Google_Play-414141?style=for-the-badge&logo=google-play&logoColor=white)](https://play.google.com/store/apps/developer?id=Tecknobit)
[![Twitter](https://img.shields.io/badge/Twitter-1DA1F2?style=for-the-badge&logo=twitter&logoColor=white)](https://twitter.com/tecknobit)

[![](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)](https://spring.io/projects/spring-boot)
[![](https://img.shields.io/badge/Jetpack%20Compose-4285F4.svg?style=for-the-badge&logo=Jetpack-Compose&logoColor=white)](https://www.jetbrains.com/lp/compose-multiplatform/)

[![](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/)
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
