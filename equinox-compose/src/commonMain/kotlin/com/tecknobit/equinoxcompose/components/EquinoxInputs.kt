package com.tecknobit.equinoxcompose.components

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/**
 * Default `onValueChange` to use in the `EquinoxInputs` components
 */
private val defaultOnValueChange: (
    ((String) -> Boolean)?,
    MutableState<Boolean>,
    MutableState<String>,
    Boolean,
    Boolean,
    String,
) -> Unit = { validator, isError, value, mustBeInLowerCase, allowsBlankSpaces, it ->
    if (validator != null)
        isError.value = value.value.isNotEmpty() && !validator.invoke(it)
    val processedValue = it
        .let {
            if (mustBeInLowerCase)
                it.lowercase()
            else
                it
        }
        .let {
            if (allowsBlankSpaces)
                it
            else
                it.replace(" ", "")
        }
    value.value = processedValue
}

/**
 * Custom implementation of a [TextField] component
 *
 * @param modifier The modifier of the text field
 * @param textFieldStyle The style to apply to the [TextField]
 * @param textFieldColors The colors to use for the [TextField]
 * @param width The width of the text field
 * @param value The action to execute when the alert dialog has been dismissed
 * @param mustBeInLowerCase Whether the input must be in lower case format
 * @param allowsBlankSpaces Whether the input can contain blank spaces or not
 * @param maxLines The max number of lines supported, different from one line the text field is considered as text area,
 * otherwise simple text field
 * @param validator The function to invoke to validate the input
 * @param isError Whether the text field is in an error state
 * @param onValueChange the callback that is triggered when the input service updates the text. An
 * updated text comes as a parameter of the callback
 * @param label The label displayed in the text field
 * @param labelStyle The style to apply to the label
 * @param placeholder The placeholder displayed in the text field
 * @param placeholderStyle The style to apply to the placeholder
 * @param errorText The error text to display if [isError] is true
 * @param errorTextStyle The style to apply to the error text
 * @param keyboardOptions software keyboard options that contains configuration
 * @param keyboardActions when the input service emits an IME action, the corresponding callback
 * is called. Note that this IME action may be different from what you specified in
 * [KeyboardOptions.imeAction]
 * @param enabled controls the enabled state of this text field. When `false`, this component will
 *   not respond to user input, and it will appear visually disabled and disabled to accessibility
 *   services.
 * @param readOnly controls the editable state of the text field. When `true`, the text field cannot
 *   be modified. However, a user can focus it and copy text from it. Read-only text fields are
 *   usually used to display pre-filled forms that a user cannot edit.
 * @param label the optional label to be displayed inside the text field container. The default text
 *   style for internal [Text] is [Typography.bodySmall] when the text field is in focus and
 *   [Typography.bodyLarge] when the text field is not in focus
 * @param placeholder the optional placeholder to be displayed when the text field is in focus and
 *   the input text is empty. The default text style for internal [Text] is [Typography.bodyLarge]
 * @param leadingIcon the optional leading icon to be displayed at the beginning of the text field
 *   container
 * @param trailingIcon the optional trailing icon to be displayed at the end of the text field
 *   container
 * @param prefix the optional prefix to be displayed before the input text in the text field
 * @param suffix the optional suffix to be displayed after the input text in the text field
 * @param supportingText the optional supporting text to be displayed below the text field
 * @param isError indicates if the text field's current value is in error. If set to true, the
 *   label, bottom indicator and trailing icon by default will be displayed in error color
 * @param visualTransformation transforms the visual representation of the input [value] For
 *   example, you can use
 *   [PasswordVisualTransformation][androidx.compose.ui.text.input.PasswordVisualTransformation] to
 *   create a password text field. By default, no visual transformation is applied.
 * @param keyboardOptions software keyboard options that contains configuration such as
 *   [KeyboardType] and [ImeAction].
 * @param keyboardActions when the input service emits an IME action, the corresponding callback is
 *   called. Note that this IME action may be different from what you specified in
 *   [KeyboardOptions.imeAction].
 * @param singleLine when `true`, this text field becomes a single horizontally scrolling text field
 *   instead of wrapping onto multiple lines. The keyboard will be informed to not show the return
 *   key as the [ImeAction]. Note that [maxLines] parameter will be ignored as the maxLines
 *   attribute will be automatically set to 1.
 * @param maxLines the maximum height in terms of maximum number of visible lines. It is required
 *   that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param minLines the minimum height in terms of minimum number of visible lines. It is required
 *   that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param interactionSource an optional hoisted [MutableInteractionSource] for observing and
 *   emitting [Interaction]s for this text field. You can use this to change the text field's
 *   appearance or preview the text field in different states. Note that if `null` is provided,
 *   interactions will still happen internally.
 * @param shape defines the shape of this text field's container
 */
