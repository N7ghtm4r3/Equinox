package com.tecknobit.equinoxcompose.utilities

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import com.tecknobit.equinoxcompose.utilities.BorderToColor.*
import kotlin.random.Random

/**
 * Method to generate a random color for a collection
 *
 * No-any params required
 *
 * @return random color as [Color]
 */
fun generateRandomColor(): Color {
    return Color(
        red = Random.nextFloat(),
        green = Random.nextFloat(),
        blue = Random.nextFloat(),
        alpha = 1f
    )
}

/**
 * Method to get the color from its hex code
 *
 * @return color as [Color]
 */
fun String.toColor(): Color {
    return Color(("ff" + removePrefix("#").lowercase()).toLong(16))
}

/**
 * Method to transform a [Color] value in the corresponding hex code
 *
 * No-any params required
 *
 * @return hex code of the color as [String]
 */
fun Color.toHex(): String {
    val red = (this.red * 255).toInt()
    val green = (this.green * 255).toInt()
    val blue = (this.blue * 255).toInt()
    val redHex = red.toString(16).uppercase().padStart(2, '0')
    val greenHex = green.toString(16).uppercase().padStart(2, '0')
    val blueHex = blue.toString(16).uppercase().padStart(2, '0')
    return "#$redHex$greenHex$blueHex"
}

/**
 * **BorderToColor** -> list of positions for the border to color by the [colorOneSideBorder] method
 */
enum class BorderToColor {

    /**
     * **START** -> to color the start border of the component
     */
    START,

    /**
     * **TOP** -> to color the top border of the component
     */
    TOP,

    /**
     * **END** -> to color the end border of the component
     */
    END,

    /**
     * **BOTTOM** -> to color the bottom border of the component
     */
    BOTTOM

}

/**
 * Method to draw just one side of a component.
 *
 * Take a look [here](https://stackoverflow.com/questions/76762186/how-to-add-border-on-one-side-of-the-card-using-jetpack-compose)
 * for the source of this snippet
 *
 * @param borderToColor: the border to color
 * @param width: the width of the border to color
 * @param color: the color of the border
 * @param shape: the shape of the border
 */
fun Modifier.colorOneSideBorder(
    borderToColor: BorderToColor = START,
    width: Dp,
    color: Color,
    shape: Shape = RectangleShape,
) = this
    .clip(shape)
    .drawWithContent {
        val widthPx = width.toPx()
        val halfWidthPx = widthPx / 2
        drawContent()
        drawLine(
            color = color,
            start = when (borderToColor) {
                START -> Offset(halfWidthPx, 0f)
                TOP -> Offset(0f, halfWidthPx)
                END -> Offset(size.width - halfWidthPx, 0f)
                else -> Offset(size.height, size.height - halfWidthPx)
            },
            end = when (borderToColor) {
                START -> Offset(halfWidthPx, size.height)
                TOP -> Offset(size.height, halfWidthPx)
                END -> Offset(size.width - halfWidthPx, size.height)
                else -> Offset(0f, size.height - halfWidthPx)
            },
            strokeWidth = widthPx
        )
    }