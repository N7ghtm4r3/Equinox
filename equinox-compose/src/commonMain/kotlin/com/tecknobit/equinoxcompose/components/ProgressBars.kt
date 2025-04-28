package com.tecknobit.equinoxcompose.components

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
@ExperimentalComposeUiApi
fun HorizontalProgressBar(
    containerModifier: Modifier = Modifier,
    progressBarModifier: Modifier = Modifier,
    completionWidth: Dp,
    currentProgress: suspend () -> Number,
    lineColor: Color = MaterialTheme.colorScheme.primary,
    cap: StrokeCap = StrokeCap.Round,
    strokeWidth: Dp = 4.dp,
    total: Number,
    onCompletion: (() -> Unit)? = null,
    progressIndicator: @Composable ColumnScope.(Number) -> Unit = { currentProgressValue ->
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 5.dp
                ),
            text = "$currentProgressValue/$total",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.End
        )
    },
    animationSpec: AnimationSpec<Dp>? = tween(
        durationMillis = 400,
        easing = EaseInOutSine
    ),
) {
    var currentProgressValue by remember { mutableStateOf<Number>(0) }
    LaunchedEffect(currentProgressValue) {
        currentProgressValue = currentProgress()
    }
    HorizontalProgressBar(
        containerModifier = containerModifier,
        progressBarModifier = progressBarModifier,
        completionWidth = completionWidth,
        currentProgress = currentProgressValue,
        lineColor = lineColor,
        cap = cap,
        strokeWidth = strokeWidth,
        total = total,
        onCompletion = onCompletion,
        progressIndicator = { progressIndicator(this, currentProgressValue) },
        animationSpec = animationSpec
    )
}

@Composable
@ExperimentalComposeUiApi
fun HorizontalProgressBar(
    containerModifier: Modifier = Modifier,
    progressBarModifier: Modifier = Modifier,
    completionWidth: Dp,
    currentProgress: Number,
    lineColor: Color = MaterialTheme.colorScheme.primary,
    cap: StrokeCap = StrokeCap.Round,
    strokeWidth: Dp = 4.dp,
    total: Number,
    onCompletion: (() -> Unit)? = null,
    progressIndicator: @Composable ColumnScope.() -> Unit = {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 5.dp
                ),
            text = "$currentProgress/$total",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.End
        )
    },
    animationSpec: AnimationSpec<Dp>? = tween(
        durationMillis = 400,
        easing = EaseInOutSine
    ),
) {
    val density = LocalDensity.current
    var completionRealWidth by remember { mutableStateOf(0.dp) }
    Column(
        modifier = containerModifier
            .onGloballyPositioned { layoutCoordinates ->
                val widthPx = layoutCoordinates.size.width
                completionRealWidth = with(density) {
                    widthPx.toDp()
                }
                if (completionRealWidth > completionWidth)
                    completionRealWidth = completionWidth
            }
            .width(completionWidth)
    ) {
        val progress = completionRealWidth / total.toInt()
        LaunchedEffect(currentProgress) {
            if (currentProgress == total)
                onCompletion?.invoke()
        }
        progressIndicator()
        var progressWidth = (progress * currentProgress.toInt())
        animationSpec?.let {
            progressWidth = animateDpAsState(
                targetValue = progressWidth,
                animationSpec = animationSpec
            ).value
        }
        Canvas(
            modifier = progressBarModifier
        ) {
            drawLine(
                color = lineColor,
                cap = cap,
                start = Offset(
                    x = 0f,
                    y = 0f
                ),
                end = Offset(
                    x = progressWidth.toPx(),
                    y = 0f
                ),
                strokeWidth = strokeWidth.toPx()
            )
        }
    }
}