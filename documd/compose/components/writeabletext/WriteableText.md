# WriteableText

This component allows to display a text or writing a new one clicking on it

## Usage

```kotlin
class TestScreen : EquinoxScreen<EquinoxViewModel>() {

    @Composable
    override fun ArrangeScreenContent() {
        Scaffold(
            modifier = Modifier
                // when tap detected on its surface the isWriting state will be set on false
                .disableWritingModeOnTap(),
            topBar = {
                LargeTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = Color.White
                    ),
                    title = {
                        val isWriting = remember { mutableStateOf(false) }
                        val writingText = remember { mutableStateOf("Initial text") }
                        // sample usage
                        WriteableText(
                            isInWritingMode = isWriting,
                            writeableText = writingText,
                            placeholder = "Placeholder"
                        )
                    }
                )
            }
        ) {
            EquinoxTextField(
                modifier = Modifier
                    // when this component gains the focus the isWriting state will be set on false
                    .disableWritingModeOnFocusGain()
                // ...
            )

            // Change writingMode state programmatically
            Button(
                onClick = {
                    // will set on false the current active writingMode state
                    disableWritingMode()
                }
            ) {
                Text(
                    text = "Disable writing mode"
                )
            }
            Button(
                onClick = {
                    // will set on true the current active writingMode state
                    enableWritingMode()
                }
            ) {
                Text(
                    text = "Disable writing mode"
                )
            }
        }
    }

}
```

## Appearance

### Mobile

[writeabletext-android](https://github.com/user-attachments/assets/8f15d61b-e792-41f3-89d8-7b163bce4557)

### Desktop & Web

[writeabletext-desktop](https://github.com/user-attachments/assets/fff5bff0-6dc4-452d-b35f-5ef018e33848)

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

