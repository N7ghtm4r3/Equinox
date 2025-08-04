package com.tecknobit.equinoxcompose.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorProducer
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * `DefaultIconSize` The default size to apply to an icon if no one has been specified
 */
private val DefaultIconSize = 24.0.dp

/**
 * Custom [Icon] with the possibility to display an informative text when the user interact with this component
 *
 * @param modifier The [Modifier] to be applied to this icon
 * @param infoTextColor The color to apply to the [infoText]
 * @param infoTextBackgroundColor The color to apply to the background of the [infoText]
 * @param size The size of the icon
 * @param imageVector The [ImageVector] to draw inside this icon
 * @param infoText The informative text about what the icon represents, it is used also as `contentDescription`
 * @param tint The tint to be applied to [imageVector]. If [Color.Unspecified] is provided, then no tint
 * is applied
 */
@Composable
@ExperimentalMaterial3Api
fun InformativeIcon(
    modifier: Modifier = Modifier,
    infoTextColor: Color = TooltipDefaults.plainTooltipContentColor,
    infoTextBackgroundColor: Color = TooltipDefaults.plainTooltipContainerColor,
    size: Dp = DefaultIconSize,
    imageVector: ImageVector,
    infoText: String,
    tint: Color = LocalContentColor.current,
) {
    InformativeIconImpl(
        infoText = infoText,
        infoTextColor = infoTextColor,
        infoTextBackgroundColor = infoTextBackgroundColor,
        iconContent = {
            Icon(
                modifier = modifier
                    .size(size),
                imageVector = imageVector,
                tint = tint,
                contentDescription = infoText
            )
        }
    )
}

/**
 * Custom [Icon] with the possibility to display an informative text when the user interact with this component
 *
 * @param modifier The [Modifier] to be applied to this icon
 * @param infoTextColor The color to apply to the [infoText]
 * @param infoTextBackgroundColor The color to apply to the background of the [infoText]
 * @param size The size of the icon
 * @param bitmap The [ImageBitmap] to draw inside this icon
 * @param infoText The informative text about what the icon represents, it is used also as `contentDescription`
 * @param tint The tint to be applied to [bitmap]. If [Color.Unspecified] is provided, then no tint is
 * applied
 */
@Composable
@ExperimentalMaterial3Api
fun InformativeIcon(
    modifier: Modifier = Modifier,
    infoTextColor: Color = TooltipDefaults.plainTooltipContentColor,
    infoTextBackgroundColor: Color = TooltipDefaults.plainTooltipContainerColor,
    size: Dp = DefaultIconSize,
    bitmap: ImageBitmap,
    infoText: String,
    tint: Color = LocalContentColor.current,
) {
    InformativeIconImpl(
        infoText = infoText,
        infoTextColor = infoTextColor,
        infoTextBackgroundColor = infoTextBackgroundColor,
        iconContent = {
            Icon(
                modifier = modifier
                    .size(size),
                bitmap = bitmap,
                tint = tint,
                contentDescription = infoText
            )
        }
    )
}

/**
 * Custom [Icon] with the possibility to display an informative text when the user interact with this component
 *
 * @param modifier The [Modifier] to be applied to this icon
 * @param infoTextColor The color to apply to the [infoText]
 * @param infoTextBackgroundColor The color to apply to the background of the [infoText]
 * @param size The size of the icon
 * @param painter The [Painter] to draw inside this icon
 * @param infoText The informative text about what the icon represents, it is used also as `contentDescription`
 * @param tint The tint to be applied to [painter]. If [Color.Unspecified] is provided, then no tint is
 * applied
 */
@Composable
@ExperimentalMaterial3Api
fun InformativeIcon(
    modifier: Modifier = Modifier,
    infoTextColor: Color = TooltipDefaults.plainTooltipContentColor,
    infoTextBackgroundColor: Color = TooltipDefaults.plainTooltipContainerColor,
    size: Dp = DefaultIconSize,
    painter: Painter,
    infoText: String,
    tint: Color = LocalContentColor.current,
) {
    InformativeIconImpl(
        infoText = infoText,
        infoTextColor = infoTextColor,
        infoTextBackgroundColor = infoTextBackgroundColor,
        iconContent = {
            Icon(
                modifier = modifier
                    .size(size),
                painter = painter,
                tint = tint,
                contentDescription = infoText
            )
        }
    )
}

/**
 * Custom [Icon] with the possibility to display an informative text when the user interact with this component
 *
 * @param modifier The [Modifier] to be applied to this icon
 * @param infoTextColor The color to apply to the [infoText]
 * @param infoTextBackgroundColor The color to apply to the background of the [infoText]
 * @param painter The [Painter] to draw inside this icon
 * @param infoText The informative text about what the icon represents, it is used also as `contentDescription`
 * @param tint The tint to be applied to [painter]. If [Color.Unspecified] is provided, then no tint is
 * applied
 */
@Composable
@ExperimentalMaterial3Api
fun InformativeIcon(
    modifier: Modifier = Modifier,
    infoTextColor: Color = TooltipDefaults.plainTooltipContentColor,
    infoTextBackgroundColor: Color = TooltipDefaults.plainTooltipContainerColor,
    painter: Painter,
    infoText: String,
    tint: ColorProducer?,
) {
    InformativeIconImpl(
        infoText = infoText,
        infoTextColor = infoTextColor,
        infoTextBackgroundColor = infoTextBackgroundColor,
        iconContent = {
            Icon(
                modifier = modifier,
                painter = painter,
                tint = tint,
                contentDescription = infoText
            )
        }
    )
}

/**
 * Custom [TooltipBox] to provide the possibility to display an informative text when the user interacts with an [Icon]
 *
 * @param infoTextColor The color to apply to the [infoText]
 * @param infoTextBackgroundColor The color to apply to the background of the [infoText]
 * @param infoText The informative text about what the icon represents
 * @param iconContent The icon content to wrap and to display
 */
@Composable
@ExperimentalMaterial3Api
private fun InformativeIconImpl(
    infoTextColor: Color,
    infoTextBackgroundColor: Color,
    infoText: String,
    iconContent: @Composable () -> Unit,
) {
    TooltipBox(
        state = rememberTooltipState(),
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        tooltip = {
            PlainTooltip(
                contentColor = infoTextColor,
                containerColor = infoTextBackgroundColor,
                content = {
                    Text(
                        text = infoText
                    )
                }
            )
        },
        content = iconContent
    )
}