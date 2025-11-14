package com.tecknobit.equinoxcompose.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.tecknobit.equinoxcompose.components.TileStyle.DASHED
import com.tecknobit.equinoxcompose.components.TileStyle.DEFAULT
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/**
 * The supported styles for the [Tile] component
 *
 * @since 1.1.8
 */
@ExperimentalComposeApi
enum class TileStyle {

    /**
     * `DEFAULT` The default style to draw a tile for general purposes
     */
    DEFAULT,

    /**
     * `DASHED` The dashed style to draw a dashed-borders tile, for example to represent an unavailable action etc...
     */
    DASHED

}

/**
 * Dynamic tile component useful to execute action when clicked and dynamically change its style to best represent
 * the action or the subject that the tile represents.
 *
 * The parameters will be applied with the criteria of the tile to represent
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
 * @param applyStyle The logic to apply the style to the tile
 * @param onClick The action to execute when the tile has been clicked
 *
 * @since 1.1.8
 */
@Composable
@ExperimentalComposeApi
fun DynamicTile(
    modifier: Modifier = Modifier,
    strokeWidth: Float = 5f,
    intervals: FloatArray = floatArrayOf(10f, 10f),
    phase: Float = 0f,
    size: Dp = 115.dp,
    cornerRadius: Dp = 15.dp,
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
    applyStyle: () -> TileStyle,
    onClick: (TileStyle) -> Unit,
) {
    DynamicTile(
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
        applyStyle = applyStyle,
        onClick = onClick
    )
}

/**
 * Dynamic tile component useful to execute action when clicked and dynamically change its style to best represent
 * the action or the subject that the tile represents.
 *
 * The parameters will be applied with the criteria of the tile to represent
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
 * @param applyStyle The logic to apply the style to the tile
 * @param onClick The action to execute when the tile has been clicked
 *
 * @since 1.1.8
 */
@Composable
@ExperimentalComposeApi
fun DynamicTile(
    modifier: Modifier = Modifier,
    strokeWidth: Float = 5f,
    intervals: FloatArray = floatArrayOf(10f, 10f),
    phase: Float = 0f,
    size: Dp = 115.dp,
    cornerRadius: Dp = 15.dp,
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
    applyStyle: () -> TileStyle,
    onClick: (TileStyle) -> Unit,
) {
    var style by rememberSaveable { mutableStateOf(applyStyle()) }
    val onClickAction: (TileStyle) -> Unit = { currentStyle ->
        onClick(currentStyle)
        style = applyStyle()
    }
    AnimatedContent(
        targetState = style
    ) { currentStyle ->
        when (currentStyle) {
            DEFAULT -> {
                Tile(
                    modifier = modifier,
                    size = size,
                    shape = RoundedCornerShape(
                        size = cornerRadius
                    ),
                    containerColor = containerColor,
                    contentColor = contentColor,
                    icon = icon,
                    iconSize = iconSize,
                    text = text,
                    textStyle = textStyle,
                    elevation = elevation,
                    onClick = { onClickAction(currentStyle) }
                )
            }

            DASHED -> {
                DashedTile(
                    modifier = modifier,
                    strokeWidth = strokeWidth,
                    intervals = intervals,
                    phase = phase,
                    size = size,
                    cornerRadius = cornerRadius,
                    containerColor = containerColor,
                    contentColor = contentColor,
                    icon = icon,
                    iconSize = iconSize,
                    text = text,
                    fontWeight = fontWeight,
                    textStyle = textStyle,
                    onClick = { onClickAction(currentStyle) }
                )
            }
        }
    }
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
            elevation = 0.dp,
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