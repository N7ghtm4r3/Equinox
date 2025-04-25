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
import androidx.compose.material3.Icon
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

/**
 * Component used to pick a numerical quantity value
 *
 * @param modifier The modifier to apply to the component
 * @param state The state of the component
 * @param informativeText The informative text which describes what type of quantity is picked
 * @param informativeTextStyle The style to apply to the [informativeText]
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
    informativeTextStyle: TextStyle = TextStyle.Default,
    decrementButtonAppearance: QuantityButtonAppearance = quantityButtonAppearance(
        icon = Icons.Default.Remove
    ),
    quantityIndicatorStyle: TextStyle = TextStyle.Default,
    incrementButtonAppearance: QuantityButtonAppearance = quantityButtonAppearance(
        icon = Icons.Default.Add
    ),
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
            state = state
        )
        Text(
            text = state.quantityPicked.toString(),
            style = quantityIndicatorStyle
        )
        IncrementButton(
            appearance = incrementButtonAppearance,
            state = state
        )
    }
}

/**
 * Custom [QuantityButton] used to decrement the quantity value picked by the [QuantityPicker]
 *
 * @param appearance The appearance for the button
 * @param state The state of the component
 *
 * @since 1.1.0
 */
@Composable
private fun DecrementButton(
    appearance: QuantityButtonAppearance,
    state: QuantityPickerState,
) {
    QuantityButton(
        appearance = appearance,
        quantityAction = {
            state.simpleDecrement()
        },
        longPressQuantityAction = if (state.longPressEnabled) {
            {
                state.longDecrement()
            }
        } else
            null
    )
}

/**
 * Custom [QuantityButton] used to increment the quantity value picked by the [QuantityPicker]
 *
 * @param appearance The appearance for the button
 * @param state The state of the component
 *
 * @since 1.1.0
 */
@Composable
private fun IncrementButton(
    appearance: QuantityButtonAppearance,
    state: QuantityPickerState,
) {
    QuantityButton(
        appearance = appearance,
        quantityAction = {
            state.simpleIncrement()
        },
        longPressQuantityAction = if (state.longPressEnabled) {
            {
                state.longIncrement()
            }
        } else
            null
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
) {
    Box(
        modifier = appearance.modifier
            .size(appearance.size)
            .clip(appearance.shape)
            .background(appearance.background)
            .combinedClickable(
                onClick = quantityAction,
                onDoubleClick = longPressQuantityAction,
                onLongClick = longPressQuantityAction
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = appearance.icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary
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