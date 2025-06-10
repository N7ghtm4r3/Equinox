package com.tecknobit.equinoxcompose.components.quantitypicker

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tecknobit.equinoxcompose.components.quantitypicker.QuantityPickerDefaults.QuantityPickerColors

/**
 * Component used to pick a numerical quantity value
 *
 * @param modifier The modifier to apply to the component
 * @param state The state of the component
 * @param informativeText The informative text which describes what type of quantity is picked
 * @param informativeTextStyle The style to apply to the [informativeText]
 * @param indicatorsSize The size of the indicators buttons must have
 * @param indicatorsShape The shape of the indicators buttons must have
 * @param onQuantityPicked Callback to invoke when a quantity has been picked
 * @param decrementButtonColors The colors to apply to the decrement button
 * @param decrementIcon The icon of the decrement button
 * @param quantityIndicatorStyle The style to apply to the indicator text of the current quantity picked
 * @param incrementButtonColors The colors to apply to the increment button
 * @param incrementIcon The icon of the increment button
 * @param enabled Whether the picker is enabled to pick quantities
 *
 * @since 1.1.0
 */
@ExperimentalComposeApi
@Composable
fun QuantityPicker(
    modifier: Modifier = Modifier,
    state: QuantityPickerState,
    informativeText: String? = null,
    informativeTextStyle: TextStyle = LocalTextStyle.current,
    indicatorsSize: Dp = QuantityPickerDefaults.IndicatorButtonSize,
    indicatorsShape: Shape = QuantityPickerDefaults.IndicatorButtonShape,
    onQuantityPicked: ((Int) -> Unit)? = null,
    decrementButtonColors: QuantityPickerColors = QuantityPickerDefaults.colors(),
    decrementIcon: ImageVector = Icons.Default.Remove,
    quantityIndicatorStyle: TextStyle = LocalTextStyle.current,
    incrementButtonColors: QuantityPickerColors = QuantityPickerDefaults.colors(),
    incrementIcon: ImageVector = Icons.Default.Add,
    enabled: Boolean = true,
) {
    QuantityPicker(
        modifier = modifier,
        state = state,
        informativeText = informativeText,
        informativeTextStyle = informativeTextStyle,
        indicatorsSize = indicatorsSize,
        indicatorsShape = indicatorsShape,
        decrementButtonColors = decrementButtonColors,
        decrementIcon = decrementIcon,
        onDecrement = onQuantityPicked,
        quantityIndicatorStyle = quantityIndicatorStyle,
        incrementButtonColors = incrementButtonColors,
        incrementIcon = incrementIcon,
        onIncrement = onQuantityPicked,
        enabled = enabled
    )
}

/**
 * Component used to pick a numerical quantity value
 *
 * @param modifier The modifier to apply to the component
 * @param state The state of the component
 * @param informativeText The informative text which describes what type of quantity is picked
 * @param informativeTextStyle The style to apply to the [informativeText]
 * @param indicatorsSize The size of the indicators buttons must have
 * @param indicatorsShape The shape of the indicators buttons must have
 * @param decrementButtonColors The colors to apply to the decrement button
 * @param decrementIcon The icon of the decrement button
 * @param onDecrement Callback to invoke when a quantity has been decremented
 * @param quantityIndicatorStyle The style to apply to the indicator text of the current quantity picked
 * @param incrementButtonColors The colors to apply to the increment button
 * @param incrementIcon The icon of the increment button
 * @param onIncrement Callback to invoke when a quantity has been incremented
 * @param enabled Whether the picker is enabled to pick quantities
 *
 * @since 1.1.0
 */
@ExperimentalComposeApi
@Composable
fun QuantityPicker(
    modifier: Modifier = Modifier,
    state: QuantityPickerState,
    informativeText: String? = null,
    informativeTextStyle: TextStyle = LocalTextStyle.current,
    indicatorsSize: Dp = QuantityPickerDefaults.IndicatorButtonSize,
    indicatorsShape: Shape = QuantityPickerDefaults.IndicatorButtonShape,
    decrementButtonColors: QuantityPickerColors = QuantityPickerDefaults.colors(),
    decrementIcon: ImageVector = Icons.Default.Remove,
    onDecrement: ((Int) -> Unit)? = null,
    quantityIndicatorStyle: TextStyle = LocalTextStyle.current,
    incrementButtonColors: QuantityPickerColors = QuantityPickerDefaults.colors(),
    incrementIcon: ImageVector = Icons.Default.Add,
    onIncrement: ((Int) -> Unit)? = null,
    enabled: Boolean = true,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        informativeText?.let {
            Text(
                text = informativeText,
                style = informativeTextStyle
            )
        }
        DecrementButton(
            colors = decrementButtonColors,
            size = indicatorsSize,
            shape = indicatorsShape,
            icon = decrementIcon,
            state = state,
            onDecrement = onDecrement,
            enabled = enabled
        )
        Text(
            text = state.quantityPicked.toString(),
            style = quantityIndicatorStyle
        )
        IncrementButton(
            colors = incrementButtonColors,
            size = indicatorsSize,
            shape = indicatorsShape,
            icon = incrementIcon,
            state = state,
            onIncrement = onIncrement,
            enabled = enabled
        )
    }
}

