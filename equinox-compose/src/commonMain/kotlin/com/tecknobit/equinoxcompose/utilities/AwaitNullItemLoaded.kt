package com.tecknobit.equinoxcompose.utilities

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.*

/**
 * Method used to wait asynchronously the change of the state of a `null` item to its non-null value.
 *
 * This method is useful in those scenarios where the content to display depends on the non-null item.
 *
 * For example:
 * ```kotlin
 * @Composable
 * override fun ArrangeScreenContent() {
 *     val text = remember { mutableStateOf<String?>(null) }
 *
 *     // simulated waiting
 *     LaunchedEffect(Unit) {
 *         delay(1000)
 *         text.value = "Hello World!"
 *     }
 *
 *     awaitNullItemLoaded(
 *         itemToWait = text.value,
 *         extras = null, // any extras condition
 *         loadedContent = { nullSafeText -> // non-null value
 *             // the UI content which depends on the text state
 *             Text(
 *                 text = nullSafeText
 *             )
 *         }
 *     )
 * }
 * ```
 *
 * @param itemToWait The item initially null to wait
 * @param extras Extra conditions to apply to show the [loadedContent] that depends on the non-null value of the
 * [itemToWait]
 * @param loadedContent The content to display when the [itemToWait] is not more `null`
 */
@Composable
@NonRestartableComposable
fun <T> awaitNullItemLoaded(
    itemToWait: T?,
    extras: (T) -> Boolean = { true },
    loadedContent: @Composable (T) -> Unit,
) {
    var loaded by remember { mutableStateOf(false) }
    LaunchedEffect(itemToWait) {
        loaded = itemToWait != null && extras(itemToWait)
    }
    AnimatedVisibility(
        visible = loaded
    ) {
        loadedContent(itemToWait!!)
    }
}