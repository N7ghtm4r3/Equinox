# Stepper

Create a dynamic interaction with the user dividing for example a long procedure such item
creation, customization, etc... in different specific steps where the user can interact

## Usage

### Create the steps

It is suggested to create the steps-array inside the `remember` method to avoid the recreation during the recompositions

```kotlin
@Composable
fun AnyComponent() {
    val steps = remember {
        arrayOf(
            Step(
                stepIcon = Icons.Default.Check, // representative icon
                title = Res.string.check, // title of the step
                content = {
                    Check() // content of the step
                }
            ),
            Step(
                stepIcon = Icons.Default.Commit,
                title = Res.string.commit,
                content = {
                    Commit()
                }
            ),
            Step(
                stepIcon = Icons.Default.Upload,
                title = Res.string.push,
                content = {
                    Push()
                },
                dismissAction = {
                    pushed.value = !pushed.value
                }
            )
        )
    }
}
```

### Customize the content of the step

```kotlin
@Composable
@NonRestartableComposable
//not mandatory, but it is better for readability
@StepContent(
    number = 0,
    enabledWhen = "This step is enabled when etc..."
)
private fun Check() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked.value,
            onCheckedChange = { checked.value = it }
        )
        Text(
            text = "All code is okay"
        )
    }
}
```

### Create and use the Stepper

```kotlin
@Composable
fun AnyComponent() {
    // steps created
    Stepper(
        headerSection =  // custom header section
        startStepShape =  // custom start shape
        middleStepShape = // custom middle shape
        finalStepShape = // custom final shape
        confirmColor = // custom confirmation color
        errorColor = // custom error color
        stepBackgroundColor = // custom step background color
        expandsStepIcon = // custom icon used to expand the steps
        steps = steps
    )
}
```

## Appearance

### Mobile

[stepper-mobile](https://github.com/user-attachments/assets/27a17172-fba6-4fbd-99b3-4084fd42af55)

### Desktop & Web

[stepper-desktop](https://github.com/user-attachments/assets/f6b62fef-0ba5-46b3-927e-50373d08893e)

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

