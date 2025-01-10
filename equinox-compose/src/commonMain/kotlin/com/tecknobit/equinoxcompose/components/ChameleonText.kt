package com.tecknobit.equinoxcompose.components

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.Paragraph
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.tecknobit.equinoxcompose.utilities.toColor
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/**
 * *RED_PERCEPTION* -> the ratio of the red perception from the color spectrum
 */
private const val RED_PERCEPTION = 0.299

/**
 * *GREEN_PERCEPTION* -> the ratio of the green perception from the color spectrum
 */
private const val GREEN_PERCEPTION = 0.587

/**
 * *BLUE_PERCEPTION* -> the ratio of the blue perception from the color spectrum
 */
private const val BLUE_PERCEPTION = 0.114

// FIXME: WILL BE INTEGRATED WHEN https://github.com/jordond/kmpalette/discussions/198 WILL BE SOLVED
///**
// * Customization of the [Text] component to change the color of the [text] displayed dynamically based on the background
// * color where the text is
// *
// * The dominant color from the [backgroundPainter] is fetched with the usage under the hood by the
// * [kmpalette](https://github.com/jordond/kmpalette) library, it is marked as [ExperimentalComposeApi] due an
// * [issue](https://github.com/jordond/kmpalette/issues/197) during its implementation
// *
// * @param text the resource identifier of the text to be displayed
// * @param modifier the [Modifier] to be applied to this layout node
// * @param fontSize the size of glyphs to use when painting the text. See [TextStyle.fontSize].
// * @param fontStyle the typeface variant to use when drawing the letters (e.g., italic). See
// *   [TextStyle.fontStyle].
// * @param fontWeight the typeface thickness to use when painting the text (e.g., [FontWeight.Bold]).
// * @param fontFamily the font family to be used when rendering the text. See [TextStyle.fontFamily].
// * @param letterSpacing the amount of space to add between each letter. See
// *   [TextStyle.letterSpacing].
// * @param textDecoration the decorations to paint on the text (e.g., an underline). See
// *   [TextStyle.textDecoration].
// * @param textAlign the alignment of the text within the lines of the paragraph. See
// *   [TextStyle.textAlign].
// * @param lineHeight line height for the [Paragraph] in [TextUnit] unit, e.g. SP or EM. See
// *   [TextStyle.lineHeight].
// * @param overflow how visual overflow should be handled.
// * @param softWrap whether the text should break at soft line breaks. If false, the glyphs in the
// *   text will be positioned as if there was unlimited horizontal space. If [softWrap] is false,
// *   [overflow] and TextAlign may have unexpected effects.
// * @param maxLines An optional maximum number of lines for the text to span, wrapping if necessary.
// *   If the text exceeds the given number of lines, it will be truncated according to [overflow] and
// *   [softWrap]. It is required that 1 <= [minLines] <= [maxLines].
// * @param minLines The minimum height in terms of minimum number of visible lines. It is required
// *   that 1 <= [minLines] <= [maxLines].
// * @param onTextLayout callback that is executed when a new text layout is calculated. A
// *   [TextLayoutResult] object that callback provides contains paragraph information, size of the
// *   text, baselines and other details. The callback can be used to add additional decoration or
// *   functionality to the text. For example, to draw selection around the text.
// * @param style style configuration for the text such as color, font, line height etc.
// * @param backgroundPainter The painter used as background for a text
// */
//@Composable
//@NonRestartableComposable
//@ExperimentalComposeApi
//fun ChameleonText(
//    text: StringResource,
//    modifier: Modifier = Modifier,
//    fontSize: TextUnit = TextUnit.Unspecified,
//    fontStyle: FontStyle? = null,
//    fontWeight: FontWeight? = null,
//    fontFamily: FontFamily? = null,
//    letterSpacing: TextUnit = TextUnit.Unspecified,
//    textDecoration: TextDecoration? = null,
//    textAlign: TextAlign? = null,
//    lineHeight: TextUnit = TextUnit.Unspecified,
//    overflow: TextOverflow = TextOverflow.Clip,
//    softWrap: Boolean = true,
//    maxLines: Int = Int.MAX_VALUE,
//    minLines: Int = 1,
//    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
//    style: TextStyle = LocalTextStyle.current,
//    backgroundPainter: Painter,
//) {
//    ChameleonText(
//        text = stringResource(text),
//        modifier = modifier,
//        fontSize = fontSize,
//        fontStyle = fontStyle,
//        fontWeight = fontWeight,
//        fontFamily = fontFamily,
//        letterSpacing = letterSpacing,
//        textDecoration = textDecoration,
//        textAlign = textAlign,
//        lineHeight = lineHeight,
//        overflow = overflow,
//        softWrap = softWrap,
//        maxLines = maxLines,
//        minLines = minLines,
//        onTextLayout = onTextLayout,
//        style = style,
//        backgroundPainter = backgroundPainter
//    )
//}
// FIXME: WILL BE INTEGRATED WHEN https://github.com/jordond/kmpalette/discussions/198 WILL BE SOLVED
///**
// * Customization of the [Text] component to change the color of the [text] displayed dynamically based on the background
// * color where the text is
// *
// * The dominant color from the [backgroundPainter] is fetched with the usage under the hood by the
// * [kmpalette](https://github.com/jordond/kmpalette) library, it is marked as [ExperimentalComposeApi] due an
// * [issue](https://github.com/jordond/kmpalette/issues/197) during its implementation
// *
// * @param text the text to be displayed
// * @param modifier the [Modifier] to be applied to this layout node
// * @param fontSize the size of glyphs to use when painting the text. See [TextStyle.fontSize].
// * @param fontStyle the typeface variant to use when drawing the letters (e.g., italic). See
// *   [TextStyle.fontStyle].
// * @param fontWeight the typeface thickness to use when painting the text (e.g., [FontWeight.Bold]).
// * @param fontFamily the font family to be used when rendering the text. See [TextStyle.fontFamily].
// * @param letterSpacing the amount of space to add between each letter. See
// *   [TextStyle.letterSpacing].
// * @param textDecoration the decorations to paint on the text (e.g., an underline). See
// *   [TextStyle.textDecoration].
// * @param textAlign the alignment of the text within the lines of the paragraph. See
// *   [TextStyle.textAlign].
// * @param lineHeight line height for the [Paragraph] in [TextUnit] unit, e.g. SP or EM. See
// *   [TextStyle.lineHeight].
// * @param overflow how visual overflow should be handled.
// * @param softWrap whether the text should break at soft line breaks. If false, the glyphs in the
// *   text will be positioned as if there was unlimited horizontal space. If [softWrap] is false,
// *   [overflow] and TextAlign may have unexpected effects.
// * @param maxLines An optional maximum number of lines for the text to span, wrapping if necessary.
// *   If the text exceeds the given number of lines, it will be truncated according to [overflow] and
// *   [softWrap]. It is required that 1 <= [minLines] <= [maxLines].
// * @param minLines The minimum height in terms of minimum number of visible lines. It is required
// *   that 1 <= [minLines] <= [maxLines].
// * @param onTextLayout callback that is executed when a new text layout is calculated. A
// *   [TextLayoutResult] object that callback provides contains paragraph information, size of the
// *   text, baselines and other details. The callback can be used to add additional decoration or
// *   functionality to the text. For example, to draw selection around the text.
// * @param style style configuration for the text such as color, font, line height etc.
// * @param backgroundPainter The painter used as background for a text
// */
//@Composable
//@NonRestartableComposable
//@ExperimentalComposeApi
//fun ChameleonText(
//    text: String,
//    modifier: Modifier = Modifier,
//    fontSize: TextUnit = TextUnit.Unspecified,
//    fontStyle: FontStyle? = null,
//    fontWeight: FontWeight? = null,
//    fontFamily: FontFamily? = null,
//    letterSpacing: TextUnit = TextUnit.Unspecified,
//    textDecoration: TextDecoration? = null,
//    textAlign: TextAlign? = null,
//    lineHeight: TextUnit = TextUnit.Unspecified,
//    overflow: TextOverflow = TextOverflow.Clip,
//    softWrap: Boolean = true,
//    maxLines: Int = Int.MAX_VALUE,
//    minLines: Int = 1,
//    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
//    style: TextStyle = LocalTextStyle.current,
//    backgroundPainter: Painter,
//) {
//    val loader = rememberPainterLoader()
//    val dominantColorState = rememberDominantColorState(
//        loader = loader
//    )
//    LaunchedEffect(backgroundPainter) {
//        dominantColorState.updateFrom(backgroundPainter)
//    }
//    ChameleonText(
//        text = text,
//        modifier = modifier,
//        fontSize = fontSize,
//        fontStyle = fontStyle,
//        fontWeight = fontWeight,
//        fontFamily = fontFamily,
//        letterSpacing = letterSpacing,
//        textDecoration = textDecoration,
//        textAlign = textAlign,
//        lineHeight = lineHeight,
//        overflow = overflow,
//        softWrap = softWrap,
//        maxLines = maxLines,
//        minLines = minLines,
//        onTextLayout = onTextLayout,
//        style = style,
//        backgroundColor = dominantColorState.color
//    )
//}
// FIXME: WILL BE INTEGRATED WHEN https://github.com/jordond/kmpalette/discussions/198 WILL BE SOLVED
///**
// * Customization of the [Text] component to change the color of the [text] displayed dynamically based on the background
// * color where the text is
// *
// * The dominant color from the [backgroundImage] is fetched with the usage under the hood by the
// * [kmpalette](https://github.com/jordond/kmpalette) library
// *
// * @param text the resource identifier of the text to be displayed
// * @param modifier the [Modifier] to be applied to this layout node
// * @param fontSize the size of glyphs to use when painting the text. See [TextStyle.fontSize].
// * @param fontStyle the typeface variant to use when drawing the letters (e.g., italic). See
// *   [TextStyle.fontStyle].
// * @param fontWeight the typeface thickness to use when painting the text (e.g., [FontWeight.Bold]).
// * @param fontFamily the font family to be used when rendering the text. See [TextStyle.fontFamily].
// * @param letterSpacing the amount of space to add between each letter. See
// *   [TextStyle.letterSpacing].
// * @param textDecoration the decorations to paint on the text (e.g., an underline). See
// *   [TextStyle.textDecoration].
// * @param textAlign the alignment of the text within the lines of the paragraph. See
// *   [TextStyle.textAlign].
// * @param lineHeight line height for the [Paragraph] in [TextUnit] unit, e.g. SP or EM. See
// *   [TextStyle.lineHeight].
// * @param overflow how visual overflow should be handled.
// * @param softWrap whether the text should break at soft line breaks. If false, the glyphs in the
// *   text will be positioned as if there was unlimited horizontal space. If [softWrap] is false,
// *   [overflow] and TextAlign may have unexpected effects.
// * @param maxLines An optional maximum number of lines for the text to span, wrapping if necessary.
// *   If the text exceeds the given number of lines, it will be truncated according to [overflow] and
// *   [softWrap]. It is required that 1 <= [minLines] <= [maxLines].
// * @param minLines The minimum height in terms of minimum number of visible lines. It is required
// *   that 1 <= [minLines] <= [maxLines].
// * @param onTextLayout callback that is executed when a new text layout is calculated. A
// *   [TextLayoutResult] object that callback provides contains paragraph information, size of the
// *   text, baselines and other details. The callback can be used to add additional decoration or
// *   functionality to the text. For example, to draw selection around the text.
// * @param style style configuration for the text such as color, font, line height etc.
// * @param backgroundImage The image used as background for a text
// */
//@Composable
//@NonRestartableComposable
//fun ChameleonText(
//    text: StringResource,
//    modifier: Modifier = Modifier,
//    fontSize: TextUnit = TextUnit.Unspecified,
//    fontStyle: FontStyle? = null,
//    fontWeight: FontWeight? = null,
//    fontFamily: FontFamily? = null,
//    letterSpacing: TextUnit = TextUnit.Unspecified,
//    textDecoration: TextDecoration? = null,
//    textAlign: TextAlign? = null,
//    lineHeight: TextUnit = TextUnit.Unspecified,
//    overflow: TextOverflow = TextOverflow.Clip,
//    softWrap: Boolean = true,
//    maxLines: Int = Int.MAX_VALUE,
//    minLines: Int = 1,
//    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
//    style: TextStyle = LocalTextStyle.current,
//    backgroundImage: ImageBitmap?,
//) {
//    ChameleonText(
//        text = stringResource(text),
//        modifier = modifier,
//        fontSize = fontSize,
//        fontStyle = fontStyle,
//        fontWeight = fontWeight,
//        fontFamily = fontFamily,
//        letterSpacing = letterSpacing,
//        textDecoration = textDecoration,
//        textAlign = textAlign,
//        lineHeight = lineHeight,
//        overflow = overflow,
//        softWrap = softWrap,
//        maxLines = maxLines,
//        minLines = minLines,
//        onTextLayout = onTextLayout,
//        style = style,
//        backgroundImage = backgroundImage
//    )
//}
// FIXME: WILL BE INTEGRATED WHEN https://github.com/jordond/kmpalette/discussions/198 WILL BE SOLVED
///**
// * Customization of the [Text] component to change the color of the [text] displayed dynamically based on the background
// * color where the text is
// *
// * The dominant color from the [backgroundImage] is fetched with the usage under the hood by the
// * [kmpalette](https://github.com/jordond/kmpalette) library
// *
// * @param text the text to be displayed
// * @param modifier the [Modifier] to be applied to this layout node
// * @param fontSize the size of glyphs to use when painting the text. See [TextStyle.fontSize].
// * @param fontStyle the typeface variant to use when drawing the letters (e.g., italic). See
// *   [TextStyle.fontStyle].
// * @param fontWeight the typeface thickness to use when painting the text (e.g., [FontWeight.Bold]).
// * @param fontFamily the font family to be used when rendering the text. See [TextStyle.fontFamily].
// * @param letterSpacing the amount of space to add between each letter. See
// *   [TextStyle.letterSpacing].
// * @param textDecoration the decorations to paint on the text (e.g., an underline). See
// *   [TextStyle.textDecoration].
// * @param textAlign the alignment of the text within the lines of the paragraph. See
// *   [TextStyle.textAlign].
// * @param lineHeight line height for the [Paragraph] in [TextUnit] unit, e.g. SP or EM. See
// *   [TextStyle.lineHeight].
// * @param overflow how visual overflow should be handled.
// * @param softWrap whether the text should break at soft line breaks. If false, the glyphs in the
// *   text will be positioned as if there was unlimited horizontal space. If [softWrap] is false,
// *   [overflow] and TextAlign may have unexpected effects.
// * @param maxLines An optional maximum number of lines for the text to span, wrapping if necessary.
// *   If the text exceeds the given number of lines, it will be truncated according to [overflow] and
// *   [softWrap]. It is required that 1 <= [minLines] <= [maxLines].
// * @param minLines The minimum height in terms of minimum number of visible lines. It is required
// *   that 1 <= [minLines] <= [maxLines].
// * @param onTextLayout callback that is executed when a new text layout is calculated. A
// *   [TextLayoutResult] object that callback provides contains paragraph information, size of the
// *   text, baselines and other details. The callback can be used to add additional decoration or
// *   functionality to the text. For example, to draw selection around the text.
// * @param style style configuration for the text such as color, font, line height etc.
// * @param backgroundImage The image used as background for a text
// */
//@Composable
//@NonRestartableComposable
//fun ChameleonText(
//    text: String,
//    modifier: Modifier = Modifier,
//    fontSize: TextUnit = TextUnit.Unspecified,
//    fontStyle: FontStyle? = null,
//    fontWeight: FontWeight? = null,
//    fontFamily: FontFamily? = null,
//    letterSpacing: TextUnit = TextUnit.Unspecified,
//    textDecoration: TextDecoration? = null,
//    textAlign: TextAlign? = null,
//    lineHeight: TextUnit = TextUnit.Unspecified,
//    overflow: TextOverflow = TextOverflow.Clip,
//    softWrap: Boolean = true,
//    maxLines: Int = Int.MAX_VALUE,
//    minLines: Int = 1,
//    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
//    style: TextStyle = LocalTextStyle.current,
//    backgroundImage: ImageBitmap?,
//) {
//    val dominantColorState = rememberDominantColorState()
//    val imageLoaded = remember { mutableStateOf(backgroundImage != null) }
//    LaunchedEffect(backgroundImage) {
//        imageLoaded.value = backgroundImage != null
//        if (imageLoaded.value)
//            dominantColorState.updateFrom(backgroundImage!!)
//    }
//    if (imageLoaded.value) {
//        ChameleonText(
//            text = text,
//            modifier = modifier,
//            fontSize = fontSize,
//            fontStyle = fontStyle,
//            fontWeight = fontWeight,
//            fontFamily = fontFamily,
//            letterSpacing = letterSpacing,
//            textDecoration = textDecoration,
//            textAlign = textAlign,
//            lineHeight = lineHeight,
//            overflow = overflow,
//            softWrap = softWrap,
//            maxLines = maxLines,
//            minLines = minLines,
//            onTextLayout = onTextLayout,
//            style = style,
//            backgroundColor = dominantColorState.color
//        )
//    }
//}

