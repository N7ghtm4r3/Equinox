@file:OptIn(ExperimentalTime::class)

package com.tecknobit.equinoxcore.time

import com.tecknobit.equinoxcore.annotations.Wrapper
import com.tecknobit.equinoxcore.time.TimeFormatter.dateAndTimeParsing
import kotlinx.datetime.*
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlin.jvm.JvmStatic
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

/**
 * The `TimeFormatter` object handles temporal values and provides easy access to custom formats for display or further use
 *
 * @author N7ghtm4r3 - Tecknobit
 * @since 1.0.6
 */
object TimeFormatter {

    /**
     * `MILLIS_GAP_CONVERSION_RATE` conversion rate from millis to other time format
     */
    private const val MILLIS_GAP_CONVERSION_RATE = 1000

    /**
     * `SEXAGESIMAL_CONVERSION_RATE` rate for a sexagesimal system conversion
     */
    private const val SEXAGESIMAL_CONVERSION_RATE = 60

    /**
     * `TIME_PATTERN_REGEX` regex pattern used from the [toTimestamp] method to distinguish what proper method
     * use (one from [dateAndTimeParsing] or [dateParsing]) to correctly format the pattern parameter passed as argument
     */
    private const val TIME_PATTERN_REGEX = "(hh?|HH?|mm?|ss?|SSS?)"

    /**
     * `timePattern` the regex validator for the [TIME_PATTERN_REGEX]
     */
    private val timePattern = Regex(TIME_PATTERN_REGEX)

    /**
     * `COMPLETE_EUROPEAN_DATE_PATTERN` the complete date pattern used in the european zone
     */
    const val COMPLETE_EUROPEAN_DATE_PATTERN = "dd/MM/yyyy HH:mm:ss"

    /**
     * `EUROPEAN_DATE_PATTERN` the date pattern used in the european zone
     */
    const val EUROPEAN_DATE_PATTERN = "dd/MM/yyyy"

    /**
     * `COMPLETE_AMERICAN_DATE_PATTERN` the complete date pattern used in the american zone
     */
    const val COMPLETE_AMERICAN_DATE_PATTERN = "MM/dd/yyyy HH:mm:ss"

    /**
     * `AMERICAN_DATE_PATTERN` the date pattern used in the american zone
     */
    const val AMERICAN_DATE_PATTERN = "MM/dd/yyyy HH:mm:ss"

    /**
     * `COMPLETE_TIME_PATTERN` the complete time pattern with hours (24 format), minutes, seconds and milliseconds
     */
    const val COMPLETE_TIME_PATTERN = "HH:mm:ss.SSS"

    /**
     * `H12_HOURS_MINUTES_SECONDS_PATTERN` the pattern with hours (12 format), minutes and seconds
     */
    const val H12_HOURS_MINUTES_SECONDS_PATTERN = "hh:mm:ss"

    /**
     * `H12_HOURS_MINUTES_PATTERN` the pattern with hours (12 format) and minutes
     */
    const val H12_HOURS_MINUTES_PATTERN = "hh:mm"

    /**
     * `H24_HOURS_MINUTES_SECONDS_PATTERN` the pattern with hours (24 format), minutes and seconds
     */
    const val H24_HOURS_MINUTES_SECONDS_PATTERN = "HH:mm:ss"

    /**
     * `H24_HOURS_MINUTES_PATTERN` the pattern with hours (24 format) and minutes
     */
    const val H24_HOURS_MINUTES_PATTERN = "HH:mm"

    /**
     * `ISO_LOCAL_DATE_TIME` ISO 8601 compliant local date-time pattern (no timezone)
     *
     * @since 1.1.7
     */
    const val ISO_LOCAL_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS"

    /**
     * `ISO_LOCAL_DATE_PATTERN` ISO 8601 compliant date pattern (date only)
     *
     * @since 1.1.7
     */
    const val ISO_LOCAL_DATE_PATTERN = "yyyy-MM-dd"

    /**
     * `defaultPattern` the default pattern used by the [TimeFormatter] if no custom one is specified
     */
    @JvmStatic
    var defaultPattern: String = COMPLETE_EUROPEAN_DATE_PATTERN

    /**
     * `invalidTimeDefValue` the default value returned when an error occurred during the formatting
     */
    @JvmStatic
    var invalidTimeDefValue: Long = -1

    /**
     * `invalidTimeStringDefValue` the default value returned when an error occurred during the formatting of a [String]
     * value
     */
    @JvmStatic
    var invalidTimeStringDefValue: String? = null

