package com.tecknobit.equinoxcompose.session.screens

import com.tecknobit.equinoxcore.annotations.Assembler

/**
 * Method used to resolve the message to log by the logger of a [EquinoxNoModelScreen]
 *
 * @param event The event to log
 *
 * @return the message to log as [String]
 */
@Assembler
internal actual fun resolveScreenEventMessage(
    event: String
): String {
    return defaultLoggerMessage(
        event = event
    )
}