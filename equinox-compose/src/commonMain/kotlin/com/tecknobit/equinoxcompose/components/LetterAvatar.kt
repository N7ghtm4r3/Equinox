package com.tecknobit.equinoxcompose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
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

@Composable
@ExperimentalMaterial3Api
fun LetterAvatar(
    modifier: Modifier = Modifier,
    size: Dp,
    shape: Shape = CircleShape,
    elevation: Dp = 0.dp,
    uppercaseFormat: Boolean = true,
    name: String,
    backgroundColor: Color = resolveAvatarColor(
        name = name
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

@Returner
fun resolveAvatarColor(
    name: String,
): Color {
    val hashCode = name.hashCode()
    return Color(
        red = hashCode shr 0 and 0xFF,
        green = hashCode shr 8 and 0xFF,
        blue = hashCode shr 16 and 0xFF
    )
}

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