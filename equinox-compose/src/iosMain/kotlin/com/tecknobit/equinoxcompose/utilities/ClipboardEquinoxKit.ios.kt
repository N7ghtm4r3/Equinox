package com.tecknobit.equinoxcompose.utilities

import platform.UIKit.UIPasteboard

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
    val pasteboard = UIPasteboard.generalPasteboard
    pasteboard.setValue(
        value = content,
        forPasteboardType = "public.text"
    )
    onCopy?.invoke()
}