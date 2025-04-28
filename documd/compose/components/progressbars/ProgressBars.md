# ProgressBars

Components used to display progress values on bars

## Usage

### HorizontalProgressBar

```kotlin
class TestScreen : EquinoxScreen<EquinoxViewModel>() {

    @Composable
    override fun ArrangeScreenContent() {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Your total value
            val total = 10

            // Arbitrary size to indicate the completion (width or height)
            val completionSize = 150.dp

            // any container
            Card(
                modifier = Modifier
                    .width(150.dp)
            ) {
                var progress = remember { 0 }
                HorizontalProgressBar(
                    containerModifier = Modifier
                        .padding(
                            all = 10.dp
                        ),
                    completionWidth = completionSize,
                    currentProgress = {
                        // your logic to retrieve the progress value
                        delay(1000)
                        if (progress < 10)
                            ++progress
                        else
                            progress++
                    },
                    total = total,
                    onCompletion = {
                        // a not mandatory completion callback
                        println("Completed!")
                    }
                )
            }
        }
    }

}
```

### Mobile

[horizontal_prograssbar-android](https://github.com/user-attachments/assets/51d2ab64-e3fb-434a-bdd6-d0cdea3218a9)

### Desktop & Web

[horizontal_prograssbar-desktop](https://github.com/user-attachments/assets/157f092c-b30f-4c49-9962-40f067599260)

### VerticalProgressBar

```kotlin
class TestScreen : EquinoxScreen<EquinoxViewModel>() {

    @Composable
    override fun ArrangeScreenContent() {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Your total value
            val total = 10

            // Arbitrary size to indicate the completion (width or height)
            val completionSize = 150.dp

            // any container
            Card(
                modifier = Modifier
                    .width(150.dp)
            ) {
                var progress = remember { 0 }
                VerticalProgressBar(
                    containerModifier = Modifier
                        .padding(
                            all = 10.dp
                        ),
                    completionHeight = completionSize,
                    currentProgress = {
                        // your logic to retrieve the progress value
                        delay(1000)
                        if (progress < 10)
                            ++progress
                        else
                            progress++
                    },
                    total = total,
                    onCompletion = {
                        // a not mandatory completion callback
                        println("Completed!")
                    }
                )
            }
        }
    }

}
```

### Mobile

[vertical_prograssbar-android](https://github.com/user-attachments/assets/fe124f60-85d5-453f-836c-961cc600ac2a)

### Desktop & Web

[vertical_prograssbar-desktop](https://github.com/user-attachments/assets/710bcdbc-8b18-4692-84a1-562f90c80158)

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

