package com.tecknobit.equinoxcompose.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.outlined.ArrowCircleDown
import androidx.compose.material.icons.outlined.ArrowCircleUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/**
 * Component to dynamically display a long text initially collapsed
 *
 * If the text lines are less than the [maxLines] set its behavior will be the same as a normal [Text] component
 *
 * @param containerModifier The modifier to apply to the container [Column]
 * @param textModifier The modifier to apply to the [Text] component
 * @param textStyle The text style to customize the appearance of the text
 * @param text The text value to display
 * @param maxLines The max lines to display in collapsed mode
 * @param expandedMaxLines The max lines value to display in expanded mode
 * @param overflow The behavior of the text in the overflow case
 * @param iconSize The size of the icon indicator
 * @param expandedIcon The icon displayed when the text is in expanded mode
 * @param collapsedIcon The icon displayed when the text is in collapsed mode
 */
@Composable
fun ExpandableText(
    containerModifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle.Default,
    text: StringResource,
    maxLines: Int = 5,
    expandedMaxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    iconSize: Dp = 30.dp,
    expandedIcon: ImageVector = Outlined.ArrowCircleUp,
    collapsedIcon: ImageVector = Outlined.ArrowCircleDown,
) {
    ExpandableText(
        containerModifier = containerModifier,
        textModifier = textModifier,
        textStyle = textStyle,
        text = stringResource(text),
        maxLines = maxLines,
        expandedMaxLines = expandedMaxLines,
        overflow = overflow,
        iconSize = iconSize,
        expandedIcon = expandedIcon,
        collapsedIcon = collapsedIcon
    )
}

/**
 * Component to dynamically display a long text initially collapsed
 *
 * If the text lines are less than the [maxLines] set its behavior will be the same as a normal [Text] component
 *
 * @param containerModifier The modifier to apply to the container [Column]
 * @param textModifier The modifier to apply to the [Text] component
 * @param textStyle The text style to customize the appearance of the text
 * @param text The text value to display
 * @param maxLines The max lines to display in collapsed mode
 * @param expandedMaxLines The max lines value to display in expanded mode
 * @param overflow The behavior of the text in the overflow case
 * @param iconSize The size of the icon indicator
 * @param expandedIcon The icon displayed when the text is in expanded mode
 * @param collapsedIcon The icon displayed when the text is in collapsed mode
 */
@Composable
fun ExpandableText(
    containerModifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle.Default,
    text: String,
    maxLines: Int = 5,
    expandedMaxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    iconSize: Dp = 30.dp,
    expandedIcon: ImageVector = Outlined.ArrowCircleUp,
    collapsedIcon: ImageVector = Outlined.ArrowCircleDown,
) {
    var expanded by remember { mutableStateOf(false) }
    var expandable by remember { mutableStateOf(false) }
    Column(
        modifier = containerModifier
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            modifier = textModifier
                .then(
                    if (expandable)
                        Modifier.animateContentSize()
                    else
                        Modifier
                ),
            text = text,
            style = textStyle,
            onTextLayout = { textLayoutResult ->
                expandable = textLayoutResult.lineCount >= maxLines
            },
            maxLines = if (expanded)
                expandedMaxLines
            else
                maxLines,
            overflow = overflow
        )
        if (expandable) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                HorizontalDivider()
                IconButton(
                    onClick = { expanded = !expanded }
                ) {
                    Icon(
                        modifier = Modifier
                            .size(iconSize),
                        imageVector = if (expanded)
                            expandedIcon
                        else
                            collapsedIcon,
                        contentDescription = null
                    )
                }
            }
        }
    }
}