    /**
     * Method used to get the current timestamp value in milliseconds
     *
     * @return current timestamp as [Long]
     */
    @JvmStatic
    fun currentTimestamp(): Long {
        return Clock.System.now().toEpochMilliseconds()
    }

    /**
     * Method used to format the current timestamp as string
     *
     * @param pattern The pattern to use to format the millis value
     *
     * @return the millis value as [String]
     */
    @JvmStatic
    fun formatNowAsString(
        pattern: String = defaultPattern,
    ): String {
        val now = Clock.System.now().toEpochMilliseconds()
        return now.toDateString(
            pattern = pattern
        )
    }

    /**
     * Method used to format a [Long] value into the corresponding date-string value
     *
     * @param invalidTimeDefValue The default value to specify an invalid temporal value not to be formatted
     * @param pattern The pattern to use to format the long value
     * @param timeZone The time zone used to convert the date and time values
     *
     * @return the formatted long value as [String]
     */
    @JvmStatic
    @OptIn(FormatStringsInDatetimeFormats::class)
    fun Long.toDateString(
        invalidTimeDefValue: String? = null,
        pattern: String = defaultPattern,
        timeZone: TimeZone = TimeZone.currentSystemDefault()
    ): String {
        invalidTimeDefValue?.let { defValue ->
            if (this == this@TimeFormatter.invalidTimeDefValue)
                return defValue
        }
        val instant = Instant.fromEpochMilliseconds(this)
        val localDateTime = instant.toLocalDateTime(
            timeZone = timeZone
        )
        return LocalDateTime.Format {
            byUnicodePattern(pattern)
        }.format(localDateTime)
    }

    /**
     * Method used to format a [String] value into the corresponding timestamp value
     *
     * @param invalidTimeDefValue The default value to specify an invalid temporal value not to be formatted
     * @param pattern The pattern to use to format the string value
     *
     * @return the formatted string value as [Long]
     */
    @JvmStatic
    fun String.toTimestamp(
        invalidTimeDefValue: Long? = null,
        pattern: String = defaultPattern,
    ): Long {
        invalidTimeDefValue?.let { defValue ->
            if (this == this@TimeFormatter.invalidTimeStringDefValue)
                return defValue
        }
        return if (timePattern.containsMatchIn(pattern)) {
            this.dateAndTimeParsing(
                pattern = pattern
            )
        } else {
            this.dateParsing(
                pattern = pattern
            )
        }
    }

    /**
     * Method used to format a [String] value which contains both date and time related values
     *
     * @param pattern The pattern to use to format the string value
     * @param timeZone The time zone used to convert the date and time values
     *
     * @return the formatted string value as [Long]
     */
    @OptIn(FormatStringsInDatetimeFormats::class)
    @JvmStatic
    private fun String.dateAndTimeParsing(
        pattern: String,
        timeZone: TimeZone = TimeZone.currentSystemDefault()
    ): Long {
        val parser = LocalDateTime.Format {
            byUnicodePattern(pattern)
        }
        return try {
            parser.parse(this).toInstant(
                timeZone = timeZone
            ).toEpochMilliseconds()
        } catch (e: Exception) {
            invalidTimeDefValue
        }
    }

    /**
     * Method used to format a [String] value which contains just date related values
     *
     * @param pattern The pattern to use to format the string value
     * @param timeZone The time zone used to convert the date and time values
     *
     * @return the formatted string value as [Long]
     */
    @OptIn(FormatStringsInDatetimeFormats::class)
    private fun String.dateParsing(
        pattern: String,
        timeZone: TimeZone = TimeZone.currentSystemDefault()
    ): Long {
        val parser = LocalDate.Format {
            byUnicodePattern(pattern)
        }
        return try {
            val localDate = parser.parse(this)
            LocalDateTime(
                year = localDate.year,
                month = localDate.month,
                day = localDate.day,
                hour = 0,
                minute = 0
            ).toInstant(
                timeZone = timeZone
            ).toEpochMilliseconds()
        } catch (e: Exception) {
            invalidTimeDefValue
        }
    }

    /**
     * Method used to count the nanoseconds passed from the [Long] value to the current timestamp
     *
     * @return the nanoseconds passed as [Long]
     *
     * @throws IllegalArgumentException when the second date is lesser than the first value
     */
    @Wrapper
    @JvmStatic
    fun Long.nanosecondsUntilNow(): Long {
        return nanosecondsUntil(
            untilDate = Clock.System.now().toEpochMilliseconds()
        )
    }

