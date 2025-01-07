package com.tecknobit.equinoxcompose.utilities

import kotlinx.browser.window

/**
 * Method to get the current locale language set
 *
 * @return the current locale language set as [String]
 */
actual fun getCurrentLocaleLanguage(): String {
    return window.navigator.language
}