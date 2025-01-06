## TimeFormatter

### Usage

#### Time parsing

```kotlin
// format the current timestamp value as String
val currentTimestamp = Timeformatter.formatNowAsString(
    pattern = defaultPattern // custom pattern if needed
)

// format a long value (date value) as String
val january6 = 1736198062000.formatAsDateString(
    pattern = defaultPattern // custom pattern if needed
) // will be 06/01/2025 21:14:22

// format a string value (date value) as Long
val january6 = "06/01/2025 21:14:22".formatAsTimestamp(
    pattern = defaultPattern // custom pattern if needed
) // will be 06/01/2025 21:14:22
```

#### Temporal gap calculation

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

### Supported temporal gap

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

The other apis will be gradually released

## Authors

- [@N7ghtm4r3](https://www.github.com/N7ghtm4r3)

## Support

If you need help using the library or encounter any problems or bugs, please contact us via the following links:

- Support via <a href="mailto:infotecknobitcompany@gmail.com">email</a>
- Support via <a href="https://github.com/N7ghtm4r3/Equinox/issues/new">GitHub</a>

Thank you for your help!

## Badges

[![](https://img.shields.io/badge/Google_Play-414141?style=for-the-badge&logo=google-play&logoColor=white)](https://play.google.com/store/apps/developer?id=Tecknobit)
[![Twitter](https://img.shields.io/badge/Twitter-1DA1F2?style=for-the-badge&logo=twitter&logoColor=white)](https://twitter.com/tecknobit)

[![](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)](https://spring.io/projects/spring-boot)
[![](https://img.shields.io/badge/Jetpack%20Compose-4285F4.svg?style=for-the-badge&logo=Jetpack-Compose&logoColor=white)](https://www.jetbrains.com/lp/compose-multiplatform/)

[![](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/)
[![](https://img.shields.io/badge/Kotlin-B125EA?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)

## Donations

If you want support project and developer

| Crypto                                                                                              | Address                                          | Network  |
|-----------------------------------------------------------------------------------------------------|--------------------------------------------------|----------|
| ![](https://img.shields.io/badge/Bitcoin-000000?style=for-the-badge&logo=bitcoin&logoColor=white)   | **3H3jyCzcRmnxroHthuXh22GXXSmizin2yp**           | Bitcoin  |
| ![](https://img.shields.io/badge/Ethereum-3C3C3D?style=for-the-badge&logo=Ethereum&logoColor=white) | **0x1b45bc41efeb3ed655b078f95086f25fc83345c4**   | Ethereum |
| ![](https://img.shields.io/badge/Solana-000?style=for-the-badge&logo=Solana&logoColor=9945FF)       | **AtPjUnxYFHw3a6Si9HinQtyPTqsdbfdKX3dJ1xiDjbrL** | Solana   |

If you want support project and developer
with <a href="https://www.paypal.com/donate/?hosted_button_id=5QMN5UQH7LDT4">PayPal</a>

Copyright © 2025 Tecknobit
