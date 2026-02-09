package com.tecknobit.equinoxcompose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times

/**
 * This component allows you to creatively display a list of avatar items. It is fully customizable, and the recommended
 * approach is to show either the user's profile picture or, if unavailable, a placeholder using the [LetterAvatar] component
 *
 * @param modifier The modifier to apply to the component
 * @param visibleAvatars The maximum number of visible avatars
 * @param showSingleAvatar Whether when the [avatars] list has just one element the component is to display
 * @param avatars The list of the avatars to display
 * @param shape The shape to apply to the [avatar] content and to the [RemainingAvatarsBadge]
 * @param size The size of the [avatar] content and to the [RemainingAvatarsBadge]
 * @param offset The horizontal offset to apply to each item to move it horizontally
 * @param remainingBadge The content used to display how many avatars remain after the [visibleAvatars] number has been exceeded
 * @param avatar The content to display the wanted avatar element
 * @param supportingContent The supporting content to describe this component or to add extra element to it
 *
 * @since 1.1.9
 */
@Composable
@ExperimentalMaterial3Api
fun <T> Avatars(
    modifier: Modifier = Modifier,
    visibleAvatars: Int,
    showSingleAvatar: Boolean = false,
    avatars: List<T>,
    shape: Shape = CircleShape,
    size: Dp = 20.dp,
    offset: Dp = 10.dp,
    remainingBadge: @Composable (Modifier, Int) -> Unit = { modifier, remainingAvatars ->
        RemainingAvatarsBadge(
            modifier = modifier,
            shape = shape,
            size = size,
            remainingAvatars = remainingAvatars
        )
    },
    avatar: @Composable (Modifier, Dp, Shape, T) -> Unit,
    supportingContent: @Composable (ColumnScope.() -> Unit)? = null,
) {
    if (!showSingleAvatar && avatars.size == 1)
        return
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Box(
            contentAlignment = Alignment.CenterStart
        ) {
            avatars.forEachIndexed { index, avatar ->
                val paddingOffset = index * offset
                if (index >= visibleAvatars) {
                    remainingBadge(
                        Modifier.padding(
                            start = paddingOffset
                        ),
                        avatars.size - visibleAvatars
                    )
                    return@Box
                }
                avatar(
                    Modifier.padding(
                        start = paddingOffset
                    ),
                    size,
                    shape,
                    avatar
                )
            }
        }
        Column(
            content = {
                supportingContent?.invoke(this)
            }
        )
    }
}

/**
 * Component which displays the remaining avatars number from the [Avatars] component
 *
 * @param modifier The modifier to apply to the component
 * @param shape The shape to apply to the badge
 * @param size The size of the badge
 * @param remainingAvatars The number of the remaining avatars
 *
 * @since 1.1.9
 */
@Composable
private fun RemainingAvatarsBadge(
    modifier: Modifier = Modifier,
    shape: Shape,
    size: Dp,
    remainingAvatars: Int,
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(shape)
            .background(MaterialTheme.colorScheme.tertiary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "+$remainingAvatars",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onTertiary,
            maxLines = 1
        )
    }
}