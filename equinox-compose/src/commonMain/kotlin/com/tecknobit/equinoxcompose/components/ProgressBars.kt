package com.tecknobit.equinoxcompose.components

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
import com.tecknobit.equinoxcore.annotations.FutureEquinoxApi

@FutureEquinoxApi(
    releaseVersion = "1.1.1",
    additionalNotes = """
        - Allow suspendable currentProgress lambda calculation
    """
)
@Composable
@ExperimentalComposeUiApi
fun HorizontalProgressBar(
    containerModifier: Modifier = Modifier,
    progressBarModifier: Modifier = Modifier,
    completionWidth: Dp,
    lineColor: Color = MaterialTheme.colorScheme.primary,
    currentProgress: () -> Number,
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
) {
    HorizontalProgressBar(
        containerModifier = containerModifier,
        progressBarModifier = progressBarModifier,
        completionWidth = completionWidth,
        currentProgress = currentProgress.invoke(),
        lineColor = lineColor,
        cap = cap,
        strokeWidth = strokeWidth,
        total = total,
        onCompletion = onCompletion,
        progressIndicator = progressIndicator
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
                    x = (progress * currentProgress.toInt()).toPx(),
                    y = 0f
                ),
                strokeWidth = strokeWidth.toPx()
            )
        }
    }
}