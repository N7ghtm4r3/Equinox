package com.tecknobit.equinoxcompose.utilities

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import com.tecknobit.equinoxcompose.helpers.utils.AppContext

/**
 * Method to copy to the clipboard a content value
 *
 * @param content The content to copy
 * @param onCopy The action to execute after the copy in the clipboard
 */
actual fun copyOnClipboard(
    content: String,
    onCopy: (() -> Unit)?,
) {
    val context = AppContext.get()
    val clipboard = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(null, content)
    clipboard.setPrimaryClip(clip)
    onCopy?.invoke()
}