/**
 * Customization of the [Text] component to change the color of the [text] displayed dynamically based on the background
 * color where the text is
 * @param text the resource identifier of the text to be displayed
 * @param modifier the [Modifier] to be applied to this layout node
 * @param fontSize the size of glyphs to use when painting the text. See [TextStyle.fontSize].
 * @param fontStyle the typeface variant to use when drawing the letters (e.g., italic). See
 *   [TextStyle.fontStyle].
 * @param fontWeight the typeface thickness to use when painting the text (e.g., [FontWeight.Bold]).
 * @param fontFamily the font family to be used when rendering the text. See [TextStyle.fontFamily].
 * @param letterSpacing the amount of space to add between each letter. See
 *   [TextStyle.letterSpacing].
 * @param textDecoration the decorations to paint on the text (e.g., an underline). See
 *   [TextStyle.textDecoration].
 * @param textAlign the alignment of the text within the lines of the paragraph. See
 *   [TextStyle.textAlign].
 * @param lineHeight line height for the [Paragraph] in [TextUnit] unit, e.g. SP or EM. See
 *   [TextStyle.lineHeight].
 * @param overflow how visual overflow should be handled.
 * @param softWrap whether the text should break at soft line breaks. If false, the glyphs in the
 *   text will be positioned as if there was unlimited horizontal space. If [softWrap] is false,
 *   [overflow] and TextAlign may have unexpected effects.
 * @param maxLines An optional maximum number of lines for the text to span, wrapping if necessary.
 *   If the text exceeds the given number of lines, it will be truncated according to [overflow] and
 *   [softWrap]. It is required that 1 <= [minLines] <= [maxLines].
 * @param minLines The minimum height in terms of minimum number of visible lines. It is required
 *   that 1 <= [minLines] <= [maxLines].
 * @param onTextLayout callback that is executed when a new text layout is calculated. A
 *   [TextLayoutResult] object that callback provides contains paragraph information, size of the
 *   text, baselines and other details. The callback can be used to add additional decoration or
 *   functionality to the text. For example, to draw selection around the text.
 * @param style style configuration for the text such as color, font, line height etc.
 * @param hexBackgroundColor The background color, in the hexadecimal format, where the text is displayed
 */
