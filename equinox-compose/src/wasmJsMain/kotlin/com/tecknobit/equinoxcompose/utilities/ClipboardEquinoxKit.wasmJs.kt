package com.tecknobit.equinoxcompose.utilities

import kotlinx.browser.window

/**
 * Method used to copy to the clipboard a content value
 *
 * @param content The content to copy
 * @param onCopy The action to execute after the copy in the clipboard
 */
actual fun copyOnClipboard(
    content: String,
    onCopy: (() -> Unit)?,
) {
    val navigator = window.navigator
    navigator.clipboard.writeText(content)
    onCopy?.invoke()
}