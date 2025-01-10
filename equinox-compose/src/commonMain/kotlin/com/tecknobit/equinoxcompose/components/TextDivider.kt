package com.tecknobit.equinoxcompose.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/**
 *
 * Component to divide sections by a representative text
 *
 * @param containerModifier: the [Modifier] to apply to the container row
 * @param textModifier: the [Modifier] to apply to the text composable
 * @param fillMaxWidth: whether the composable must occupy the entire horizontal space
 * @param thickness: thickness of this divider line. Using [Dp.Hairline] will produce a single pixel
 * divider regardless of screen density
 * @param dividerColor: color of this divider line
 * @param text: the text message
 * @param textStyle: the style to apply to the [text]
 */
@Composable
@NonRestartableComposable
fun TextDivider(
    containerModifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    fillMaxWidth: Boolean = true,
    thickness: Dp = DividerDefaults.Thickness,
    dividerColor: Color = DividerDefaults.color,
    text: StringResource,
    textStyle: TextStyle = TextStyle.Default,
) {
    TextDivider(
        containerModifier = containerModifier,
        fillMaxWidth = fillMaxWidth,
        thickness = thickness,
        dividerColor = dividerColor,
        text = stringResource(text),
        textStyle = textStyle
    )
}

/**
 *
 * Component to divide sections by a representative text
 *
 * @param containerModifier: the [Modifier] to apply to the container row
 * @param textModifier: the [Modifier] to apply to the text composable
 * @param fillMaxWidth: whether the composable must occupy the entire horizontal space
 * @param thickness: thickness of this divider line. Using [Dp.Hairline] will produce a single pixel
 * divider regardless of screen density
 * @param dividerColor: color of this divider line
 * @param text: the text message
 * @param textStyle: the style to apply to the [text]
 */
@Composable
@NonRestartableComposable
fun TextDivider(
    containerModifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    fillMaxWidth: Boolean = true,
    thickness: Dp = DividerDefaults.Thickness,
    dividerColor: Color = DividerDefaults.color,
    text: String,
    textStyle: TextStyle = TextStyle.Default,
) {
    Row(
        modifier = containerModifier
            .then(
                if (fillMaxWidth)
                    Modifier.fillMaxWidth()
                else
                    Modifier
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        CustomDivider(
            modifier = Modifier
                .weight(1f),
            thickness = thickness,
            color = dividerColor
        )
        Text(
            modifier = textModifier,
            text = text,
            style = textStyle,
            textAlign = TextAlign.Center
        )
        CustomDivider(
            modifier = Modifier
                .weight(1f),
            thickness = thickness,
            color = dividerColor
        )
    }
}

/**
 *
 * Custom divider to center the text message between two lines
 *
 * @param modifier: the [Modifier] to be applied to this divider line.
 * @param thickness: thickness of this divider line. Using [Dp.Hairline] will produce a single pixel
 * divider regardless of screen density.
 * @param color: color of this divider line.
 */
@Composable
@NonRestartableComposable
private fun CustomDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = DividerDefaults.Thickness,
    color: Color = DividerDefaults.color,
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
    ) {
        drawLine(
            color = color,
            strokeWidth = thickness.toPx(),
            start = Offset(0f, thickness.toPx() / 2),
            end = Offset(size.width, thickness.toPx() / 2),
        )
    }
}