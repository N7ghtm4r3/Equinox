package com.tecknobit.equinoxcore.util

import com.tecknobit.equinoxcore.annotations.Wrapper
import com.tecknobit.equinoxcore.enums.OperatingSystem
import com.tecknobit.equinoxcore.enums.OperatingSystem.LINUX
import kotlinx.io.IOException
import java.io.File

/**
 * `OS_NAME_PROPERTY` constant for the `os.name` value
 */
private const val OS_NAME_PROPERTY = "os.name"

/**
 * `USER_HOME_PROPERTY` constant for the `user.home` value
 */
private const val USER_HOME_PROPERTY = "user.home"

/**
 * `WINDOWS` constant for the `Windows` value
 */
private const val WINDOWS = "Windows"

/**
 * `APPDATA` constant for the `APPDATA` value
 */
private const val APPDATA = "APPDATA"

/**
 * `LOCALAPPDATA` constant for the `LOCALAPPDATA` value
 */
private const val LOCALAPPDATA = "LOCALAPPDATA"

/**
 * `Mac` constant for the `Mac` value
 */
private const val MACOS = "Mac"

/**
 * Method used to get the `application roaming data` directory path.
 *
 * The directory retrieved is:
 *
 * - Windows: `%APPDATA%`
 * - macOS: `~/Library/Application Support`
 * - Linux: `$XDG_DATA_HOME` or `~/.local/share` as default
 *
 * @return the application roaming data directory path as [String]
 *
 * @throws IOException when could not locate the application roaming data directory
 *
 * @since 1.1.5
 */
@Wrapper
fun getApplicationRoamingDataDirectoryPath(): String = getApplicationRoamingDataDirectory().absolutePath

/**
 * Method used to get the `application roaming data` directory.
 *
 * The directory retrieved is:
 *
 * - Windows: `%APPDATA%`
 * - macOS: `~/Library/Application Support`
 * - Linux: `$XDG_DATA_HOME` or `~/.local/share` as default
 *
 * @return the application roaming data directory as [File]
 *
 * @throws IOException when could not locate the application roaming data directory
 *
 * @since 1.1.5
 */
fun getApplicationRoamingDataDirectory(): File {
    return retrieveOSDirectory(
        onWindows = { System.getenv(APPDATA) },
        onMacOs = { userHome -> "$userHome/Library/Application Support" },
        onLinux = { userHome -> System.getenv("XDG_DATA_HOME") ?: "$userHome/.local/share" }
    )
}

/**
 * Method used to get the `application local data` directory path.
 *
 * The directory retrieved is:
 *
 * - Windows: `%LOCALAPPDATA%`
 * - macOS: `~/Library/Application Support`
 * - Linux: `~/.local/share`
 *
 * @return the application local data directory path as [String]
 *
 * @throws IOException when could not locate the application local data path directory
 *
 * @since 1.1.5
 */
@Wrapper
fun getApplicationLocalDataDirectoryPath(): String = getApplicationLocalDataDirectory().absolutePath

/**
 * Method used to get the `application local data` directory.
 *
 * The directory retrieved is:
 *
 * - Windows: `%LOCALAPPDATA%`
 * - macOS: `~/Library/Application Support`
 * - Linux: `~/.local/share`
 *
 * @return the application local data directory as [File]
 *
 * @throws IOException when could not locate the application local data directory
 *
 * @since 1.1.5
 */
fun getApplicationLocalDataDirectory(): File {
    return retrieveOSDirectory(
        onWindows = { System.getenv(LOCALAPPDATA) },
        onMacOs = { userHome -> "$userHome/Library/Application Support" },
        onLinux = { userHome -> "$userHome/.local/share" }
    )
}

/**
 * Method used to get the `configuration` directory path.
 *
 * The directory retrieved is:
 *
 * - Windows: `%APPDATA%`
 * - macOS: `~/Library/Preferences`
 * - Linux: `$XDG_CONFIG_HOME` or `~/.config` as default
 *
 * @return the configuration directory path as [String]
 *
 * @throws IOException when could not locate the configuration directory
 *
 * @since 1.1.5
 */
@Wrapper
fun getConfigurationDirectoryPath(): String = getConfigurationDirectory().absolutePath

/**
 * Method used to get the `configuration` directory.
 *
 * The directory retrieved is:
 *
 * - Windows: `%APPDATA%`
 * - macOS: `~/Library/Preferences`
 * - Linux: `$XDG_CONFIG_HOME` or `~/.config` as default
 *
 * @return the configuration directory as [File]
 *
 * @throws IOException when could not locate the configuration directory
 *
 * @since 1.1.5
 */
fun getConfigurationDirectory(): File {
    return retrieveOSDirectory(
        onWindows = { System.getenv(APPDATA) },
        onMacOs = { userHome -> "$userHome/Library/Preferences" },
        onLinux = { userHome -> System.getenv("XDG_CONFIG_HOME") ?: "$userHome/.config" }
    )
}