@Composable
fun EquinoxTextField(
    modifier: Modifier = Modifier,
    textFieldStyle: TextStyle = LocalTextStyle.current,
    textFieldColors: TextFieldColors = TextFieldDefaults.colors(),
    width: Dp = 280.dp,
    value: MutableState<String>,
    mustBeInLowerCase: Boolean = false,
    allowsBlankSpaces: Boolean = true,
    maxLines: Int = 1,
    validator: ((String) -> Boolean)? = null,
    isError: MutableState<Boolean> = remember { mutableStateOf(false) },
    onValueChange: (String) -> Unit = {
        defaultOnValueChange(validator, isError, value, mustBeInLowerCase, allowsBlankSpaces, it)
    },
    label: StringResource? = null,
    labelStyle: TextStyle = LocalTextStyle.current,
    placeholder: StringResource? = null,
    placeholderStyle: TextStyle = LocalTextStyle.current,
    errorText: StringResource? = null,
    errorTextStyle: TextStyle = LocalTextStyle.current,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = false,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource? = null,
    shape: Shape = TextFieldDefaults.shape,
) {
    EquinoxTextField(
        modifier = modifier,
        textFieldStyle = textFieldStyle,
        textFieldColors = textFieldColors,
        width = width,
        value = value,
        mustBeInLowerCase = mustBeInLowerCase,
        maxLines = maxLines,
        validator = validator,
        isError = isError,
        onValueChange = onValueChange,
        label = if (label != null)
            stringResource(label)
        else
            null,
        labelStyle = labelStyle,
        placeholder = if (placeholder != null)
            stringResource(placeholder)
        else
            null,
        placeholderStyle = placeholderStyle,
        errorText = if (errorText != null)
            stringResource(errorText)
        else
            null,
        errorTextStyle = errorTextStyle,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        allowsBlankSpaces = allowsBlankSpaces,
        enabled = enabled,
        readOnly = readOnly,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        prefix = prefix,
        suffix = suffix,
        supportingText = supportingText,
        visualTransformation = visualTransformation,
        singleLine = singleLine,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape
    )
}

