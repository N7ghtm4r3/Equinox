package com.tecknobit.equinoxcompose.session.screens

import com.tecknobit.equinoxcore.annotations.Assembler
import com.tecknobit.equinoxcore.annotations.Returner
import com.tecknobit.equinoxcore.time.TimeFormatter
import com.tecknobit.equinoxcore.time.TimeFormatter.ISO_LOCAL_DATE_TIME_PATTERN

/**
 * `DEFAULT_LOGGER_MESSAGE` the default message to log by the logger
 */
internal const val DEFAULT_LOGGER_MESSAGE = "Current status -> %s"

/**
 * Method used to resolve the message to log by the logger of a [EquinoxNoModelScreen]
 * 
 * @param event The event to log
 * 
 * @return the message to log as [String]
 */
@Assembler
internal expect fun resolveScreenEventMessage(
    event: String
): String

/**
 * Method used to format the logger message with an additional timestamp indicative of the event occurrence
 *
 * @param event The event to log
 *
 * @return the message to log with an additional timestamp as [String]
 */
@Assembler
internal fun timestampedLoggerMessage(
    event: String
): String {
    val now =  TimeFormatter.formatNowAsString(
        pattern = ISO_LOCAL_DATE_TIME_PATTERN
    )
    val eventMessage = defaultLoggerMessage(
        event = event
    )
    return "[$now] $eventMessage"
}

/**
 * Method used to format the default logger message
 *
 * @param event The event to log
 *
 * @return the default message to log as [String]
 */
@Returner
internal fun defaultLoggerMessage(
    event: String
): String {
    return DEFAULT_LOGGER_MESSAGE.replace("%s", event)
}

