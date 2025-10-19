package com.tecknobit.equinoxcompose.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.Paragraph
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

/**
 * Custom [Text] used to display an outlined badge element
 *
 * @param modifier The [Modifier] to be applied to the component
 * @param borderWidth The width of the border
 * @param shape The shape of the badge
 * @param padding The padding applied from the text to outside the badge
 * @param fontSize the size of glyphs to use when painting the text. See [TextStyle.fontSize]
 * @param fontStyle the typeface variant to use when drawing the letters (e.g., italic). See
 *   [TextStyle.fontStyle]
 * @param fontWeight the typeface thickness to use when painting the text (e.g., [FontWeight.Bold])
 * @param fontFamily the font family to be used when rendering the text. See [TextStyle.fontFamily]
 * @param letterSpacing the amount of space to add between each letter. See
 *   [TextStyle.letterSpacing]
 * @param textDecoration the decorations to paint on the text (e.g., an underline). See
 *   [TextStyle.textDecoration]
 * @param lineHeight line height for the [Paragraph] in [TextUnit] unit, e.g. SP or EM. See
 *   [TextStyle.lineHeight]
 * @param softWrap whether the text should break at soft line breaks. If false, the glyphs in the
 *   text will be positioned as if there was unlimited horizontal space
 * @param onTextLayout callback that is executed when a new text layout is calculated. A
 *   [TextLayoutResult] object that callback provides contains paragraph information, size of the
 *   text, baselines and other details. The callback can be used to add additional decoration or
 *   functionality to the text. For example, to draw selection around the text.
 * @param badgeTextStyle The style configuration for the text
 * @param color The color to apply to the component, includes the border, background and text
 * @param alpha The alpha to apply to give to the badge a translucent effect
 * @param badgeText The text displayed on the badge
 * @param onClick The callback to invoke when badge clicked
 */
@Composable
@NonRestartableComposable
@ExperimentalComposeUiApi
fun OutlinedBadgeText(
    modifier: Modifier = Modifier,
    borderWidth: Dp = 1.5.dp,
    shape: Shape = RoundedCornerShape(
        size = 8.dp
    ),
    padding: PaddingValues = PaddingValues(
        vertical = 1.dp,
        horizontal = 4.dp
    ),
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    softWrap: Boolean = true,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    badgeTextStyle: TextStyle = LocalTextStyle.current,
    color: Color,
    alpha: Float = 0.2f,
    badgeText: String,
    onClick: (() -> Unit)? = null,
) {
    Box(
        modifier = Modifier
            .width(IntrinsicSize.Min)
            .height(IntrinsicSize.Min)
            .border(
                border = BorderStroke(
                    width = borderWidth,
                    brush = SolidColor(
                        value = color
                    ),
                ),
                shape = shape
            ),
        contentAlignment = Alignment.Center
    ) {
        BadgeText(
            modifier = modifier,
            shape = shape,
            padding = padding,
            fontSize = fontSize,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            lineHeight = lineHeight,
            softWrap = softWrap,
            onTextLayout = onTextLayout,
            badgeTextStyle = badgeTextStyle,
            badgeColor = color
                .copy(
                    alpha = alpha
                ),
            badgeText = badgeText,
            textColor = color,
            onClick = onClick
        )
    }
}

/**
 * Custom [Text] used to display a badge element
 *
 * @param modifier the [Modifier] to be applied to the component
 * @param shape The shape of the badge
 * @param padding The padding applied from the text to outside the badge
 * @param elevation The elevation to apply to the badge
 * @param fontSize the size of glyphs to use when painting the text. See [TextStyle.fontSize]
 * @param fontStyle the typeface variant to use when drawing the letters (e.g., italic). See
 *   [TextStyle.fontStyle]
 * @param fontWeight the typeface thickness to use when painting the text (e.g., [FontWeight.Bold])
 * @param fontFamily the font family to be used when rendering the text. See [TextStyle.fontFamily]
 * @param letterSpacing the amount of space to add between each letter. See
 *   [TextStyle.letterSpacing]
 * @param textDecoration the decorations to paint on the text (e.g., an underline). See
 *   [TextStyle.textDecoration]
 * @param lineHeight line height for the [Paragraph] in [TextUnit] unit, e.g. SP or EM. See
 *   [TextStyle.lineHeight]
 * @param softWrap whether the text should break at soft line breaks. If false, the glyphs in the
 *   text will be positioned as if there was unlimited horizontal space
 * @param onTextLayout callback that is executed when a new text layout is calculated. A
 *   [TextLayoutResult] object that callback provides contains paragraph information, size of the
 *   text, baselines and other details. The callback can be used to add additional decoration or
 *   functionality to the text. For example, to draw selection around the text.
 * @param badgeTextStyle The style configuration for the text
 * @param badgeColor The color of the badge
 * @param badgeText The text displayed on the badge
 * @param textColor The color of the text based on the [contentColorFor] of the current [badgeColor]
 * @param onClick The callback to invoke when badge clicked
 */
@Composable
@NonRestartableComposable
fun BadgeText(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(
        size = 8.dp
    ),
    padding: PaddingValues = PaddingValues(
        vertical = 1.dp,
        horizontal = 4.dp
    ),
    elevation: Dp = 0.dp,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    softWrap: Boolean = true,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    badgeTextStyle: TextStyle = LocalTextStyle.current,
    badgeColor: Color,
    badgeText: String,
    textColor: Color = contentColorFor(badgeColor),
    onClick: (() -> Unit)? = null,
) {
    Box(
        modifier = Modifier
            .width(IntrinsicSize.Min)
            .height(IntrinsicSize.Min)
            .shadow(
                elevation = elevation,
                shape = shape
            )
    ) {
        Text(
            modifier = modifier
                .clip(shape)
                .clickable(
                    enabled = onClick != null,
                    onClick = { onClick?.invoke() }
                )
                .background(badgeColor)
                .padding(padding),
            text = badgeText,
            style = badgeTextStyle,
            color = textColor,
            fontSize = fontSize,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            lineHeight = lineHeight,
            softWrap = softWrap,
            onTextLayout = onTextLayout,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}