    /**
     * Method used to count the nanoseconds passed from the [Long] value to the [untilDate]
     *
     * @param untilDate The second date used to count the nanoseconds
     *
     * @return the nanoseconds passed as [Long]
     *
     * @throws IllegalArgumentException when the [untilDate] is lesser than the first value
     */
    @JvmStatic
    fun Long.nanosecondsUntil(
        untilDate: Long,
    ): Long {
        val millisGap = this.millisecondsUntil(
            untilDate = untilDate
        )
        return millisGap * MILLIS_GAP_CONVERSION_RATE
    }

    /**
     * Method used to count the milliseconds passed from the [Long] value to the current timestamp
     *
     * @return the milliseconds passed as [Long]
     *
     * @throws IllegalArgumentException when the second date is lesser than the first value
     */
    @Wrapper
    @JvmStatic
    fun Long.millisecondsUntilNow(): Long {
        return millisecondsUntil(
            untilDate = Clock.System.now().toEpochMilliseconds()
        )
    }

    /**
     * Method used to count the milliseconds passed from the [Long] value to the [untilDate]
     *
     * @param untilDate The second date used to count the milliseconds
     *
     * @return the milliseconds passed as [Long]
     *
     * @throws IllegalArgumentException when the [untilDate] is lesser than the first value
     */
    @JvmStatic
    fun Long.millisecondsUntil(
        untilDate: Long,
    ): Long {
        if (this > untilDate)
            throw IllegalArgumentException("The untilDate must be greater than the first one")
        return untilDate - this
    }

    /**
     * Method used to count the seconds passed from the [Long] value to the current timestamp
     *
     * @return the seconds passed as [Long]
     *
     * @throws IllegalArgumentException when the second date is lesser than the first value
     */
    @Wrapper
    @JvmStatic
    fun Long.secondsUntilNow(): Int {
        return secondsUntil(
            untilDate = Clock.System.now().toEpochMilliseconds()
        )
    }

    /**
     * Method used to count the seconds passed from the [Long] value to the [untilDate]
     *
     * @param untilDate The second date used to count the seconds
     *
     * @return the seconds passed as [Long]
     *
     * @throws IllegalArgumentException when the [untilDate] is lesser than the first value
     */
    @JvmStatic
    fun Long.secondsUntil(
        untilDate: Long,
    ): Int {
        val millisGap = this.millisecondsUntil(
            untilDate = untilDate
        )
        return (millisGap / MILLIS_GAP_CONVERSION_RATE).toInt()
    }

    /**
     * Method used to count the minutes passed from the [Long] value to the current timestamp
     *
     * @return the minutes passed as [Long]
     *
     * @throws IllegalArgumentException when the second date is lesser than the first value
     */
    @Wrapper
    @JvmStatic
    fun Long.minutesUntilNow(): Int {
        return minutesUntil(
            untilDate = Clock.System.now().toEpochMilliseconds()
        )
    }

    /**
     * Method used to count the minutes passed from the [Long] value to the [untilDate]
     *
     * @param untilDate The second date used to count the minutes
     *
     * @return the minutes passed as [Long]
     *
     * @throws IllegalArgumentException when the [untilDate] is lesser than the first value
     */
    @JvmStatic
    fun Long.minutesUntil(
        untilDate: Long,
    ): Int {
        val millisGap = this.secondsUntil(
            untilDate = untilDate
        )
        return (millisGap / SEXAGESIMAL_CONVERSION_RATE)
    }

    /**
     * Method used to count the hours passed from the [Long] value to the current timestamp
     *
     * @return the hours passed as [Long]
     *
     * @throws IllegalArgumentException when the second date is lesser than the first value
     *
     */
    @Wrapper
    @JvmStatic
    fun Long.hoursUntilNow(): Int {
        return hoursUntil(
            untilDate = Clock.System.now().toEpochMilliseconds()
        )
    }

    /**
     * Method used to count the hours passed from the [Long] value to the [untilDate]
     *
     * @param untilDate The second date used to count the hours
     *
     * @return the hours passed as [Long]
     *
     * @throws IllegalArgumentException when the [untilDate] is lesser than the first value
     */
    @JvmStatic
    fun Long.hoursUntil(
        untilDate: Long,
    ): Int {
        val millisGap = this.minutesUntil(
            untilDate = untilDate
        )
        return (millisGap / SEXAGESIMAL_CONVERSION_RATE)
    }

