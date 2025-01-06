package com.tecknobit.equinoxcompose.components.splittext

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection.Companion.Next
import androidx.compose.ui.focus.FocusDirection.Companion.Previous
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * **focusManager** -> the focus manager
 */
private lateinit var focusManager: FocusManager

/**
 * Informative text about what the [SplitText] needs
 *
 * @param text The informative text
 * @param textStyle The style to apply to the [text]
 * @param fontSize The size of the [text]
 * @param color The color of the [text]
 */
data class InfoText(
    val text: String,
    val textStyle: TextStyle = TextStyle.Default,
    val fontSize: TextUnit = 14.sp,
    val color: Color? = null,
)

/**
 * Component to allow the user to insert a split text such OTP codes, PIN or any other texts of this type
 *
 * @param columnModifier The modifier to apply to the [Column] container
 * @param rowModifier The modifier to apply to the [LazyRow] container
 * @param splitsTextState The state used to manage this component
 * @param spacingBetweenBoxes The spacing between the boxes
 * @param boxShape The shape to apply to the [SplitBox]
 * @param boxTextStyle The text style to use for the [SplitBox]'s text
 * @param infoText The informative text about what the component needs
 */
@Composable
@NonRestartableComposable
fun SplitText(
    columnModifier: Modifier = Modifier,
    rowModifier: Modifier = Modifier,
    splitsTextState: SplitTextState,
    spacingBetweenBoxes: Dp = 10.dp,
    boxShape: Shape = CardDefaults.shape,
    boxTextStyle: TextStyle = TextStyle(
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    ),
    infoText: InfoText? = null,
) {
    focusManager = LocalFocusManager.current
    splitsTextState.CreateSlices()
    Column(
        modifier = columnModifier
    ) {
        LazyRow(
            modifier = rowModifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacingBetweenBoxes)
        ) {
            itemsIndexed(
                items = splitsTextState.textSlices
            ) { index, textSlice ->
                SplitBox(
                    currentTextSlices = splitsTextState.textSlices,
                    boxShape = boxShape,
                    boxTextStyle = boxTextStyle,
                    textSlice = textSlice,
                    currentBox = index
                )
            }
        }
        infoText?.let {
            Text(
                modifier = Modifier
                    .padding(
                        top = 5.dp
                    ),
                text = infoText.text,
                fontSize = infoText.fontSize,
                color = infoText.color ?: MaterialTheme.colorScheme.primary
            )
        }
    }
}

/**
 * Single component to allow the user to a slice of the complete text of the [SplitText] component
 *
 * @param currentTextSlices The array container of each parts of the split text
 * @param boxSelectedColor The color to use when the box is currently selected
 * @param boxShape The shape to apply to the [SplitBox]
 * @param boxTextStyle The text style to use for the [SplitBox]'s text
 * @param textSlice The slice of the complete text to fill
 * @param currentBox The index of the current box where the focus is applied
 */
@Composable
@NonRestartableComposable
private fun SplitBox(
    currentTextSlices: ArrayList<MutableState<String>>,
    boxSelectedColor: Color = MaterialTheme.colorScheme.inversePrimary,
    boxShape: Shape,
    boxTextStyle: TextStyle,
    textSlice: MutableState<String>,
    currentBox: Int,
) {
    val isLast = currentBox == currentTextSlices.lastIndex
    val keyboardManager = LocalSoftwareKeyboardController.current
    var selectedBoxColor by remember { mutableStateOf(Color.Transparent) }
    var hasFocus by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .onKeyEvent { event ->
                if (event.key == Key.Backspace) {
                    textSlice.value = ""
                    if (currentBox > 0) {
                        if (!isLast)
                            currentTextSlices[currentBox - 1].value = ""
                        focusManager.moveFocus(Previous)
                    }
                }
                false
            }
            .onFocusChanged {
                hasFocus = it.hasFocus
                selectedBoxColor = if (hasFocus)
                    boxSelectedColor
                else
                    Color.Transparent
            }
            .border(
                color = selectedBoxColor,
                width = 2.dp,
                shape = boxShape
            )
            .size(50.dp),
        shape = boxShape,
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (hasFocus)
                3.dp
            else
                1.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            BasicTextField(
                modifier = Modifier
                    .align(Alignment.Center),
                value = textSlice.value,
                onValueChange = { slice ->
                    val sliceLength = slice.length
                    if (sliceLength == currentTextSlices.size) {
                        pasteSlices(
                            splits = sliceLength,
                            currentTextSlices = currentTextSlices,
                            slice = slice
                        )
                    } else {
                        if (slice.isEmpty())
                            textSlice.value = slice
                        else {
                            if (!isLast) {
                                textSlice.value = slice.first().toString()
                                focusManager.moveFocus(Next)
                            } else
                                textSlice.value = slice.last().toString()
                        }
                    }
                },
                textStyle = boxTextStyle,
                cursorBrush = SolidColor(Color.Transparent),
                keyboardOptions = KeyboardOptions(
                    imeAction = if (isLast)
                        ImeAction.Done
                    else
                        ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardManager!!.hide()
                    }
                )
            )
        }
    }
}

/**
 * Function to automatically all the [SplitBox] when the user copy the text to insert in the [SplitText]
 *
 * @param splits The number of splits used to create the [SplitText] component
 * @param currentTextSlices The array container of the parts of the split text
 * @param slice The complete text requested to copy in the component
 */
private fun pasteSlices(
    splits: Int,
    currentTextSlices: ArrayList<MutableState<String>>,
    slice: String,
) {
    repeat(splits) { offset ->
        currentTextSlices[offset].value = slice.drop(offset).first().toString()
    }
}