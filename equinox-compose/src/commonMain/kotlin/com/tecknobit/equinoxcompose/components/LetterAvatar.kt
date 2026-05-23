package com.tecknobit.equinoxcompose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecknobit.equinoxcore.annotations.Returner

/**
 * Component allows to display the letters of a provided [name] in the format: Name Second-name Third-name (NST)
 *
 * Intended to be used as a fallback placeholder when the user does not
 * have a profile picture or when a network error occurs
 *
 * @param modifier The modifier to apply to the component
 * @param size The size the avatar must have
 * @param shape The shape to apply to the component
 * @param elevation The elevation to apply to the component
 * @param uppercaseFormat Whether the letters must be displayed in uppercase format or displayed as provided
 * @param name The name of the avatar
 * @param defaultBackgroundColor The default color when the [name] is empty
 * @param backgroundColor The color of the background, default color is resolved with [resolveAvatarColor] method
 * @param style The style of the displayed letters
 * @param onClick The callback action to invoke when the user click the component
 *
 * @since 1.1.9
 */
@Composable
@ExperimentalMaterial3Api
fun LetterAvatar(
    modifier: Modifier = Modifier,
    size: Dp,
    shape: Shape = CircleShape,
    elevation: Dp = 0.dp,
    uppercaseFormat: Boolean = true,
    name: String,
    defaultBackgroundColor: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = resolveAvatarColor(
        name = name,
        defaultBackgroundColor = defaultBackgroundColor
    ),
    style: TextStyle = LocalTextStyle.current,
    onClick: (() -> Unit)? = null,
) {
    val letters = rememberSaveable(name) {
        resolveInitialLetters(
            name = name,
            uppercaseFormat = uppercaseFormat
        )
    }

    Box(
        modifier = modifier
            .size(size)
            .shadow(
                elevation = elevation,
                shape = shape
            )
            .clip(shape)
            .background(
                color = backgroundColor,
                shape = shape
            )
            .clickable(
                enabled = onClick != null,
                onClick = {
                    onClick?.invoke()
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = letters,
            style = style.copy(
                fontSize = (size.value * 0.3f).sp,
                color = getContrastColor(
                    backgroundColor = backgroundColor
                )
            ),
            maxLines = 1,
            overflow = TextOverflow.MiddleEllipsis
        )
    }
}

/**
 * Method used to resolve the letters to display on the [LetterAvatar] component based on the provided [name]
 *
 * @param name The name of the avatar
 * @param uppercaseFormat Whether the letters must be displayed in uppercase format or displayed as provided
 *
 * @return the letters to display as [String]
 *
 * @since 1.1.9
 */
@Returner
private fun resolveInitialLetters(
    name: String,
    uppercaseFormat: Boolean,
): String {
    var initialLetters = ""
    name.split(" ").forEach { namePart ->
        if (namePart.isNotBlank())
            initialLetters += namePart.first()
    }

    return if (uppercaseFormat)
        initialLetters.uppercase()
    else
        initialLetters
}

/**
 * Method used to resolve the background color for the [LetterAvatar] component
 *
 * @param name The name of the avatar
 * @param defaultBackgroundColor The default color when the [name] is empty
 *
 * @return the background color as [Color]
 *
 * @since 1.1.9
 */
@Returner
fun resolveAvatarColor(
    name: String,
    defaultBackgroundColor: Color
): Color {
    if(name.isBlank())
        return defaultBackgroundColor

    val hashCode = name.hashCode()

    return Color(
        red = hashCode shr 0 and 0xFF,
        green = hashCode shr 8 and 0xFF,
        blue = hashCode shr 16 and 0xFF
    )
}