@Composable
@NonRestartableComposable
fun ChameleonText(
    text: StringResource,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    style: TextStyle = LocalTextStyle.current,
    hexBackgroundColor: String,
) {
    ChameleonText(
        text = stringResource(text),
        modifier = modifier,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = onTextLayout,
        style = style,
        hexBackgroundColor = hexBackgroundColor
    )
}

/**
 * Customization of the [Text] component to change the color of the [text] displayed dynamically based on the background
 * color where the text is
 * @param text the text to be displayed
 * @param modifier the [Modifier] to be applied to this layout node
 * @param fontSize the size of glyphs to use when painting the text. See [TextStyle.fontSize].
 * @param fontStyle the typeface variant to use when drawing the letters (e.g., italic). See
 *   [TextStyle.fontStyle].
 * @param fontWeight the typeface thickness to use when painting the text (e.g., [FontWeight.Bold]).
 * @param fontFamily the font family to be used when rendering the text. See [TextStyle.fontFamily].
 * @param letterSpacing the amount of space to add between each letter. See
 *   [TextStyle.letterSpacing].
 * @param textDecoration the decorations to paint on the text (e.g., an underline). See
 *   [TextStyle.textDecoration].
 * @param textAlign the alignment of the text within the lines of the paragraph. See
 *   [TextStyle.textAlign].
 * @param lineHeight line height for the [Paragraph] in [TextUnit] unit, e.g. SP or EM. See
 *   [TextStyle.lineHeight].
 * @param overflow how visual overflow should be handled.
 * @param softWrap whether the text should break at soft line breaks. If false, the glyphs in the
 *   text will be positioned as if there was unlimited horizontal space. If [softWrap] is false,
 *   [overflow] and TextAlign may have unexpected effects.
 * @param maxLines An optional maximum number of lines for the text to span, wrapping if necessary.
 *   If the text exceeds the given number of lines, it will be truncated according to [overflow] and
 *   [softWrap]. It is required that 1 <= [minLines] <= [maxLines].
 * @param minLines The minimum height in terms of minimum number of visible lines. It is required
 *   that 1 <= [minLines] <= [maxLines].
 * @param onTextLayout callback that is executed when a new text layout is calculated. A
 *   [TextLayoutResult] object that callback provides contains paragraph information, size of the
 *   text, baselines and other details. The callback can be used to add additional decoration or
 *   functionality to the text. For example, to draw selection around the text.
 * @param style style configuration for the text such as color, font, line height etc.
 * @param hexBackgroundColor The background color, in the hexadecimal format, where the text is displayed
 */
