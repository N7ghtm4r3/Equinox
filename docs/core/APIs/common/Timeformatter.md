This API handles temporal values and provides easy access to custom formats for display or further use

## Usage

### Time parsing

#### Format the current timestamp value as String

=== "Kotlin"

    ```kotlin
    val currentTimestamp = Timeformatter.formatNowAsString(
        pattern = defaultPattern // custom pattern if needed
    )
    ```

=== "Java"

    ```java
    String currentTimestamp = TimeFormatter.formatNowAsString(
        TimeFormatter.COMPLETE_EUROPEAN_DATE_PATTERN // default pattern
    );
    ```

#### Format a long date value as String

=== "Kotlin"

    ```kotlin
    val january6 = 1736198062000.toDateString(
        pattern = defaultPattern // custom pattern if needed
    ) // will be 06/01/2025 21:14:22
    ```

=== "Java"

    ```java
    String january6 = TimeFormatter.toDateString(
        1736198062000L,
        null, // default invalid time Value
        TimeFormatter.COMPLETE_EUROPEAN_DATE_PATTERN // default pattern
    );
    ```

#### Format a string date value as Long

=== "Kotlin"

    ```kotlin
    val january6 = "06/01/2025 21:14:22".toTimestamp(
        pattern = defaultPattern // custom pattern if needed
    ) // will be 1736198062000
    ```

=== "Java"

    ```java
    long january6 = TimeFormatter.toTimestamp(
        "06/01/2025 21:14:22",
        null, // default invalid time Value
        TimeFormatter.COMPLETE_EUROPEAN_DATE_PATTERN // default pattern
    ); // will be 1736198062000
    ```

### Temporal gap calculation

```kotlin
// 6 January 
val january6 = 1736191911000L

// 16 January 
val january16 = 1737055946000L

// the days gap will be 10 days
val daysGap = genuary6.daysUntil(
    untilDate = genuary16
)
```

#### Supported temporal gap

| **Type**       | **Description**                |
|----------------|--------------------------------|
| `nanoseconds`  | Nanoseconds between two dates  |
| `milliseconds` | Milliseconds between two dates |
| `seconds`      | Seconds between two dates      |
| `minutes`      | Minutes between two dates      |
| `hours`        | Hours between two dates        |
| `days`         | Days between two dates         |
| `weeks`        | Weeks between two dates        |
| `months`       | Months between two dates       |
| `quarters`     | Quarters between two dates     |
| `years`        | Years between two dates        |
| `centuries`    | Centuries between two dates    |