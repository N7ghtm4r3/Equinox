package com.tecknobit.equinoxcompose.components

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
import androidx.compose.ui.text.TextStyle
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
 * Function to display a custom [TextField]
 *
 * @param modifier: the modifier of the text field
 * @param textFieldStyle: the style to apply to the [TextField]
 * @param textFieldColors: the colors to use for the [TextField]
 * @param width: the width of the text field
 * @param value: the action to execute when the alert dialog has been dismissed
 * @param mustBeInLowerCase: whether the input must be in lower case format
 * @param allowsBlankSpaces: whether the input can contain blank spaces or not
 * @param maxLines: the max number of lines supported, different from one line the text field is considered as text area,
 * otherwise simple text field
 * @param validator: the function to invoke to validate the input
 * @param isError: whether the text field is in an error state
 * @param onValueChange the callback that is triggered when the input service updates the text. An
 * updated text comes as a parameter of the callback
 * @param label: the label displayed in the text field
 * @param labelStyle: the style to apply to the label
 * @param placeholder: the placeholder displayed in the text field
 * @param placeholderStyle: the style to apply to the placeholder
 * @param errorText: the error text to display if [isError] is true
 * @param errorTextStyle: the style to apply to the error text
 * @param keyboardOptions software keyboard options that contains configuration
 * @param keyboardActions when the input service emits an IME action, the corresponding callback
 * is called. Note that this IME action may be different from what you specified in
 * [KeyboardOptions.imeAction]
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
        keyboardActions = keyboardActions
    )
}

/**
 * Function to display a custom [TextField]
 *
 * @param modifier: the modifier of the text field
 * @param textFieldStyle: the style to apply to the [TextField]
 * @param textFieldColors: the colors to use for the [TextField]
 * @param width: the width of the text field
 * @param value: the action to execute when the alert dialog has been dismissed
 * @param mustBeInLowerCase: whether the input must be in lower case format
 * @param allowsBlankSpaces: whether the input can contain blank spaces or not
 * @param maxLines: the max number of lines supported, different from one line the text field is considered as text area,
 * otherwise simple text field
 * @param validator: the function to invoke to validate the input
 * @param isError: whether the text field is in an error state
 * @param onValueChange the callback that is triggered when the input service updates the text. An
 * updated text comes as a parameter of the callback
 * @param label: the label displayed in the text field
 * @param labelStyle: the style to apply to the label
 * @param placeholder: the placeholder displayed in the text field
 * @param placeholderStyle: the style to apply to the placeholder
 * @param errorText: the error text to display if [isError] is true
 * @param errorTextStyle: the style to apply to the error text
 * @param keyboardOptions software keyboard options that contains configuration
 * @param keyboardActions when the input service emits an IME action, the corresponding callback
 * is called. Note that this IME action may be different from what you specified in
 * [KeyboardOptions.imeAction]
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
            null,
        keyboardActions = keyboardActions
    )
}

/**
 * Function to display a custom [OutlinedTextField]
 *
 * @param modifier: the modifier of the text field
 * @param outlinedTextFieldStyle: the style to apply to the [OutlinedTextField]
 * @param outlinedTextFieldColors: the colors to use for the [OutlinedTextField]
 * @param width: the width of the text field
 * @param value: the action to execute when the alert dialog has been dismissed
 * @param mustBeInLowerCase: whether the input must be in lower case format
 * @param allowsBlankSpaces: whether the input can contain blank spaces or not
 * @param maxLines: the max number of lines supported, different from one line the text field is considered as text area,
 * otherwise simple text field
 * @param validator: the function to invoke to validate the input
 * @param isError: whether the text field is in an error state
 * @param onValueChange the callback that is triggered when the input service updates the text. An
 * updated text comes as a parameter of the callback
 * @param label: the label displayed in the text field
 * @param labelStyle: the style to apply to the label
 * @param placeholder: the placeholder displayed in the text field
 * @param placeholderStyle: the style to apply to the placeholder
 * @param errorText: the error text to display if [isError] is true
 * @param errorTextStyle: the style to apply to the error text
 * @param trailingIcon: the optional trailing icon to be displayed at the end of the text field container
 * @param visualTransformation: transforms the visual representation of the input [value]
 * @param keyboardOptions software keyboard options that contains configuration
 * @param keyboardActions when the input service emits an IME action, the corresponding callback
 * is called. Note that this IME action may be different from what you specified in
 * [KeyboardOptions.imeAction]
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
        keyboardActions = keyboardActions
    )
}

/**
 * Function to display a custom [OutlinedTextField]
 *
 * @param modifier: the modifier of the text field
 * @param outlinedTextFieldStyle: the style to apply to the [OutlinedTextField]
 * @param outlinedTextFieldColors: the colors to use for the [OutlinedTextField]
 * @param width: the width of the text field
 * @param value: the action to execute when the alert dialog has been dismissed
 * @param mustBeInLowerCase: whether the input must be in lower case format
 * @param allowsBlankSpaces: whether the input can contain blank spaces or not
 * @param maxLines: the max number of lines supported, different from one line the text field is considered as text area,
 * otherwise simple text field
 * @param validator: the function to invoke to validate the input
 * @param isError: whether the text field is in an error state
 * @param onValueChange the callback that is triggered when the input service updates the text. An
 * updated text comes as a parameter of the callback
 * @param label: the label displayed in the text field
 * @param labelStyle: the style to apply to the label
 * @param placeholder: the placeholder displayed in the text field
 * @param placeholderStyle: the style to apply to the placeholder
 * @param errorText: the error text to display if [isError] is true
 * @param errorTextStyle: the style to apply to the error text
 * @param trailingIcon: the optional trailing icon to be displayed at the end of the text field container
 * @param visualTransformation: transforms the visual representation of the input [value]
 * @param keyboardOptions software keyboard options that contains configuration
 * @param keyboardActions when the input service emits an IME action, the corresponding callback
 * is called. Note that this IME action may be different from what you specified in
 * [KeyboardOptions.imeAction]
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
            null,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
    )
}