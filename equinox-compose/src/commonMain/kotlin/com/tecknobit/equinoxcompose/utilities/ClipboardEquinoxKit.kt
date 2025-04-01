package com.tecknobit.equinoxcompose.utilities

/**
 * Method used to copy to the clipboard a content value
 *
 * @param content The content to copy
 * @param onCopy The action to execute after the copy in the clipboard
 */
expect fun copyOnClipboard(
    content: String,
    onCopy: (() -> Unit)? = null,
)