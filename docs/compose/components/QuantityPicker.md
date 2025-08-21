This component allows to pick a numerical quantity value

## Usage

```kotlin
class TestScreen : EquinoxNoModelScreen() {

    @Composable
    override fun ArrangeScreenContent() {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val state = rememberQuantityPickerState(
                initialQuantity = 0,
                minQuantity = 0, // minimum threshold quantity allowed,
                maxQuantity = 10, // maximum threshold quantity allowed,
                longPressQuantity = 2,
                /* quantity to decrement or increment when the user long press
                           (or double-clicked) the quantity buttons */
            )
            QuantityPicker(
                state = state,
                informativeText = "Informative text", // not mandatory
                decrementButtonColors = QuantityPickerDefaults.colors(
                    ...
            ), // customize the decrement button colors
            incrementButtonColors = QuantityPickerDefaults.colors(
                ...
            ) // customize the increment button colors
            )
        }
    }

}
```

## Customization

Check out the table below to apply your customizations to the component:

| Property                 | Description                                                             |
|--------------------------|-------------------------------------------------------------------------|
| `modifier`               | The modifier to apply to the component                                  |
| `informativeText`        | The informative text which describes what type of quantity is picked    |
| `informativeTextStyle`   | The style to apply to the `informativeText`                             |
| `indicatorsSize`         | The size of the indicators buttons must have                            |
| `indicatorsShape`        | The shape of the indicators buttons must have                           |
| `onQuantityPicked`       | Callback to invoke when a quantity has been picked                      |
| `decrementButtonColors`  | The colors to apply to the decrement button                             |
| `decrementIcon`          | The icon of the decrement button                                        |
| `quantityIndicatorStyle` | The style to apply to the indicator text of the current quantity picked |
| `incrementButtonColors`  | The colors to apply to the increment button                             |
| `incrementIcon`          | The icon of the increment button                                        |
| `enabled`                | Whether the picker is enabled to pick quantities                        |

## Appearance

### Mobile

<video class="shadow mobile-appearance" controls>
  <source src="../assets/videos/quantitypicker/quantitypicker-mobile.webm" type="video/webm">
  Cannot play the video
</video>

### Desktop & Web

<video class="shadow" controls>
  <source src="../assets/videos/quantitypicker/quantitypicker-desktop.mp4" type="video/mp4">
  Cannot play the video
</video>