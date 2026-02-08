# Debounced Inputs

These components allow to implement a debounce routine handled when the user stop to typing in the inputs fields

## DebouncedTextField

### Usage

```kotlin
@Composable
fun App() {
    DebouncedTextField(
        value = state,
        placeholder = "your_placeholder",
        debounceDelay = ,// custom delay default 500 ms
        debounce = {
            // your debounce logic here
        }
    )
}
```

### Customization

Check out the table below to apply your customizations to the component:

| Property               | Description                                                                                                                                                                                                   |
|------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `modifier`             | The modifier of the text field                                                                                                                                                                                |
| `textFieldStyle`       | The style to apply to the `TextField`                                                                                                                                                                         |
| `textFieldColors`      | The colors to use for the `TextField`                                                                                                                                                                         |
| `width`                | The width of the text field                                                                                                                                                                                   |
| `mustBeInLowerCase`    | Whether the input must be in lower case format                                                                                                                                                                |
| `allowsBlankSpaces`    | Whether the input can contain blank spaces or not                                                                                                                                                             |
| `maxLines`             | The max number of lines supported. If different from one, the text field is considered as a text area; otherwise, a simple text field                                                                         |
| `validator`            | The function to invoke to validate the input                                                                                                                                                                  |
| `isError`              | Whether the text field is in an error state                                                                                                                                                                   |
| `debounceDelay`        | The delay before invoking the `debounce`                                                                                                                                                                      |
| `onValueChange`        | Callback triggered when the input service updates the text. An updated text comes as a parameter of the callback                                                                                              |
| `label`                | The label displayed in the text field. The default text style for internal `Text` is `Typography.bodySmall` when focused, and `Typography.bodyLarge` when not focused                                         |
| `labelStyle`           | The style to apply to the label                                                                                                                                                                               |
| `placeholder`          | The placeholder displayed in the text field. Default text style for internal `Text` is `Typography.bodyLarge`                                                                                                 |
| `placeholderStyle`     | The style to apply to the placeholder                                                                                                                                                                         |
| `errorText`            | The error text to display if `isError` is true                                                                                                                                                                |
| `errorTextStyle`       | The style to apply to the error text                                                                                                                                                                          |
| `keyboardOptions`      | Software keyboard options that contain configuration (e.g., `KeyboardType`, `ImeAction`)                                                                                                                      |
| `keyboardActions`      | When the input service emits an IME action, the corresponding callback is called. This IME action may differ from the one specified in `KeyboardOptions.imeAction`                                            |
| `enabled`              | Controls the enabled state of this text field. When `false`, the component will not respond to input, will appear visually disabled, and will be inaccessible to accessibility services                       |
| `readOnly`             | Controls the editable state of the text field. When `true`, the text field cannot be modified, but it can be focused and its text copied. Typically used for pre-filled forms that cannot be edited           |
| `leadingIcon`          | The optional leading icon to be displayed at the beginning of the text field container                                                                                                                        |
| `trailingIcon`         | The optional trailing icon to be displayed at the end of the text field container                                                                                                                             |
| `prefix`               | The optional prefix to be displayed before the input text in the text field                                                                                                                                   |
| `suffix`               | The optional suffix to be displayed after the input text in the text field                                                                                                                                    |
| `supportingText`       | The optional supporting text to be displayed below the text field                                                                                                                                             |
| `visualTransformation` | Transforms the visual representation of the input `value`. For example, you can use `PasswordVisualTransformation` to create a password text field. By default, no transformation is applied                  |
| `singleLine`           | When `true`, the text field becomes a single horizontally scrolling text field instead of wrapping multiple lines. Keyboard return key will not show as `ImeAction`. Ignores `maxLines` (set to 1 internally) |
| `interactionSource`    | An optional hoisted `MutableInteractionSource` for observing and emitting `Interaction`s. Allows changing appearance or previewing different states. Defaults internally if `null` is provided                |
| `shape`                | Defines the shape of this text field's container                                                                                                                                                              |

