package com.tecknobit.equinoxcompose.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/**
 * `currentActiveWritingMode` the current active writing mode state in use, it allows to manage in automatically
 * the [disableWritingMode] and the [enableWritingMode] mechanisms by the [disableWritingModeOnTap] or
 * the [disableWritingModeOnFocusGain] methods
 */
private lateinit var currentActiveWritingMode: MutableState<Boolean>

/**
 *
 * This component allows to display a text or writing a new one clicking on it or using the [enableWritingMode] method
 * programmatically
 *
 * @param isInWritingMode: the control flag used to enter or exit from the writing mode
 * @param writableModifier: the [Modifier] to apply to the [EquinoxTextField] used in the writing mode
 * @param writeableTextColors: the colors scheme used for the [EquinoxTextField]
 * @param writeableTextWidth: the width of the [EquinoxTextField]
 * @param writeableText: the text state used in the writing mode
 * @param writeableTextStyle: the text style used for the [writeableText]
 * @param onWriting: the action to execute during the writing mode
 * @param placeholder: the placeholder to use for the [EquinoxTextField]
 * @param placeholderStyle: the text style used for the [placeholder]
 * @param displayedTextModifier: the [Modifier] to apply to the [Text] used in not-writing mode
 * @param displayedText: the text displayed in not-writing mode
 * @param displayedTextStyle: the text style used for the [displayedText]
 * @param changeEditModeClickingOnSimpleText: whether clicking on the [Text] the writing mode is directly enabled
 */
@Composable
fun WriteableText(
    isInWritingMode: MutableState<Boolean>,
    writableModifier: Modifier = Modifier,
    writeableTextColors: TextFieldColors = TextFieldDefaults.colors(
        unfocusedContainerColor = MaterialTheme.colorScheme.primary,
        focusedContainerColor = MaterialTheme.colorScheme.primary,
        focusedIndicatorColor = MaterialTheme.colorScheme.inversePrimary,
        unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
        cursorColor = MaterialTheme.colorScheme.inversePrimary,
        focusedTextColor = Color.White,
        unfocusedTextColor = Color.White
    ),
    writeableTextWidth: Dp = 280.dp,
    writeableText: MutableState<String>,
    writeableTextStyle: TextStyle = LocalTextStyle.current.merge(
        fontSize = 20.sp
    ),
    onWriting: (String) -> Unit = {
        writeableText.value = it
    },
    placeholder: StringResource,
    placeholderStyle: TextStyle = LocalTextStyle.current.merge(
        fontSize = 20.sp
    ),
    displayedTextModifier: Modifier = Modifier,
    displayedText: String = writeableText.value,
    displayedTextStyle: TextStyle = LocalTextStyle.current.merge(
        fontSize = 20.sp
    ),
    changeEditModeClickingOnSimpleText: Boolean = true,
) {
    WriteableText(
        isInWritingMode = isInWritingMode,
        writableModifier = writableModifier,
        writeableTextColors = writeableTextColors,
        writeableTextWidth = writeableTextWidth,
        writeableText = writeableText,
        writeableTextStyle = writeableTextStyle,
        onWriting = onWriting,
        placeholder = stringResource(placeholder),
        placeholderStyle = placeholderStyle,
        displayedTextModifier = displayedTextModifier,
        displayedText = displayedText,
        displayedTextStyle = displayedTextStyle,
        changeEditModeClickingOnSimpleText = changeEditModeClickingOnSimpleText
    )
}

/**
 *
 * This component allows to display a text or writing a new one clicking on it or using the [enableWritingMode] method
 * programmatically
 *
 * @param isInWritingMode: the control flag used to enter or exit from the writing mode
 * @param writableModifier: the [Modifier] to apply to the [EquinoxTextField] used in the writing mode
 * @param writeableTextColors: the colors scheme used for the [EquinoxTextField]
 * @param writeableTextWidth: the width of the [EquinoxTextField]
 * @param writeableText: the text state used in the writing mode
 * @param writeableTextStyle: the text style used for the [writeableText]
 * @param onWriting: the action to execute during the writing mode
 * @param placeholder: the placeholder to use for the [EquinoxTextField]
 * @param placeholderStyle: the text style used for the [placeholder]
 * @param displayedTextModifier: the [Modifier] to apply to the [Text] used in not-writing mode
 * @param displayedText: the text displayed in not-writing mode
 * @param displayedTextStyle: the text style used for the [displayedText]
 * @param changeEditModeClickingOnSimpleText: whether clicking on the [Text] the writing mode is directly enabled
 */