/**
 * Custom implementation of a [TextField] component
 *
 * @param modifier The modifier of the text field
 * @param textFieldStyle The style to apply to the [TextField]
 * @param textFieldColors The colors to use for the [TextField]
 * @param width The width of the text field
 * @param value The action to execute when the alert dialog has been dismissed
 * @param mustBeInLowerCase Whether the input must be in lower case format
 * @param allowsBlankSpaces Whether the input can contain blank spaces or not
 * @param maxLines The max number of lines supported, different from one line the text field is considered as text area,
 * otherwise simple text field
 * @param validator The function to invoke to validate the input
 * @param isError Whether the text field is in an error state
 * @param onValueChange the callback that is triggered when the input service updates the text. An
 * updated text comes as a parameter of the callback
 * @param label The label displayed in the text field
 * @param labelStyle The style to apply to the label
 * @param placeholder The placeholder displayed in the text field
 * @param placeholderStyle The style to apply to the placeholder
 * @param errorText The error text to display if [isError] is true
 * @param errorTextStyle The style to apply to the error text
 * @param keyboardOptions software keyboard options that contains configuration
 * @param keyboardActions when the input service emits an IME action, the corresponding callback
 * is called. Note that this IME action may be different from what you specified in
 * [KeyboardOptions.imeAction]
 * @param enabled controls the enabled state of this text field. When `false`, this component will
 *   not respond to user input, and it will appear visually disabled and disabled to accessibility
 *   services.
 * @param readOnly controls the editable state of the text field. When `true`, the text field cannot
 *   be modified. However, a user can focus it and copy text from it. Read-only text fields are
 *   usually used to display pre-filled forms that a user cannot edit.
 * @param label the optional label to be displayed inside the text field container. The default text
 *   style for internal [Text] is [Typography.bodySmall] when the text field is in focus and
 *   [Typography.bodyLarge] when the text field is not in focus
 * @param placeholder the optional placeholder to be displayed when the text field is in focus and
 *   the input text is empty. The default text style for internal [Text] is [Typography.bodyLarge]
 * @param leadingIcon the optional leading icon to be displayed at the beginning of the text field
 *   container
 * @param trailingIcon the optional trailing icon to be displayed at the end of the text field
 *   container
 * @param prefix the optional prefix to be displayed before the input text in the text field
 * @param suffix the optional suffix to be displayed after the input text in the text field
 * @param supportingText the optional supporting text to be displayed below the text field
 * @param isError indicates if the text field's current value is in error. If set to true, the
 *   label, bottom indicator and trailing icon by default will be displayed in error color
 * @param visualTransformation transforms the visual representation of the input [value] For
 *   example, you can use
 *   [PasswordVisualTransformation][androidx.compose.ui.text.input.PasswordVisualTransformation] to
 *   create a password text field. By default, no visual transformation is applied.
 * @param keyboardOptions software keyboard options that contains configuration such as
 *   [KeyboardType] and [ImeAction].
 * @param keyboardActions when the input service emits an IME action, the corresponding callback is
 *   called. Note that this IME action may be different from what you specified in
 *   [KeyboardOptions.imeAction].
 * @param singleLine when `true`, this text field becomes a single horizontally scrolling text field
 *   instead of wrapping onto multiple lines. The keyboard will be informed to not show the return
 *   key as the [ImeAction]. Note that [maxLines] parameter will be ignored as the maxLines
 *   attribute will be automatically set to 1.
 * @param maxLines the maximum height in terms of maximum number of visible lines. It is required
 *   that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param minLines the minimum height in terms of minimum number of visible lines. It is required
 *   that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param interactionSource an optional hoisted [MutableInteractionSource] for observing and
 *   emitting [Interaction]s for this text field. You can use this to change the text field's
 *   appearance or preview the text field in different states. Note that if `null` is provided,
 *   interactions will still happen internally.
 * @param shape defines the shape of this text field's container
 */
@Composable
fun EquinoxTextField(
    modifier: Modifier = Modifier,
    textFieldStyle: TextStyle = LocalTextStyle.current,
    textFieldColors: TextFieldColors = TextFieldDefaults.colors(),
    width: Dp = 280.dp,
    value: MutableState<String>,
    mustBeInLowerCase: Boolean = false,
    allowsBlankSpaces: Boolean = true,
    maxLines: Int = 1,
    validator: ((String) -> Boolean)? = null,
    isError: MutableState<Boolean> = remember { mutableStateOf(false) },
    onValueChange: (String) -> Unit = {
        defaultOnValueChange(validator, isError, value, mustBeInLowerCase, allowsBlankSpaces, it)
    },
    label: String? = null,
    labelStyle: TextStyle = LocalTextStyle.current,
    placeholder: String? = null,
    placeholderStyle: TextStyle = LocalTextStyle.current,
    errorText: String? = null,
    errorTextStyle: TextStyle = LocalTextStyle.current,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = false,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource? = null,
    shape: Shape = TextFieldDefaults.shape,
) {
    TextField(
        modifier = modifier
            .width(width),
        textStyle = textFieldStyle,
        colors = textFieldColors,
        value = value.value,
        onValueChange = onValueChange,
        label = if (label != null) {
            {
                Text(
                    text = label,
                    style = labelStyle
                )
            }
        } else
            null,
        placeholder = if (placeholder != null) {
            {
                Text(
                    text = placeholder,
                    style = placeholderStyle
                )
            }
        } else
            null,
        maxLines = maxLines,
        keyboardOptions = keyboardOptions,
        isError = isError.value,
        supportingText = if (isError.value && errorText != null) {
            {
                Text(
                    text = errorText,
                    style = errorTextStyle
                )
            }
        } else
            supportingText,
        keyboardActions = keyboardActions,
        enabled = enabled,
        readOnly = readOnly,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        prefix = prefix,
        suffix = suffix,
        visualTransformation = visualTransformation,
        singleLine = singleLine,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape,
    )
}

