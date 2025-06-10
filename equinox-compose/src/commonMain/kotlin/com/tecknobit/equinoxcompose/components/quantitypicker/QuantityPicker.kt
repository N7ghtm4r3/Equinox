@file:OptIn(ExperimentalFoundationApi::class)

package com.tecknobit.equinoxcompose.components.quantitypicker

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material3.*
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

/**
 * Component used to pick a numerical quantity value
 *
 * @param modifier The modifier to apply to the component
 * @param state The state of the component
 * @param informativeText The informative text which describes what type of quantity is picked
 * @param informativeTextStyle The style to apply to the [informativeText]
 * @param onQuantityPicked Callback to invoke when a quantity has been picked
 * @param decrementButtonAppearance The appearance for the [DecrementButton] component
 * @param quantityIndicatorStyle The style to apply to the indicator text of the current quantity picked
 * @param incrementButtonAppearance The appearance for the [IncrementButton] component
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
    onQuantityPicked: ((Int) -> Unit)? = null,
    decrementButtonAppearance: QuantityButtonAppearance = quantityButtonAppearance(
        icon = Icons.Default.Remove
    ),
    quantityIndicatorStyle: TextStyle = LocalTextStyle.current,
    incrementButtonAppearance: QuantityButtonAppearance = quantityButtonAppearance(
        icon = Icons.Default.Add
    ),
    enabled: Boolean = true,
) {
    QuantityPicker(
        modifier = modifier,
        state = state,
        informativeText = informativeText,
        informativeTextStyle = informativeTextStyle,
        onDecrement = onQuantityPicked,
        decrementButtonAppearance = decrementButtonAppearance,
        quantityIndicatorStyle = quantityIndicatorStyle,
        onIncrement = onQuantityPicked,
        incrementButtonAppearance = incrementButtonAppearance,
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
 * @param onDecrement Callback to invoke when a quantity has been decremented
 * @param decrementButtonAppearance The appearance for the [DecrementButton] component
 * @param quantityIndicatorStyle The style to apply to the indicator text of the current quantity picked
 * @param onIncrement Callback to invoke when a quantity has been incremented
 * @param incrementButtonAppearance The appearance for the [IncrementButton] component
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
    onDecrement: ((Int) -> Unit)? = null,
    decrementButtonAppearance: QuantityButtonAppearance = quantityButtonAppearance(
        icon = Icons.Default.Remove
    ),
    quantityIndicatorStyle: TextStyle = LocalTextStyle.current,
    onIncrement: ((Int) -> Unit)? = null,
    incrementButtonAppearance: QuantityButtonAppearance = quantityButtonAppearance(
        icon = Icons.Default.Add
    ),
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
            appearance = decrementButtonAppearance,
            state = state,
            onDecrement = onDecrement,
            enabled = enabled
        )
        Text(
            text = state.quantityPicked.toString(),
            style = quantityIndicatorStyle
        )
        IncrementButton(
            appearance = incrementButtonAppearance,
            state = state,
            onIncrement = onIncrement,
            enabled = enabled
        )
    }
}

/**
 * Custom [QuantityButton] used to decrement the quantity value picked by the [QuantityPicker]
 *
 * @param appearance The appearance for the button
 * @param state The state of the component
 * @param onDecrement Callback to invoke when a quantity has been decremented
 *
 * @since 1.1.0
 */
@Composable
private fun DecrementButton(
    appearance: QuantityButtonAppearance,
    state: QuantityPickerState,
    onDecrement: ((Int) -> Unit)? = null,
    enabled: Boolean,
) {
    QuantityButton(
        appearance = appearance,
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
 * @param appearance The appearance for the button
 * @param state The state of the component
 * @param onIncrement Callback to invoke when a quantity has been incremented
 *
 * @since 1.1.0
 */
@Composable
private fun IncrementButton(
    appearance: QuantityButtonAppearance,
    state: QuantityPickerState,
    onIncrement: ((Int) -> Unit)? = null,
    enabled: Boolean,
) {
    QuantityButton(
        appearance = appearance,
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
 * @param appearance The appearance for the button
 * @param quantityAction The callback action invoked to decrement or increment the quantity picked
 * @param longPressQuantityAction The callback action invoked after the button has been double-clicked or long pressed
 * the button
 *
 * @since 1.1.0
 */
@Composable
private fun QuantityButton(
    appearance: QuantityButtonAppearance,
    quantityAction: () -> Unit,
    longPressQuantityAction: (() -> Unit)? = null,
    enabled: Boolean,
) {
    val background = appearance.background
    Box(
        modifier = appearance.modifier
            .size(appearance.size)
            .clip(appearance.shape)
            .background(
                color = background.copy(
                    alpha = if (enabled)
                        1f
                    else
                        0.12f
                )
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
            imageVector = appearance.icon,
            contentDescription = null,
            tint = contentColorFor(background).copy(
                alpha = if (enabled)
                    1f
                else
                    0.38f
            )
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
    message = ""
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
    message = ""
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