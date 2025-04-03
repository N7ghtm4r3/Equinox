package com.tecknobit.equinoxcompose.utilities

import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

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
    val stringSelection = StringSelection(content)
    Toolkit.getDefaultToolkit().systemClipboard.setContents(stringSelection, null)
    onCopy?.invoke()
}