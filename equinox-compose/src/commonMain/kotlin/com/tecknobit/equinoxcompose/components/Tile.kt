package com.tecknobit.equinoxcompose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/**
 * Tile component useful to execute action when clicked
 *
 * @param modifier The modifier to apply to the container [Card]
 * @param strokeWidth The stroke width to apply as dashed effect
 * @param intervals The number of the interval from each part of the line
 * @param phase The pixel offset for the intervals
 * @param size The size of the tile
 * @param cornerRadius The radius of the tile
 * @param containerColor The colors scheme to apply to the tile
 * @param contentColor The color of the content, icon and the text
 * @param elevation The elevation of the tile
 * @param icon The representative icon
 * @param iconSize The size of the [icon]
 * @param text The representative text
 * @param fontWeight The weight to apply to the [text]
 * @param textStyle The style to apply to the [text]
 * @param onClick The action to execute when the tile has been clicked
 */
@Composable
fun DashedTile(
    modifier: Modifier = Modifier,
    strokeWidth: Float = 5f,
    intervals: FloatArray = floatArrayOf(10f, 10f),
    phase: Float = 0f,
    size: Dp = 115.dp,
    cornerRadius: Dp = 15.dp,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = contentColorFor(containerColor),
    elevation: Dp = 0.dp,
    icon: ImageVector,
    iconSize: Dp = 65.dp,
    text: StringResource,
    fontWeight: FontWeight = FontWeight.Bold,
    textStyle: TextStyle = LocalTextStyle.current.merge(
        color = contentColor,
        fontWeight = fontWeight
    ),
    onClick: () -> Unit,
) {
    DashedTile(
        modifier = modifier,
        strokeWidth = strokeWidth,
        intervals = intervals,
        phase = phase,
        size = size,
        cornerRadius = cornerRadius,
        containerColor = containerColor,
        contentColor = contentColor,
        elevation = elevation,
        icon = icon,
        iconSize = iconSize,
        text = stringResource(text),
        fontWeight = fontWeight,
        textStyle = textStyle,
        onClick = onClick
    )
}

/**
 * Tile component useful to execute action when clicked
 *
 * @param modifier The modifier to apply to the container [Card]
 * @param strokeWidth The stroke width to apply as dashed effect
 * @param intervals The number of the interval from each part of the line
 * @param phase The pixel offset for the intervals
 * @param size The size of the tile
 * @param cornerRadius The radius of the tile
 * @param containerColor The colors scheme to apply to the tile
 * @param contentColor The color of the content, icon and the text
 * @param elevation The elevation of the tile
 * @param icon The representative icon
 * @param iconSize The size of the [icon]
 * @param text The representative text
 * @param fontWeight The weight to apply to the [text]
 * @param textStyle The style to apply to the [text]
 * @param onClick The action to execute when the tile has been clicked
 */
@Composable
@NonRestartableComposable
fun DashedTile(
    modifier: Modifier = Modifier,
    strokeWidth: Float = 5f,
    intervals: FloatArray = floatArrayOf(10f, 10f),
    phase: Float = 0f,
    size: Dp = 115.dp,
    cornerRadius: Dp = 15.dp,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = contentColorFor(containerColor),
    elevation: Dp = 0.dp,
    icon: ImageVector,
    iconSize: Dp = 65.dp,
    text: String,
    fontWeight: FontWeight = FontWeight.Bold,
    textStyle: TextStyle = LocalTextStyle.current.merge(
        color = contentColor,
        fontWeight = fontWeight
    ),
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(size)
            .aspectRatio(1f)
            .dashedBorder(
                strokeWidth = strokeWidth,
                containerColor = containerColor,
                intervals = intervals,
                phase = phase,
                cornerRadius = cornerRadius
            ),
        contentAlignment = Alignment.Center
    ) {
        Tile(
            modifier = modifier
                .fillMaxSize(),
            size = size,
            containerColor = Color.Transparent,
            elevation = elevation,
            contentColor = containerColor,
            icon = icon,
            iconSize = iconSize,
            text = text,
            textStyle = textStyle,
            onClick = onClick
        )
    }
}

