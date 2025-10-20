![Static Badge](https://img.shields.io/badge/desktop-006874?link=https%3A%2F%2Fimg.shields.io%2Fbadge%2Fandroid-4280511051)

These APIs provide utility methods to retrieve information about the current operating system where the application is 
running on 

## getApplicationRoamingDataDirectoryPath

### Description

Method used to get the `application roaming data` directory path

The directory retrieved is:

- Windows: `%APPDATA%`
- macOS: `~/Library/Application Support`
- Linux: `$XDG_DATA_HOME` or `~/.local/share` as default

### Usage

```kotlin
val path: String = getApplicationRoamingDataDirectoryPath()

println(path) // e.g %APPDATA%
```

## getApplicationRoamingDataDirectory

### Description

Method used to get the `application roaming data` directory

The directory retrieved is:

- Windows: `%APPDATA%`
- macOS: `~/Library/Application Support`
- Linux: `$XDG_DATA_HOME` or `~/.local/share` as default

### Usage

```kotlin
val directory: File = getApplicationRoamingDataDirectory()

println(directory.name) // e.g %APPDATA%
```

## getApplicationLocalDataDirectoryPath

### Description

Method used to get the `application local data` directory path

The directory retrieved is:

- Windows: `%LOCALAPPDATA%`
- macOS: `~/Library/Application Support`
- Linux: `~/.local/share`

### Usage

```kotlin
val path: String = getApplicationLocalDataDirectoryPath()

println(path) // e.g %LOCALAPPDATA%
```

## getApplicationLocalDataDirectory

### Description

Method used to get the `application local data` directory

The directory retrieved is:

- Windows: `%LOCALAPPDATA%`
- macOS: `~/Library/Application Support`
- Linux: `~/.local/share`

### Usage

```kotlin
val directory: File = getApplicationLocalDataDirectory()

println(directory.name) // e.g %LOCALAPPDATA%
```

## getConfigurationDirectoryPath

### Description

Method used to get the `configuration` directory path

The directory retrieved is:

- Windows: `%APPDATA%`
- macOS: `~/Library/Preferences`
- Linux: `$XDG_CONFIG_HOME` or `~/.config` as default

### Usage

```kotlin
val path: String = getConfigurationDirectoryPath()

println(path) // e.g %APPDATA%
```

## getConfigurationDirectory

### Description

Method used to get the `configuration` directory

The directory retrieved is:

- Windows: `%APPDATA%`
- macOS: `~/Library/Preferences`
- Linux: `$XDG_CONFIG_HOME` or `~/.config` as default

### Usage

```kotlin
val directory: File = getConfigurationDirectory()

println(directory.name) // e.g %APPDATA%
```

## getCacheDirectoryPath

### Description

* Method used to get the `cache` directory path

The directory retrieved is:

- Windows: `%LOCALAPPDATA%\Cache`
- macOS: `~/Library/Caches`
- Linux: `$XDG_CACHE_HOME` or `~/.cache` as default

### Usage

```kotlin
val path: String = getCacheDirectoryPath()

println(path) // e.g %%LOCALAPPDATA%\Cache%
```

## getCacheDirectory

### Description

* Method used to get the `cache` directory

The directory retrieved is:

- Windows: `%LOCALAPPDATA%\Cache`
- macOS: `~/Library/Caches`
- Linux: `$XDG_CACHE_HOME` or `~/.cache` as default

### Usage

```kotlin
val directory: File = getCacheDirectory()

println(directory.name) // e.g %LOCALAPPDATA%\Cache`
```

## getTempDirectoryPath

### Description

* Method used to get the `temp` directory path

The directory retrieved is:

- Windows: `%USERPROFILE%\AppData\Local\Temp`
- macOS: `~/tmp`
- Linux: `~/tmp`

### Usage

```kotlin
val path: String = getTempDirectoryPath()

println(path) // e.g %USERPROFILE%\AppData\Local\Temp
```

## getTempDirectory

### Description

* Method used to get the `temp` directory

The directory retrieved is:

- Windows: `%USERPROFILE%\AppData\Local\Temp`
- macOS: `~/tmp`
- Linux: `~/tmp`

### Usage

```kotlin
val directory: File = getTempDirectory()

println(directory.name) // e.g %USERPROFILE%\AppData\Local\Temp%
```

## whenIsRunningOn

Utility method used to execute a specific callback for the operating system which the application is currently
running on, for example:

```kotlin
whenIsRunningOn(
    windows = {
        println("Hi from Windows!")
    },
    macOs = {
        println("Hi from macOS!")
    },
    linux = {
        println("Hi from linux!")
    }
)
```

## isRunningOn

### OperatingSystem

| **OS**      | **Description**                                                  |
|-------------|------------------------------------------------------------------|
| **WINDOWS** | All the operating systems which are part of the Windows's family |
| **MACOS**   | All the operating systems which are part of the macOS's family   |
| **LINUX**   | All the distros which are part of the Linux's family             |

### Usage

Retrieves the operating system which the application is currently running on, for example:

```kotlin
val isWindows = isRunningOn() == WINDOWS
if(isWindows) {
    println("Hi from Windows!")
}
```