/**
 * Custom [QuantityButton] used to decrement the quantity value picked by the [QuantityPicker]
 *
 * @param colors The colors to apply to the button
 * @param size The size of the button
 * @param shape The shape of the button
 * @param icon The icon of the button
 * @param state The state of the component
 * @param onDecrement Callback to invoke when a quantity has been decremented
 * @param enabled Whether the button is enabled to decrement quantities
 *
 * @since 1.1.0
 */
@Composable
private fun DecrementButton(
    colors: QuantityPickerColors,
    size: Dp,
    shape: Shape,
    icon: ImageVector,
    state: QuantityPickerState,
    onDecrement: ((Int) -> Unit)? = null,
    enabled: Boolean,
) {
    QuantityButton(
        colors = colors,
        size = size,
        shape = shape,
        icon = icon,
        quantityAction = {
            state.simpleDecrement()
            onDecrement?.invoke(state.quantityPicked)
        },
        longPressQuantityAction = if (state.longPressEnabled) {
            {
                state.longDecrement()
                onDecrement?.invoke(state.quantityPicked)
            }
        } else
            null,
        enabled = enabled
    )
}

/**
 * Custom [QuantityButton] used to increment the quantity value picked by the [QuantityPicker]
 *
 * @param colors The colors to apply to the button
 * @param size The size of the button
 * @param shape The shape of the button
 * @param icon The icon of the button
 * @param state The state of the component
 * @param onIncrement Callback to invoke when a quantity has been incremented
 * @param enabled Whether the button is enabled to increment quantities
 *
 * @since 1.1.0
 */
@Composable
private fun IncrementButton(
    colors: QuantityPickerColors,
    size: Dp,
    shape: Shape,
    icon: ImageVector,
    state: QuantityPickerState,
    onIncrement: ((Int) -> Unit)? = null,
    enabled: Boolean,
) {
    QuantityButton(
        colors = colors,
        size = size,
        shape = shape,
        icon = icon,
        quantityAction = {
            state.simpleIncrement()
            onIncrement?.invoke(state.quantityPicked)
        },
        longPressQuantityAction = if (state.longPressEnabled) {
            {
                state.longIncrement()
                onIncrement?.invoke(state.quantityPicked)
            }
        } else
            null,
        enabled = enabled
    )
}

/**
 * Custom [Box] used to handle the quantity value picked by the [QuantityPicker]
 *
 * @param colors The colors to apply to the button
 * @param size The size of the button
 * @param shape The shape of the button
 * @param icon The icon of the button
 * @param quantityAction The callback action invoked to decrement or increment the quantity picked
 * @param longPressQuantityAction The callback action invoked after the button has been double-clicked or long pressed
 * the button
 * @param enabled Whether the button is enabled to perform any quantity actions
 *
 * @since 1.1.0
 */
@Composable
private fun QuantityButton(
    colors: QuantityPickerColors,
    size: Dp,
    shape: Shape,
    icon: ImageVector,
    quantityAction: () -> Unit,
    longPressQuantityAction: (() -> Unit)? = null,
    enabled: Boolean,
) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(shape)
            .background(
                color = if (enabled)
                    colors.containerColor
                else
                    colors.disabledContainerColor
            )
            .combinedClickable(
                enabled = enabled,
                onClick = quantityAction,
                onDoubleClick = longPressQuantityAction,
                onLongClick = longPressQuantityAction
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (enabled)
                colors.iconColor
            else
                colors.disabledIconColor
        )
    }
}

/**
 * The `QuantityButtonAppearance` allows to customize the appearance of the [QuantityButton]
 *
 * @property modifier The modifier to apply to the button
 * @property shape The shape of the button
 * @property size The size of the button
 * @property background The background of the button
 * @property icon The representative icon of the button
 *
 * @author N7ghtm4r3
 *
 * @since 1.1.0
 */
@Deprecated(
    message = "This way to customize the appearance has been replaced with QuantityPickerDefaults.colors instead",
    level = DeprecationLevel.WARNING,
    replaceWith = ReplaceWith(
        expression = "QuantityPickerDefaults.colors",
        "com.tecknobit.equinoxcompose.components.quantitypicker.QuantityPickerDefaults"
    )
)
data class QuantityButtonAppearance(
    val modifier: Modifier = Modifier,
    val shape: Shape = CircleShape,
    val size: Dp = 30.dp,
    val background: Color,
    val icon: ImageVector,
)

/**
 * Method used to create a customization style for a [QuantityButton]
 *
 * @param modifier The modifier to apply to the button
 * @param shape The shape of the button
 * @param size The size of the button
 * @param background The background of the button
 * @param icon The representative icon of the button
 *
 * @return the customization style for a button as [QuantityButtonAppearance]
 */
@Deprecated(
    message = "This way to customize the appearance has been replaced with QuantityPickerDefaults.colors instead",
    level = DeprecationLevel.ERROR,
    replaceWith = ReplaceWith(
        expression = "QuantityPickerDefaults.colors",
        "com.tecknobit.equinoxcompose.components.quantitypicker.QuantityPickerDefaults"
    )
)
@Composable
fun quantityButtonAppearance(
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    size: Dp = 30.dp,
    background: Color = MaterialTheme.colorScheme.primary,
    icon: ImageVector,
): QuantityButtonAppearance {
    return QuantityButtonAppearance(
        modifier = modifier,
        shape = shape,
        size = size,
        background = background,
        icon = icon
    )
}