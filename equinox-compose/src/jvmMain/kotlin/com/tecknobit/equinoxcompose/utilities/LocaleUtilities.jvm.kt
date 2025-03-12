package com.tecknobit.equinoxcompose.utilities

import androidx.compose.ui.text.intl.Locale

/**
 * Method used to get the current locale language set
 *
 * @return the current locale language set as [String]
 */
actual fun getCurrentLocaleLanguage(): String {
    return Locale.current.toLanguageTag()
}