@Composable
@NonRestartableComposable
fun ChameleonText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    style: TextStyle = LocalTextStyle.current,
    hexBackgroundColor: String,
) {
    ChameleonText(
        text = text,
        modifier = modifier,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = onTextLayout,
        style = style,
        backgroundColor = hexBackgroundColor.toColor()
    )
}

/**
 * Customization of the [Text] component to change the color of the [text] displayed dynamically based on the background
 * color where the text is
 * @param text the resource identifier of the text to be displayed
 * @param modifier the [Modifier] to be applied to this layout node
 * @param fontSize the size of glyphs to use when painting the text. See [TextStyle.fontSize].
 * @param fontStyle the typeface variant to use when drawing the letters (e.g., italic). See
 *   [TextStyle.fontStyle].
 * @param fontWeight the typeface thickness to use when painting the text (e.g., [FontWeight.Bold]).
 * @param fontFamily the font family to be used when rendering the text. See [TextStyle.fontFamily].
 * @param letterSpacing the amount of space to add between each letter. See
 *   [TextStyle.letterSpacing].
 * @param textDecoration the decorations to paint on the text (e.g., an underline). See
 *   [TextStyle.textDecoration].
 * @param textAlign the alignment of the text within the lines of the paragraph. See
 *   [TextStyle.textAlign].
 * @param lineHeight line height for the [Paragraph] in [TextUnit] unit, e.g. SP or EM. See
 *   [TextStyle.lineHeight].
 * @param overflow how visual overflow should be handled.
 * @param softWrap whether the text should break at soft line breaks. If false, the glyphs in the
 *   text will be positioned as if there was unlimited horizontal space. If [softWrap] is false,
 *   [overflow] and TextAlign may have unexpected effects.
 * @param maxLines An optional maximum number of lines for the text to span, wrapping if necessary.
 *   If the text exceeds the given number of lines, it will be truncated according to [overflow] and
 *   [softWrap]. It is required that 1 <= [minLines] <= [maxLines].
 * @param minLines The minimum height in terms of minimum number of visible lines. It is required
 *   that 1 <= [minLines] <= [maxLines].
 * @param onTextLayout callback that is executed when a new text layout is calculated. A
 *   [TextLayoutResult] object that callback provides contains paragraph information, size of the
 *   text, baselines and other details. The callback can be used to add additional decoration or
 *   functionality to the text. For example, to draw selection around the text.
 * @param style style configuration for the text such as color, font, line height etc.
 * @param backgroundColor The background color where the text is displayed
 */
