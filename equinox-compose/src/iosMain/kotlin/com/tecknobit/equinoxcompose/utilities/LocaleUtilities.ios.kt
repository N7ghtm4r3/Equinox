package com.tecknobit.equinoxcompose.utilities

import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.languageCode

/**
 * Method used to get the current locale language set
 *
 * @return the current locale language set as [String]
 */
actual fun getCurrentLocaleLanguage(): String {
    return NSLocale.currentLocale.languageCode
}