/**
 * Custom implementation of a [OutlinedTextField]
 *
 * @param modifier The modifier of the text field
 * @param outlinedTextFieldStyle The style to apply to the [OutlinedTextField]
 * @param outlinedTextFieldColors The colors to use for the [OutlinedTextField]
 * @param width The width of the text field
 * @param value The action to execute when the alert dialog has been dismissed
 * @param mustBeInLowerCase Whether the input must be in lower case format
 * @param allowsBlankSpaces Whether the input can contain blank spaces or not
 * @param maxLines The max number of lines supported, different from one line the text field is considered as text area,
 * otherwise simple text field
 * @param validator The function to invoke to validate the input
 * @param isError Whether the text field is in an error state
 * @param onValueChange the callback that is triggered when the input service updates the text. An
 * updated text comes as a parameter of the callback
 * @param label The label displayed in the text field
 * @param labelStyle The style to apply to the label
 * @param placeholder The placeholder displayed in the text field
 * @param placeholderStyle The style to apply to the placeholder
 * @param errorText The error text to display if [isError] is true
 * @param errorTextStyle The style to apply to the error text
 * @param trailingIcon The optional trailing icon to be displayed at the end of the text field container
 * @param visualTransformation Transforms the visual representation of the input [value]
 * @param keyboardOptions software keyboard options that contains configuration
 * @param keyboardActions when the input service emits an IME action, the corresponding callback
 * is called. Note that this IME action may be different from what you specified in
 * [KeyboardOptions.imeAction]
 * @param enabled controls the enabled state of this text field. When `false`, this component will
 *   not respond to user input, and it will appear visually disabled and disabled to accessibility
 *   services.
 * @param readOnly controls the editable state of the text field. When `true`, the text field cannot
 *   be modified. However, a user can focus it and copy text from it. Read-only text fields are
 *   usually used to display pre-filled forms that a user cannot edit.
 * @param label the optional label to be displayed inside the text field container. The default text
 *   style for internal [Text] is [Typography.bodySmall] when the text field is in focus and
 *   [Typography.bodyLarge] when the text field is not in focus
 * @param placeholder the optional placeholder to be displayed when the text field is in focus and
 *   the input text is empty. The default text style for internal [Text] is [Typography.bodyLarge]
 * @param leadingIcon the optional leading icon to be displayed at the beginning of the text field
 *   container
 * @param trailingIcon the optional trailing icon to be displayed at the end of the text field
 *   container
 * @param prefix the optional prefix to be displayed before the input text in the text field
 * @param suffix the optional suffix to be displayed after the input text in the text field
 * @param supportingText the optional supporting text to be displayed below the text field
 * @param isError indicates if the text field's current value is in error. If set to true, the
 *   label, bottom indicator and trailing icon by default will be displayed in error color
 * @param visualTransformation transforms the visual representation of the input [value] For
 *   example, you can use
 *   [PasswordVisualTransformation][androidx.compose.ui.text.input.PasswordVisualTransformation] to
 *   create a password text field. By default, no visual transformation is applied.
 * @param keyboardOptions software keyboard options that contains configuration such as
 *   [KeyboardType] and [ImeAction]
 * @param keyboardActions when the input service emits an IME action, the corresponding callback is
 *   called. Note that this IME action may be different from what you specified in
 *   [KeyboardOptions.imeAction]
 * @param maxLines the maximum height in terms of maximum number of visible lines. It is required
 *   that 1 <= [minLines] <= [maxLines].
 * @param minLines the minimum height in terms of minimum number of visible lines. It is required
 *   that 1 <= [minLines] <= [maxLines].
 * @param interactionSource an optional hoisted [MutableInteractionSource] for observing and
 *   emitting [Interaction]s for this text field. You can use this to change the text field's
 *   appearance or preview the text field in different states. Note that if `null` is provided,
 *   interactions will still happen internally.
 * @param shape defines the shape of this text field's border
 */