## DebouncedOutlinedTextField

### Usage

```kotlin
@Composable
fun App() {
    DebouncedOutlinedTextField(
        value = state,
        placeholder = "your_placeholder",
        debounceDelay = ,// custom delay default 500 ms
        debounce = {
            // your debounce logic here
        }
    )
}
```

### Customization

Check out the table below to apply your customizations to the component:

| Property                  | Description                                                                                                                                                                                    |
|---------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `modifier`                | The modifier of the text field                                                                                                                                                                 |
| `outlinedTextFieldStyle`  | The style to apply to the `OutlinedTextField`                                                                                                                                                  |
| `outlinedTextFieldColors` | The colors to use for the `OutlinedTextField`                                                                                                                                                  |
| `width`                   | The width of the text field                                                                                                                                                                    |
| `mustBeInLowerCase`       | Whether the input must be in lower case format                                                                                                                                                 |
| `allowsBlankSpaces`       | Whether the input can contain blank spaces or not                                                                                                                                              |
| `maxLines`                | The maximum number of lines supported. If > 1, the text field is considered a text area; otherwise a simple one-line text field. It is required that `1 <= minLines <= maxLines`               |
| `minLines`                | The minimum number of visible lines. It is required that `1 <= minLines <= maxLines`                                                                                                           |
| `validator`               | The function to invoke to validate the input                                                                                                                                                   |
| `isError`                 | Whether the text field is in an error state. If true, the label, bottom indicator, and trailing icon will be displayed in error color                                                          |
| `debounceDelay`           | The delay before invoking the `debounce`                                                                                                                                                       |
| `onValueChange`           | Callback triggered when the input service updates the text. An updated text comes as a parameter of the callback                                                                               |
| `label`                   | The optional label displayed inside the text field container. Default `Text` style is `Typography.bodySmall` when focused, and `Typography.bodyLarge` when not focused                         |
| `labelStyle`              | The style to apply to the label                                                                                                                                                                |
| `placeholder`             | The optional placeholder displayed when the field is in focus and input text is empty. Default `Text` style is `Typography.bodyLarge`                                                          |
| `placeholderStyle`        | The style to apply to the placeholder                                                                                                                                                          |
| `errorText`               | The error text to display if `isError` is true                                                                                                                                                 |
| `errorTextStyle`          | The style to apply to the error text                                                                                                                                                           |
| `leadingIcon`             | The optional leading icon displayed at the beginning of the text field container                                                                                                               |
| `trailingIcon`            | The optional trailing icon displayed at the end of the text field container                                                                                                                    |
| `prefix`                  | The optional prefix to be displayed before the input text                                                                                                                                      |
| `suffix`                  | The optional suffix to be displayed after the input text                                                                                                                                       |
| `supportingText`          | The optional supporting text displayed below the text field                                                                                                                                    |
| `visualTransformation`    | Transforms the visual representation of the input `value`. For example, `PasswordVisualTransformation` can be used to create a password text field. By default, no transformation is applied   |
| `keyboardOptions`         | Software keyboard options that contain configuration such as `KeyboardType` and `ImeAction`                                                                                                    |
| `keyboardActions`         | When the input service emits an IME action, the corresponding callback is called. The IME action may differ from the one specified in `KeyboardOptions.imeAction`                              |
| `enabled`                 | Controls the enabled state of this text field. When `false`, the component will not respond to input, will appear visually disabled, and be inaccessible to accessibility services             |
| `readOnly`                | Controls the editable state of the text field. When `true`, the field cannot be modified, but it can be focused and its text copied. Typically used for pre-filled forms that cannot be edited |
| `interactionSource`       | An optional hoisted `MutableInteractionSource` for observing and emitting `Interaction`s. Allows appearance changes or state previews. Defaults internally if `null`                           |
| `shape`                   | Defines the shape of this text field's border                                                                                                                                                  |
