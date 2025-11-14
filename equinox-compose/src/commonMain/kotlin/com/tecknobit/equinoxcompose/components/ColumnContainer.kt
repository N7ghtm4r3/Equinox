package com.tecknobit.equinoxcompose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tecknobit.equinoxcompose.utilities.responsiveMaxWidth

/**
 * Layout composable used to create a horizontal-centered container to place the [content] in the middle of the screen.
 * Its width is internally handled using the [responsiveMaxWidth] API making the component responsive by default
 *
 * @param outerModifier The modifier to apply to the outer [Column] made-up this component
 * @param innerModifier The modifier to apply to the inner [Column] made-up this component
 * @param verticalArrangement The vertical arrangement of the [content]
 * @param content The content to display inside the container
 *
 * @since 1.1.8
 */
@Composable
@ExperimentalComposeApi
inline fun ColumnContainer(
    outerModifier: Modifier = Modifier,
    innerModifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = outerModifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = innerModifier
                .responsiveMaxWidth(),
            verticalArrangement = verticalArrangement,
            content = content
        )
    }
}