    /**
     * Method used to count the days passed from the [Long] value to the current timestamp
     *
     * @return the days passed as [Long]
     *
     * @throws IllegalArgumentException when the second date is lesser than the first value
     */
    @Wrapper
    @JvmStatic
    fun Long.daysUntilNow(): Long {
        return daysUntil(
            untilDate = Clock.System.now().toEpochMilliseconds()
        )
    }

    /**
     * Method used to count the days passed from the [Long] value to the [untilDate]
     *
     * @param untilDate The second date used to count the days
     *
     * @return the days passed as [Long]
     *
     * @throws IllegalArgumentException when the [untilDate] is lesser than the first value
     */
    @JvmStatic
    fun Long.daysUntil(
        untilDate: Long,
    ): Long {
        return computeUntilGap(
            baseDate = this,
            untilDate = untilDate,
            untilGap = DateTimeUnit.DAY
        )
    }

    /**
     * Method used to count the weeks passed from the [Long] value to the current timestamp
     *
     * @return the weeks passed as [Long]
     *
     * @throws IllegalArgumentException when the second date is lesser than the first value
     */
    @Wrapper
    @JvmStatic
    fun Long.weeksUntilNow(): Long {
        return weeksUntil(
            untilDate = Clock.System.now().toEpochMilliseconds()
        )
    }

    /**
     * Method used to count the weeks passed from the [Long] value to the [untilDate]
     *
     * @param untilDate The second date used to count the weeks
     *
     * @return the weeks passed as [Long]
     *
     * @throws IllegalArgumentException when the [untilDate] is lesser than the first value
     */
    @JvmStatic
    fun Long.weeksUntil(
        untilDate: Long,
    ): Long {
        return computeUntilGap(
            baseDate = this,
            untilDate = untilDate,
            untilGap = DateTimeUnit.WEEK
        )
    }

    /**
     * Method used to count the months passed from the [Long] value to the current timestamp
     *
     * @return the months passed as [Long]
     *
     * @throws IllegalArgumentException when the second date is lesser than the first value
     */
    @Wrapper
    @JvmStatic
    fun Long.monthsUntilNow(): Long {
        return monthsUntil(
            untilDate = Clock.System.now().toEpochMilliseconds()
        )
    }

    /**
     * Method used to count the months passed from the [Long] value to the [untilDate]
     *
     * @param untilDate The second date used to count the months
     *
     * @return the months passed as [Long]
     *
     * @throws IllegalArgumentException when the [untilDate] is lesser than the first value
     */
    @JvmStatic
    fun Long.monthsUntil(
        untilDate: Long,
    ): Long {
        return computeUntilGap(
            baseDate = this,
            untilDate = untilDate,
            untilGap = DateTimeUnit.MONTH
        )
    }

    /**
     * Method used to count the quarters passed from the [Long] value to the current timestamp
     *
     * @return the quarters passed as [Long]
     *
     * @throws IllegalArgumentException when the second date is lesser than the first value
     */
    @Wrapper
    @JvmStatic
    fun Long.quartersUntilNow(): Long {
        return quartersUntil(
            untilDate = Clock.System.now().toEpochMilliseconds()
        )
    }

    /**
     * Method used to count the quarters passed from the [Long] value to the [untilDate]
     *
     * @param untilDate The second date used to count the quarters
     *
     * @return the quarters passed as [Long]
     *
     * @throws IllegalArgumentException when the [untilDate] is lesser than the first value
     */
    @JvmStatic
    fun Long.quartersUntil(
        untilDate: Long,
    ): Long {
        return computeUntilGap(
            baseDate = this,
            untilDate = untilDate,
            untilGap = DateTimeUnit.QUARTER
        )
    }

    /**
     * Method used to count the years passed from the [Long] value to the current timestamp
     *
     * @return the years passed as [Long]
     *
     * @throws IllegalArgumentException when the second date is lesser than the first value
     */
    @Wrapper
    @JvmStatic
    fun Long.yearsUntilNow(): Long {
        return yearsUntil(
            untilDate = Clock.System.now().toEpochMilliseconds()
        )
    }