/**
 * Modifier method used to apply dashed borders to the [DashedTile] component
 *
 * @param strokeWidth The stroke width to apply as dashed effect
 * @param intervals The number of the interval from each part of the line
 * @param phase The pixel offset for the intervals
 * @param cornerRadius The radius of the tile
 * @param containerColor The colors scheme to apply to the tile
 *
 * @return the modifier to apply to the component as [Modifier]
 */
private fun Modifier.dashedBorder(
    strokeWidth: Float,
    containerColor: Color,
    intervals: FloatArray,
    phase: Float,
    cornerRadius: Dp,
) : Modifier = drawWithContent {
        drawContent()
        val halfStroke = strokeWidth / 2
        drawRoundRect(
            color = containerColor,
            style = Stroke(
                width = strokeWidth,
                pathEffect = PathEffect.dashPathEffect(intervals, phase)
            ),
            cornerRadius = CornerRadius(cornerRadius.toPx()),
            topLeft = Offset(
                x = halfStroke,
                y = halfStroke
            ),
            size = Size(
                width = this.size.width - strokeWidth,
                height = this.size.height - strokeWidth
            )
        )
    }

/**
 * Tile component useful to execute action when clicked
 *
 * @param modifier The modifier to apply to the container [Card]
 * @param size The size of the tile
 * @param shape The shape of the tile
 * @param containerColor The colors scheme to apply to the tile
 * @param contentColor The color of the content, icon and the text
 * @param elevation The elevation of the tile
 * @param icon The representative icon
 * @param iconSize The size of the [icon]
 * @param text The representative text
 * @param fontWeight The weight to apply to the [text]
 * @param textStyle The style to apply to the [text]
 * @param onClick The action to execute when the tile has been clicked
 */
@Composable
fun Tile(
    modifier: Modifier = Modifier,
    size: Dp = 115.dp,
    shape: Shape = RoundedCornerShape(
        size = 15.dp
    ),
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = contentColorFor(containerColor),
    elevation: Dp = 3.dp,
    icon: ImageVector,
    iconSize: Dp = 65.dp,
    text: StringResource,
    fontWeight: FontWeight = FontWeight.Bold,
    textStyle: TextStyle = LocalTextStyle.current.merge(
        color = contentColor,
        fontWeight = fontWeight
    ),
    onClick: () -> Unit,
) {
    Tile(
        modifier = modifier,
        size = size,
        shape = shape,
        containerColor = containerColor,
        contentColor = contentColor,
        icon = icon,
        iconSize = iconSize,
        text = stringResource(text),
        textStyle = textStyle,
        elevation = elevation,
        onClick = onClick
    )
}

/**
 * Tile component useful to execute action when clicked
 *
 * @param modifier The modifier to apply to the container [Card]
 * @param size The size of the tile
 * @param shape The shape of the tile
 * @param containerColor The colors scheme to apply to the tile
 * @param contentColor The color of the content, icon and the text
 * @param elevation The elevation of the tile
 * @param icon The representative icon
 * @param iconSize The size of the [icon]
 * @param text The representative text
 * @param fontWeight The weight to apply to the [text]
 * @param textStyle The style to apply to the [text]
 * @param onClick The action to execute when the tile has been clicked
 */
@Composable
fun Tile(
    modifier: Modifier = Modifier,
    size: Dp = 115.dp,
    shape: Shape = RoundedCornerShape(
        size = 15.dp
    ),
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = contentColorFor(containerColor),
    elevation: Dp = 3.dp,
    icon: ImageVector,
    iconSize: Dp = 65.dp,
    text: String,
    fontWeight: FontWeight = FontWeight.Bold,
    textStyle: TextStyle = LocalTextStyle.current.merge(
        color = contentColor,
        fontWeight = fontWeight
    ),
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .size(size)
            .aspectRatio(1f),
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = elevation
        ),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(iconSize)
                    .background(Color.Transparent),
                imageVector = icon,
                contentDescription = text,
                tint = contentColor
            )
            Text(
                text = text,
                style = textStyle.merge(
                    color = contentColor,
                    fontWeight = fontWeight
                )
            )
        }
    }
}