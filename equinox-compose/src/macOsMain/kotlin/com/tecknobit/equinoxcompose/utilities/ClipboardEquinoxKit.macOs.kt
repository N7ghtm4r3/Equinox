@file:OptIn(BetaInteropApi::class)

package com.tecknobit.equinoxcompose.utilities

import com.tecknobit.equinoxcore.annotations.Returner
import kotlinx.cinterop.BetaInteropApi
import platform.AppKit.NSPasteboard
import platform.AppKit.NSPasteboardTypeString
import platform.Foundation.*

/**
 * Method used to copy to the clipboard a content value
 *
 * @param content The content to copy
 * @param onCopy The action to execute after the copy in the clipboard
 */
actual fun copyOnClipboard(
    content: String,
    onCopy: (() -> Unit)?
) {
    val pasteboard = NSPasteboard.generalPasteboard
    pasteboard.clearContents()
    pasteboard.setData(
        data = content.toNSData(),
        forType = NSPasteboardTypeString
    )
    onCopy?.invoke()
}

/**
 * Method used to convert a string into a [NSData] object
 *
 * @return the string as nullable [NSData]
 */
@Returner
private inline fun String.toNSData(): NSData? {
    return NSString.create(
        string = this
    ).dataUsingEncoding(
        encoding = NSUTF8StringEncoding
    )
}