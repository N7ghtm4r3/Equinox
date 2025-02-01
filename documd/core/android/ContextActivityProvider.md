# ContextActivityProvider

![Static Badge](https://img.shields.io/badge/android-4280511051)

A singleton object that helps to maintain a weak reference to the current activity,
avoiding memory leaks by preventing strong references to an Activity

### Implementation

- Initialize it in your MainActivity.kt class

```kotlin
class MainActivity : ComponentActivity() {

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ...
        // attach the activity
        ContextActivityProvider.setCurrentActivity(this)

        ...
    }

}
```

- Use it in composable methods

```kotlin
@Composable
fun SomeComposable() {
    val activity = ContextActivityProvider.getCurrentActivity()
}
```



## Support

If you need help using the library or encounter any problems or bugs, please contact us via the following links:

- Support via <a href="mailto:infotecknobitcompany@gmail.com">email</a>
- Support via <a href="https://github.com/N7ghtm4r3/Equinox/issues/new">GitHub</a>

Thank you for your help!

## Donations

If you want support project and developer

| Crypto                                                                                              | Address                                          | Network  |
|-----------------------------------------------------------------------------------------------------|--------------------------------------------------|----------|
| ![](https://img.shields.io/badge/Bitcoin-000000?style=for-the-badge&logo=bitcoin&logoColor=white)   | 3H3jyCzcRmnxroHthuXh22GXXSmizin2yp               | Bitcoin  |
| ![](https://img.shields.io/badge/Ethereum-3C3C3D?style=for-the-badge&logo=Ethereum&logoColor=white) | 0x1b45bc41efeb3ed655b078f95086f25fc83345c4       | Ethereum |
| ![](https://img.shields.io/badge/Solana-000?style=for-the-badge&logo=Solana&logoColor=9945FF)       | **AtPjUnxYFHw3a6Si9HinQtyPTqsdbfdKX3dJ1xiDjbrL** | Solana   |

If you want support project and developer
with <a href="https://www.paypal.com/donate/?hosted_button_id=5QMN5UQH7LDT4">PayPal</a>

Copyright © 2025 Tecknobit