@Composable
fun EquinoxOutlinedTextField(
    modifier: Modifier = Modifier,
    outlinedTextFieldStyle: TextStyle = LocalTextStyle.current,
    outlinedTextFieldColors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    width: Dp = 300.dp,
    value: MutableState<String>,
    mustBeInLowerCase: Boolean = false,
    allowsBlankSpaces: Boolean = true,
    maxLines: Int = 1,
    validator: ((String) -> Boolean)? = null,
    isError: MutableState<Boolean> = remember { mutableStateOf(false) },
    onValueChange: (String) -> Unit = {
        defaultOnValueChange(validator, isError, value, mustBeInLowerCase, allowsBlankSpaces, it)
    },
    label: StringResource? = null,
    labelStyle: TextStyle = LocalTextStyle.current,
    placeholder: StringResource? = null,
    placeholderStyle: TextStyle = LocalTextStyle.current,
    errorText: StringResource? = null,
    errorTextStyle: TextStyle = LocalTextStyle.current,
    trailingIcon: @Composable (() -> Unit)? = {
        IconButton(
            onClick = { value.value = "" }
        ) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = null
            )
        }
    },
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource? = null,
    shape: Shape = OutlinedTextFieldDefaults.shape,
) {
    EquinoxOutlinedTextField(
        modifier = modifier,
        outlinedTextFieldStyle = outlinedTextFieldStyle,
        outlinedTextFieldColors = outlinedTextFieldColors,
        width = width,
        value = value,
        mustBeInLowerCase = mustBeInLowerCase,
        maxLines = maxLines,
        validator = validator,
        isError = isError,
        onValueChange = onValueChange,
        label = if (label != null)
            stringResource(label)
        else
            null,
        labelStyle = labelStyle,
        placeholder = if (placeholder != null)
            stringResource(placeholder)
        else
            null,
        placeholderStyle = placeholderStyle,
        errorText = if (errorText != null)
            stringResource(errorText)
        else
            null,
        errorTextStyle = errorTextStyle,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        allowsBlankSpaces = allowsBlankSpaces,
        enabled = enabled,
        readOnly = readOnly,
        leadingIcon = leadingIcon,
        prefix = prefix,
        suffix = suffix,
        supportingText = supportingText,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape
    )
}