@Composable
fun WriteableText(
    isInWritingMode: MutableState<Boolean>,
    writableModifier: Modifier = Modifier,
    writeableTextColors: TextFieldColors = TextFieldDefaults.colors(
        unfocusedContainerColor = MaterialTheme.colorScheme.primary,
        focusedContainerColor = MaterialTheme.colorScheme.primary,
        focusedIndicatorColor = MaterialTheme.colorScheme.inversePrimary,
        unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
        cursorColor = MaterialTheme.colorScheme.inversePrimary,
        focusedTextColor = Color.White,
        unfocusedTextColor = Color.White
    ),
    writeableTextWidth: Dp = 280.dp,
    writeableText: MutableState<String>,
    writeableTextStyle: TextStyle = LocalTextStyle.current.merge(
        fontSize = 20.sp
    ),
    onWriting: (String) -> Unit = {
        writeableText.value = it
    },
    placeholder: String,
    placeholderStyle: TextStyle = LocalTextStyle.current.merge(
        fontSize = 20.sp
    ),
    displayedTextModifier: Modifier = Modifier,
    displayedText: String = writeableText.value,
    displayedTextStyle: TextStyle = LocalTextStyle.current.merge(
        fontSize = 20.sp
    ),
    changeEditModeClickingOnSimpleText: Boolean = true,
) {
    if (isInWritingMode.value) {
        EquinoxTextField(
            modifier = writableModifier,
            textFieldColors = writeableTextColors,
            width = writeableTextWidth,
            value = writeableText,
            onValueChange = onWriting,
            placeholder = placeholder,
            placeholderStyle = placeholderStyle,
            textFieldStyle = writeableTextStyle,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { disableWritingMode() }
            )
        )
        currentActiveWritingMode = isInWritingMode
    } else {
        Text(
            modifier = if (changeEditModeClickingOnSimpleText)
                displayedTextModifier.clickable { isInWritingMode.value = true }
            else
                displayedTextModifier,
            text = displayedText,
            style = displayedTextStyle
        )
    }
}

/**
 * Extension [Modifier] function to apply to the components which have to manage the [currentActiveWritingMode], this
 * method allows to disable the writing mode tapping any part of the component where this method is applied
 *
 * #### Usage example:
 *
 * ```
 * val isWritingMode = remember { mutableStateOf(false) }
 * Scaffold(
 *      modifier = Modifier
 *          .disableWritingModeOnTap(), // clicking on any point of the Scaffold will disable the writing mode
 *      topBar = {
 *          LargeTopAppBar(
 *              colors = TopAppBarDefaults.topAppBarColors(
 *                  containerColor = MaterialTheme.colorScheme.primary
 *              ),
 *              title = {
 *                  val text = remember { mutableStateOf("any_text") }
 *                  WriteableText(
 *                      isInWritingMode = isWritingMode,
 *                      writeableText = text,
 *                      placeholder = "placeholder"
 *                  )
 *              }
 *          )
 *      }
 * ) {
 *      ...
 * }
 * ```
 */
fun Modifier.disableWritingModeOnTap() = this
    .pointerInput(Unit) {
        detectTapGestures {
            disableWritingMode()
        }
    }

/**
 * Extension [Modifier] function to apply to the components which have to manage the [currentActiveWritingMode], this
 * method allows to disable the writing mode when the component where this method is applied gains focus
 *
 * #### Usage example:
 *
 * ```kotlin
 * val isWritingMode = remember { mutableStateOf(false) }
 * Scaffold(
 *      modifier = Modifier
 *          .disableWritingModeOnTap(), // clicking on any point of the Scaffold will disable the writing mode
 *      topBar = {
 *          LargeTopAppBar(
 *              colors = TopAppBarDefaults.topAppBarColors(
 *                  containerColor = MaterialTheme.colorScheme.primary
 *              ),
 *              title = {
 *                  val text = remember { mutableStateOf("any_text") }
 *                  WriteableText(
 *                      isInWritingMode = isWritingMode,
 *                      writeableText = text,
 *                      placeholder = "placeholder"
 *                  )
 *              }
 *          )
 *      }
 * ) {
 *      val value = remember { mutableStateOf("") }
 *      Column(
 *          modifier = Modifier
 *             .fillMaxSize(),
 *          horizontalAlignment = Alignment.CenterHorizontally,
 *          verticalArrangement = Arrangement.Center
 *      ) {
 *          EquinoxTextField(
 *              modifier = Modifier
 *                 .disableWritingModeOnFocusGain(), // when this component gains the focus will disable the writing mode
 *              value = value,
 *              label = "label"
 *          )
 *      }
 * }
 * ```
 */
fun Modifier.disableWritingModeOnFocusGain() = this
    .onFocusEvent { event ->
        if (event.hasFocus)
            disableWritingMode()
    }

/**
 * Method used to enable the [currentActiveWritingMode]
 *
 * No-any params required
 */
fun enableWritingMode() {
    if (::currentActiveWritingMode.isInitialized)
        currentActiveWritingMode.value = true
}

/**
 * Method used to disable the [currentActiveWritingMode]
 *
 * No-any params required
 */
fun disableWritingMode() {
    if (::currentActiveWritingMode.isInitialized)
        currentActiveWritingMode.value = false
}