package com.tecknobit.equinoxcore.helpers

import com.tecknobit.equinoxcore.annotations.Wrapper
import kotlinx.datetime.*
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern

object TimeFormatter {

    private const val MILLIS_GAP_CONVERSION_RATE = 1000

    private const val SEXAGESIMAL_CONVERSION_RATE = 60

    const val COMPLETE_DATE_PATTERN = "dd/MM/yyyy HH:mm:ss"

    const val DATE_PATTERN = "dd/MM/yyyy"

    const val H24_HOURS_MINUTES_PATTERN = "HH:mm"

    private var defaultPattern: String = COMPLETE_DATE_PATTERN

    private var defaultDatePattern: String = DATE_PATTERN

    var invalidTimeGuard: Long = -1

    @Wrapper
    fun Long.formatAsDateString(
        invalidTimeDefValue: String? = null,
    ): String {
        return formatAsTimeString(
            invalidTimeDefValue = invalidTimeDefValue,
            pattern = defaultDatePattern
        )
    }

    @Wrapper
    fun Long.formatAsTimeString(
        invalidTimeDefValue: String? = null,
    ): String {
        return formatAsTimeString(
            invalidTimeDefValue = invalidTimeDefValue,
            pattern = defaultPattern
        )
    }

    @OptIn(FormatStringsInDatetimeFormats::class)
    fun Long.formatAsTimeString(
        invalidTimeDefValue: String? = null,
        pattern: String,
    ): String {
        invalidTimeDefValue?.let { defValue ->
            if (this == invalidTimeGuard)
                return defValue
        }
        val instant = Instant.fromEpochMilliseconds(this)
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        return LocalDateTime.Format {
            byUnicodePattern(pattern)
        }.format(localDateTime)
    }

    @Wrapper
    fun Long.nanosecondsUntilNow(): Long {
        return nanosecondsUntil(
            untilDate = Clock.System.now().toEpochMilliseconds()
        )
    }

    fun Long.nanosecondsUntil(
        untilDate: Long,
    ): Long {
        val millisGap = this.millisecondsUntil(
            untilDate = untilDate
        )
        return millisGap * MILLIS_GAP_CONVERSION_RATE
    }

    @Wrapper
    fun Long.millisecondsUntilNow(): Long {
        return millisecondsUntil(
            untilDate = Clock.System.now().toEpochMilliseconds()
        )
    }

    fun Long.millisecondsUntil(
        untilDate: Long,
    ): Long {
        return untilDate - this
    }

    @Wrapper
    fun Long.secondsUntilNow(): Int {
        return secondsUntil(
            untilDate = Clock.System.now().toEpochMilliseconds()
        )
    }

    fun Long.secondsUntil(
        untilDate: Long,
    ): Int {
        val millisGap = this.millisecondsUntil(
            untilDate = untilDate
        )
        return (millisGap / MILLIS_GAP_CONVERSION_RATE).toInt()
    }

    @Wrapper
    fun Long.minutesUntilNow(): Int {
        return minutesUntil(
            untilDate = Clock.System.now().toEpochMilliseconds()
        )
    }

    fun Long.minutesUntil(
        untilDate: Long,
    ): Int {
        val millisGap = this.secondsUntil(
            untilDate = untilDate
        )
        return (millisGap / SEXAGESIMAL_CONVERSION_RATE)
    }

    @Wrapper
    fun Long.hoursUntilNow(): Int {
        return hoursUntil(
            untilDate = Clock.System.now().toEpochMilliseconds()
        )
    }

    fun Long.hoursUntil(
        untilDate: Long,
    ): Int {
        val millisGap = this.minutesUntil(
            untilDate = untilDate
        )
        return (millisGap / SEXAGESIMAL_CONVERSION_RATE)
    }

    @Wrapper
    fun Long.daysUntilNow(): Int {
        return daysUntil(
            untilDate = Clock.System.now().toEpochMilliseconds()
        )
    }

    fun Long.daysUntil(
        untilDate: Long,
    ): Int {
        return computeUntilGap(
            baseDate = this,
            untilDate = untilDate,
            untilGap = DateTimeUnit.DAY
        )
    }

    @Wrapper
    fun Long.weeksUntilNow(): Int {
        return weeksUntil(
            untilDate = Clock.System.now().toEpochMilliseconds()
        )
    }

    fun Long.weeksUntil(
        untilDate: Long,
    ): Int {
        return computeUntilGap(
            baseDate = this,
            untilDate = untilDate,
            untilGap = DateTimeUnit.WEEK
        )
    }

    @Wrapper
    fun Long.monthsUntilNow(): Int {
        return monthsUntil(
            untilDate = Clock.System.now().toEpochMilliseconds()
        )
    }

    fun Long.monthsUntil(
        untilDate: Long,
    ): Int {
        return computeUntilGap(
            baseDate = this,
            untilDate = untilDate,
            untilGap = DateTimeUnit.MONTH
        )
    }

    @Wrapper
    fun Long.quartersUntilNow(): Int {
        return monthsUntil(
            untilDate = Clock.System.now().toEpochMilliseconds()
        )
    }

    fun Long.quartersUntil(
        untilDate: Long,
    ): Int {
        return computeUntilGap(
            baseDate = this,
            untilDate = untilDate,
            untilGap = DateTimeUnit.QUARTER
        )
    }

    @Wrapper
    fun Long.yearsUntilNow(): Int {
        return yearsUntil(
            untilDate = Clock.System.now().toEpochMilliseconds()
        )
    }

    fun Long.yearsUntil(
        untilDate: Long,
    ): Int {
        return computeUntilGap(
            baseDate = this,
            untilDate = untilDate,
            untilGap = DateTimeUnit.YEAR
        )
    }

    @Wrapper
    fun Long.centuriesUntilNow(): Int {
        return centuriesUntil(
            untilDate = Clock.System.now().toEpochMilliseconds()
        )
    }

    fun Long.centuriesUntil(
        untilDate: Long,
    ): Int {
        return computeUntilGap(
            baseDate = this,
            untilDate = untilDate,
            untilGap = DateTimeUnit.CENTURY
        )
    }

    private fun computeUntilGap(
        baseDate: Long,
        untilDate: Long,
        untilGap: DateTimeUnit.DateBased,
    ): Int {
        val firstDate = baseDate.toLocalDate()
        val secondDate = untilDate.toLocalDate()
        return firstDate
            .until(
                other = secondDate,
                unit = untilGap
            )
    }

    private fun Long.toLocalDate(): LocalDate {
        return Instant.fromEpochMilliseconds(this)
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date
    }

}