    /**
     * Method used to count the years passed from the [Long] value to the [untilDate]
     *
     * @param untilDate The second date used to count the years
     *
     * @return the years passed as [Long]
     *
     * @throws IllegalArgumentException when the [untilDate] is lesser than the first value
     */
    @JvmStatic
    fun Long.yearsUntil(
        untilDate: Long,
    ): Long {
        return computeUntilGap(
            baseDate = this,
            untilDate = untilDate,
            untilGap = DateTimeUnit.YEAR
        )
    }

    /**
     * Method used to count the centuries passed from the [Long] value to the current timestamp
     *
     * @return the centuries passed as [Long]
     *
     * @throws IllegalArgumentException when the second date is lesser than the first value
     */
    @Wrapper
    @JvmStatic
    fun Long.centuriesUntilNow(): Long {
        return centuriesUntil(
            untilDate = Clock.System.now().toEpochMilliseconds()
        )
    }

    /**
     * Method used to count the centuries passed from the [Long] value to the [untilDate]
     *
     * @param untilDate The second date used to count the centuries
     *
     * @return the centuries passed as [Long]
     *
     * @throws IllegalArgumentException when the [untilDate] is lesser than the first value
     */
    @JvmStatic
    fun Long.centuriesUntil(
        untilDate: Long,
    ): Long {
        return computeUntilGap(
            baseDate = this,
            untilDate = untilDate,
            untilGap = DateTimeUnit.CENTURY
        )
    }

    /**
     * Method used to calculate a temporal gap between the [baseDate] and the [untilDate]
     *
     * @param baseDate The first date used to calculate the temporal gap
     * @param untilDate The second date used to calculate the temporal gap
     * @param untilGap The temporal gap to calculate
     *
     * @return the temporal gap passed as [Long]
     *
     * @throws IllegalArgumentException when the second date is lesser than the first value
     */
    private fun computeUntilGap(
        baseDate: Long,
        untilDate: Long,
        untilGap: DateTimeUnit.DateBased,
    ): Long {
        if (untilDate < baseDate)
            throw IllegalArgumentException("The second date must be greater than the first one")
        val firstDate = baseDate.toLocalDate()
        val secondDate = untilDate.toLocalDate()
        return firstDate
            .until(
                other = secondDate,
                unit = untilGap
            )
    }

    /**
     * Method used to transform a [Long] value into the corresponding [LocalDate]
     *
     * @param timeZone The time zone used to convert the date and time values
     *
     * @return the local date value as [LocalDate]
     */
    @JvmStatic
    fun Long.toLocalDate(
        timeZone: TimeZone = TimeZone.currentSystemDefault()
    ): LocalDate {
        return this.toLocalDateTime().date
    }

    /**
     * Method used to transform a [Long] value into the corresponding [LocalDateTime]
     *
     * @param timeZone The time zone used to convert the date and time values
     *
     * @return the local date value as [LocalDateTime]
     */
    @JvmStatic
    fun Long.toLocalDateTime(
        timeZone: TimeZone = TimeZone.currentSystemDefault()
    ): LocalDateTime {
        return Instant.fromEpochMilliseconds(this)
            .toLocalDateTime(
                timeZone = timeZone
            )
    }

    /**
     * Method used to transform a [LocalDateTime] value into the corresponding nanoseconds value
     *
     * @param timeZone The time zone used to convert the date and time values
     *
     * @return the local date time value in nanoseconds as [Long]
     */
    @JvmStatic
    fun LocalDateTime.toNanos(
        timeZone: TimeZone = TimeZone.currentSystemDefault()
    ): Long {
        return this.toMillis(
            timeZone = timeZone
        ) * MILLIS_GAP_CONVERSION_RATE
    }

    /**
     * Method used to transform a [LocalDateTime] value into the corresponding seconds value
     *
     * @param timeZone The time zone used to convert the date and time values
     *
     * @return the local date time value in seconds as [Long]
     */
    @JvmStatic
    fun LocalDateTime.toSeconds(
        timeZone: TimeZone = TimeZone.currentSystemDefault()
    ): Long {
        return this.toMillis(
            timeZone = timeZone
        ) / MILLIS_GAP_CONVERSION_RATE
    }

    /**
     * Method used to transform a [LocalDateTime] value into the corresponding milliseconds value
     *
     * @param timeZone The time zone used to convert the date and time values
     *
     * @return the local date time value in milliseconds as [Long]
     */
    @JvmStatic
    fun LocalDateTime.toMillis(
        timeZone: TimeZone = TimeZone.currentSystemDefault()
    ): Long {
        return this.toInstant(
            timeZone = timeZone
        ).toEpochMilliseconds()
    }

}