@Composable
@NonRestartableComposable
fun ChameleonText(
    text: StringResource,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    style: TextStyle = LocalTextStyle.current,
    backgroundColor: Color,
) {
    ChameleonText(
        text = stringResource(text),
        modifier = modifier,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = onTextLayout,
        style = style,
        backgroundColor = backgroundColor
    )
}

/**
 * Customization of the [Text] component to change the color of the [text] displayed dynamically based on the background
 * color where the text is
 * @param text the text to be displayed
 * @param modifier the [Modifier] to be applied to this layout node
 * @param fontSize the size of glyphs to use when painting the text. See [TextStyle.fontSize].
 * @param fontStyle the typeface variant to use when drawing the letters (e.g., italic). See
 *   [TextStyle.fontStyle].
 * @param fontWeight the typeface thickness to use when painting the text (e.g., [FontWeight.Bold]).
 * @param fontFamily the font family to be used when rendering the text. See [TextStyle.fontFamily].
 * @param letterSpacing the amount of space to add between each letter. See
 *   [TextStyle.letterSpacing].
 * @param textDecoration the decorations to paint on the text (e.g., an underline). See
 *   [TextStyle.textDecoration].
 * @param textAlign the alignment of the text within the lines of the paragraph. See
 *   [TextStyle.textAlign].
 * @param lineHeight line height for the [Paragraph] in [TextUnit] unit, e.g. SP or EM. See
 *   [TextStyle.lineHeight].
 * @param overflow how visual overflow should be handled.
 * @param softWrap whether the text should break at soft line breaks. If false, the glyphs in the
 *   text will be positioned as if there was unlimited horizontal space. If [softWrap] is false,
 *   [overflow] and TextAlign may have unexpected effects.
 * @param maxLines An optional maximum number of lines for the text to span, wrapping if necessary.
 *   If the text exceeds the given number of lines, it will be truncated according to [overflow] and
 *   [softWrap]. It is required that 1 <= [minLines] <= [maxLines].
 * @param minLines The minimum height in terms of minimum number of visible lines. It is required
 *   that 1 <= [minLines] <= [maxLines].
 * @param onTextLayout callback that is executed when a new text layout is calculated. A
 *   [TextLayoutResult] object that callback provides contains paragraph information, size of the
 *   text, baselines and other details. The callback can be used to add additional decoration or
 *   functionality to the text. For example, to draw selection around the text.
 * @param style style configuration for the text such as color, font, line height etc.
 * @param backgroundColor The background color where the text is displayed
 */
@Composable
@NonRestartableComposable
fun ChameleonText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    style: TextStyle = LocalTextStyle.current,
    backgroundColor: Color,
) {
    Text(
        text = text,
        modifier = modifier,
        color = getContrastColor(
            backgroundColor = backgroundColor
        ),
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = onTextLayout,
        style = style
    )
}

/**
 * Method to get the color to use on a specified [backgroundColor] based on its luminance
 *
 * @param backgroundColor The background color where the text is on top
 *
 * @return the correct color based on the background luminance as [Color]
 */
fun getContrastColor(
    backgroundColor: Color,
): Color {
    val redRate = RED_PERCEPTION * backgroundColor.red
    val greenRate = GREEN_PERCEPTION * backgroundColor.green
    val blueRate = BLUE_PERCEPTION * backgroundColor.blue
    val luminance = ((redRate + greenRate + blueRate) * 255)
    return if (luminance > 128)
        Color.Black
    else
        Color.White
}