/**
 * Method used to get the `cache` directory path.
 *
 * The directory retrieved is:
 *
 * - Windows: `%LOCALAPPDATA%\Cache`
 * - macOS: `~/Library/Caches`
 * - Linux: `$XDG_CACHE_HOME` or `~/.cache` as default
 *
 * @return the cache directory path as [String]
 *
 * @throws IOException when could not locate the cache directory
 *
 * @since 1.1.5
 */
@Wrapper
fun getCacheDirectoryPath(): String = getCacheDirectory().absolutePath

/**
 * Method used to get the `cache` directory.
 *
 * The directory retrieved is:
 *
 * - Windows: `%LOCALAPPDATA%\Cache`
 * - macOS: `~/Library/Caches`
 * - Linux: `$XDG_CACHE_HOME` or `~/.cache` as default
 *
 * @return the cache directory as [File]
 *
 * @throws IOException when could not locate the cache directory
 *
 * @since 1.1.5
 */
fun getCacheDirectory(): File {
    return retrieveOSDirectory(
        onWindows = { File(System.getenv(LOCALAPPDATA), "Cache").absolutePath },
        onMacOs = { userHome -> "$userHome/Library/Caches" },
        onLinux = { userHome -> System.getenv("XDG_CACHE_HOME") ?: "$userHome/.cache" }
    )
}

/**
 * Method used to get the `temp` directory path.
 *
 * The directory retrieved is:
 *
 * - Windows: `%USERPROFILE%\AppData\Local\Temp`
 * - macOS: `~/tmp`
 * - Linux: `~/tmp`
 *
 * @return the temp directory path as [String]
 *
 * @throws IOException when could not locate the temp directory
 *
 * @since 1.1.5
 */
@Wrapper
fun getTempDirectoryPath(): String = getTempDirectory().absolutePath

/**
 * Method used to get the `temp` directory.
 *
 * The directory retrieved is:
 *
 * - Windows: `%USERPROFILE%\AppData\Local\Temp`
 * - macOS: `~/tmp`
 * - Linux: `~/tmp`
 *
 * @return the temp directory as [File]
 *
 * @throws IOException when could not locate the temp directory
 *
 * @since 1.1.5
 */
fun getTempDirectory(): File {
    val tempDirectory = File(System.getProperty("java.io.tmpdir"))
    return tempDirectory
}

/**
 * Method used to retrieve a related directory to the operating system which the application is currently
 * running on
 *
 * @param onWindows The callback to execute if the operating system is [OperatingSystem.WINDOWS]
 * @param onMacOs The callback to execute if the operating system is [OperatingSystem.MACOS]
 * @param onLinux The callback to execute if the operating system is [OperatingSystem.LINUX]
 *
 * @return the specific directory as [File]
 *
 * @throws IOException when could not locate the requested directory
 *
 * @since 1.1.5
 */
private fun retrieveOSDirectory(
    onWindows: (String) -> String?,
    onMacOs: (String) -> String?,
    onLinux: (String) -> String?,
): File {
    val userHome = System.getProperty(USER_HOME_PROPERTY)
    var directory: String? = null
    whenIsRunningOn(
        windows = { directory = onWindows(userHome) },
        macOs = { directory = onMacOs(userHome) },
        linux = { directory = onLinux(userHome) }
    )
    if (directory == null)
        throw IOException("Could not locate the requested directory")
    return File(directory!!)
}

/**
 * Utility method used to execute a specific callback for the operating system which the application is currently
 * running on
 *
 * @param windows The callback to execute if the operating system is [OperatingSystem.WINDOWS]
 * @param macOs The callback to execute if the operating system is [OperatingSystem.MACOS]
 * @param linux The callback to execute if the operating system is [OperatingSystem.LINUX]
 *
 * @since 1.1.5
 */
fun whenIsRunningOn(
    windows: () -> Unit,
    macOs: () -> Unit,
    linux: () -> Unit,
) {
    val runningOn = isRunningOn()
    when (runningOn) {
        OperatingSystem.WINDOWS -> windows()
        OperatingSystem.MACOS -> macOs()
        LINUX -> linux()
    }
}

/**
 * Method used to retrieve the operating system which the application is currently running on
 *
 * @return the operating system which the application is currently running on as [OperatingSystem]
 *
 * @since 1.1.5
 */
fun isRunningOn(): OperatingSystem {
    val currentOs = System.getProperty(OS_NAME_PROPERTY)
    return when {
        currentOs.startsWith(WINDOWS) -> OperatingSystem.WINDOWS
        currentOs.startsWith(MACOS) -> OperatingSystem.MACOS
        else -> LINUX
    }
}