/**
 * Custom implementation of a [OutlinedTextField]
 *
 * @param modifier The modifier of the text field
 * @param outlinedTextFieldStyle The style to apply to the [OutlinedTextField]
 * @param outlinedTextFieldColors The colors to use for the [OutlinedTextField]
 * @param width The width of the text field
 * @param value The action to execute when the alert dialog has been dismissed
 * @param mustBeInLowerCase Whether the input must be in lower case format
 * @param allowsBlankSpaces Whether the input can contain blank spaces or not
 * @param maxLines The max number of lines supported, different from one line the text field is considered as text area,
 * otherwise simple text field
 * @param validator The function to invoke to validate the input
 * @param isError Whether the text field is in an error state
 * @param onValueChange the callback that is triggered when the input service updates the text. An
 * updated text comes as a parameter of the callback
 * @param label The label displayed in the text field
 * @param labelStyle The style to apply to the label
 * @param placeholder The placeholder displayed in the text field
 * @param placeholderStyle The style to apply to the placeholder
 * @param errorText The error text to display if [isError] is true
 * @param errorTextStyle The style to apply to the error text
 * @param trailingIcon The optional trailing icon to be displayed at the end of the text field container
 * @param visualTransformation Transforms the visual representation of the input [value]
 * @param keyboardOptions software keyboard options that contains configuration
 * @param keyboardActions when the input service emits an IME action, the corresponding callback
 * is called. Note that this IME action may be different from what you specified in
 * [KeyboardOptions.imeAction]
 * @param enabled controls the enabled state of this text field. When `false`, this component will
 *   not respond to user input, and it will appear visually disabled and disabled to accessibility
 *   services.
 * @param readOnly controls the editable state of the text field. When `true`, the text field cannot
 *   be modified. However, a user can focus it and copy text from it. Read-only text fields are
 *   usually used to display pre-filled forms that a user cannot edit.
 * @param label the optional label to be displayed inside the text field container. The default text
 *   style for internal [Text] is [Typography.bodySmall] when the text field is in focus and
 *   [Typography.bodyLarge] when the text field is not in focus
 * @param placeholder the optional placeholder to be displayed when the text field is in focus and
 *   the input text is empty. The default text style for internal [Text] is [Typography.bodyLarge]
 * @param leadingIcon the optional leading icon to be displayed at the beginning of the text field
 *   container
 * @param trailingIcon the optional trailing icon to be displayed at the end of the text field
 *   container
 * @param prefix the optional prefix to be displayed before the input text in the text field
 * @param suffix the optional suffix to be displayed after the input text in the text field
 * @param supportingText the optional supporting text to be displayed below the text field
 * @param isError indicates if the text field's current value is in error. If set to true, the
 *   label, bottom indicator and trailing icon by default will be displayed in error color
 * @param visualTransformation transforms the visual representation of the input [value] For
 *   example, you can use
 *   [PasswordVisualTransformation][androidx.compose.ui.text.input.PasswordVisualTransformation] to
 *   create a password text field. By default, no visual transformation is applied.
 * @param keyboardOptions software keyboard options that contains configuration such as
 *   [KeyboardType] and [ImeAction]
 * @param keyboardActions when the input service emits an IME action, the corresponding callback is
 *   called. Note that this IME action may be different from what you specified in
 *   [KeyboardOptions.imeAction]
 * @param maxLines the maximum height in terms of maximum number of visible lines. It is required
 *   that 1 <= [minLines] <= [maxLines].
 * @param minLines the minimum height in terms of minimum number of visible lines. It is required
 *   that 1 <= [minLines] <= [maxLines].
 * @param interactionSource an optional hoisted [MutableInteractionSource] for observing and
 *   emitting [Interaction]s for this text field. You can use this to change the text field's
 *   appearance or preview the text field in different states. Note that if `null` is provided,
 *   interactions will still happen internally.
 * @param shape defines the shape of this text field's border
 */
@Composable
fun EquinoxOutlinedTextField(
    modifier: Modifier = Modifier,
    outlinedTextFieldStyle: TextStyle = LocalTextStyle.current,
    outlinedTextFieldColors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    width: Dp = 300.dp,
    value: MutableState<String>,
    mustBeInLowerCase: Boolean = false,
    allowsBlankSpaces: Boolean = true,
    maxLines: Int = 1,
    validator: ((String) -> Boolean)? = null,
    isError: MutableState<Boolean> = remember { mutableStateOf(false) },
    onValueChange: (String) -> Unit = {
        defaultOnValueChange(validator, isError, value, mustBeInLowerCase, allowsBlankSpaces, it)
    },
    label: String? = null,
    labelStyle: TextStyle = LocalTextStyle.current,
    placeholder: String? = null,
    placeholderStyle: TextStyle = LocalTextStyle.current,
    errorText: String? = null,
    errorTextStyle: TextStyle = LocalTextStyle.current,
    trailingIcon: @Composable (() -> Unit)? = {
        IconButton(
            onClick = { value.value = "" }
        ) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = null
            )
        }
    },
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource? = null,
    shape: Shape = OutlinedTextFieldDefaults.shape,
) {
    OutlinedTextField(
        modifier = modifier
            .width(width),
        textStyle = outlinedTextFieldStyle,
        colors = outlinedTextFieldColors,
        value = value.value,
        onValueChange = onValueChange,
        label = if (label != null) {
            {
                Text(
                    text = label,
                    style = labelStyle
                )
            }
        } else
            null,
        placeholder = if (placeholder != null) {
            {
                Text(
                    text = placeholder,
                    style = placeholderStyle
                )
            }
        } else
            null,
        trailingIcon = trailingIcon,
        singleLine = maxLines == 1,
        maxLines = maxLines,
        isError = isError.value,
        supportingText = if (isError.value && errorText != null) {
            {
                Text(
                    text = errorText,
                    style = errorTextStyle
                )
            }
        } else
            supportingText,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        enabled = enabled,
        readOnly = readOnly,
        leadingIcon = leadingIcon,
        prefix = prefix,
        suffix = suffix,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape,
    )
}