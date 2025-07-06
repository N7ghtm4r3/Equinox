package com.tecknobit.equinoxcompose.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import com.tecknobit.equinoxcompose.resources.Res
import com.tecknobit.equinoxcompose.resources.retry
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/**
 * Custom [TextButton] used to retry any actions
 *
 * @param modifier The modifier to apply to the button
 * @param enabled Whether the button is enabled
 * @param shape The shape of the button
 * @param colors The colors of the button
 * @param elevation The elevation to apply to the button
 * @param border The style to apply to the borders
 * @param contentPadding The padding the content must have
 * @param interactionSource An optional hoisted [MutableInteractionSource] for observing and
 * emitting [androidx.compose.foundation.interaction.Interaction]s for this button. You can use this to change the
 * button's appearance or preview the button in different states. Note that if `null` is provided, interactions will
 * still happen internally
 * @param onRetry The retry callback to invoke
 * @param text The indicative text to retry an action
 */
@Composable
fun RetryTextButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.textShape,
    colors: ButtonColors = ButtonDefaults.textButtonColors(),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.TextButtonContentPadding,
    interactionSource: MutableInteractionSource? = null,
    onRetry: () -> Unit,
    text: StringResource = Res.string.retry,
) {
    TextButton(
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        onClick = onRetry
    ) {
        Text(
            text = stringResource(text)
        )
    }
}

/**
 * Custom [Button] used to retry any actions
 *
 * @param modifier The modifier to apply to the button
 * @param enabled Whether the button is enabled
 * @param shape The shape of the button
 * @param colors The colors of the button
 * @param elevation The elevation to apply to the button
 * @param border The style to apply to the borders
 * @param contentPadding The padding the content must have
 * @param interactionSource An optional hoisted [MutableInteractionSource] for observing and
 * emitting [androidx.compose.foundation.interaction.Interaction]s for this button. You can use this to change the
 * button's appearance or preview the button in different states. Note that if `null` is provided, interactions will
 * still happen internally
 * @param onRetry The retry callback to invoke
 * @param text The indicative text to retry an action
 */
@Composable
fun RetryButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource? = null,
    onRetry: () -> Unit,
    text: StringResource = Res.string.retry,
) {
    Button(
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        onClick = onRetry
    ) {
        Text(
            text = stringResource(text)
        )
    }
}