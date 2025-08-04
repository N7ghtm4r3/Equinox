package com.tecknobit.equinoxcompose.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorProducer
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
@ExperimentalMaterial3Api
fun InformativeIcon(
    modifier: Modifier = Modifier,
    infoTextColor: Color = TooltipDefaults.plainTooltipContentColor,
    infoTextBackgroundColor: Color = TooltipDefaults.plainTooltipContainerColor,
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
                modifier = modifier,
                imageVector = imageVector,
                tint = tint,
                contentDescription = infoText
            )
        }
    )
}

@Composable
@ExperimentalMaterial3Api
fun InformativeIcon(
    modifier: Modifier = Modifier,
    infoTextColor: Color = TooltipDefaults.plainTooltipContentColor,
    infoTextBackgroundColor: Color = TooltipDefaults.plainTooltipContainerColor,
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
                modifier = modifier,
                bitmap = bitmap,
                tint = tint,
                contentDescription = infoText
            )
        }
    )
}

@Composable
@ExperimentalMaterial3Api
fun InformativeIcon(
    modifier: Modifier = Modifier,
    infoTextColor: Color = TooltipDefaults.plainTooltipContentColor,
    infoTextBackgroundColor: Color = TooltipDefaults.plainTooltipContainerColor,
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
                modifier = modifier,
                painter = painter,
                tint = tint,
                contentDescription = infoText
            )
        }
    )
}

@Composable
@ExperimentalMaterial3Api
fun InformativeIcon(
    modifier: Modifier = Modifier,
    infoTextColor: Color = TooltipDefaults.plainTooltipContentColor,
    infoTextBackgroundColor: Color = TooltipDefaults.plainTooltipContainerColor,
    painter: Painter,
    tint: ColorProducer?,
    infoText: String,
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