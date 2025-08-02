package com.tecknobit.equinoxcore.util

import com.tecknobit.equinoxcore.annotations.Wrapper
import com.tecknobit.equinoxcore.enums.OperatingSystem
import com.tecknobit.equinoxcore.enums.OperatingSystem.LINUX
import java.io.File

private const val OS_NAME_PROPERTY = "os.name"

private const val USER_HOME_PROPERTY = "user.home"

private const val WINDOWS = "Windows"

private const val APPDATA = "APPDATA"

private const val LOCALAPPDATA = "LOCALAPPDATA"

private const val MACOS = "Mac"

@Wrapper
@ExperimentalStdlibApi
fun getApplicationRoamingDataDirectoryPath(): String = getApplicationRoamingDataDirectory().absolutePath

@ExperimentalStdlibApi
fun getApplicationRoamingDataDirectory(): File {
    return retrieveOSDirectory(
        onWindows = { System.getenv(APPDATA) },
        onMacOs = { userHome -> "$userHome/Library/Application Support" },
        onLinux = { System.getenv("\$XDG_DATA_HOME") }
    )
}

@Wrapper
@ExperimentalStdlibApi
fun getApplicationLocalDataDirectoryPath() = getApplicationLocalDataDirectory().absolutePath

@ExperimentalStdlibApi
fun getApplicationLocalDataDirectory(): File {
    return retrieveOSDirectory(
        onWindows = { System.getenv(LOCALAPPDATA) },
        onMacOs = { userHome -> "$userHome/Library/Application Support" },
        onLinux = { userHome -> "$userHome/.local/share" }
    )
}

@Wrapper
@ExperimentalStdlibApi
fun getConfigurationDirectoryPath(): String = getConfigurationDirectory().absolutePath

@ExperimentalStdlibApi
fun getConfigurationDirectory(): File {
    return retrieveOSDirectory(
        onWindows = { System.getenv(APPDATA) },
        onMacOs = { userHome -> "$userHome/Library/Preferences" },
        onLinux = { System.getenv("\$XDG_CONFIG_HOME") }
    )
}

@Wrapper
@ExperimentalStdlibApi
fun getCacheDirectoryPath(): String = getCacheDirectory().absolutePath

@ExperimentalStdlibApi
fun getCacheDirectory(): File {
    return retrieveOSDirectory(
        onWindows = { System.getenv(LOCALAPPDATA) + "\\Cache" },
        onMacOs = { userHome -> "$userHome/Library/Caches" },
        onLinux = { System.getenv("\$XDG_CACHE_HOME") }
    )
}

@Wrapper
@ExperimentalStdlibApi
fun getTempDirectoryPath(): String = getTempDirectory().absolutePath

@ExperimentalStdlibApi
fun getTempDirectory(): File {
    return retrieveOSDirectory(
        onWindows = { System.getenv("TEMP") },
        onMacOs = { System.getenv("TMPDIR") },
        onLinux = { System.getenv("TMPDIR") }
    )
}

@ExperimentalStdlibApi
private fun retrieveOSDirectory(
    onWindows: (String) -> String,
    onMacOs: (String) -> String,
    onLinux: (String) -> String,
): File {
    val userHome = System.getProperty(USER_HOME_PROPERTY)
    var directory = ""
    whenRunningOn(
        onWindows = { directory = onWindows(userHome) },
        onMacOs = { directory = onMacOs(userHome) },
        onLinux = { directory = onLinux(userHome) }
    )
    return File(directory)
}

@ExperimentalStdlibApi
fun whenRunningOn(
    onWindows: () -> Unit,
    onMacOs: () -> Unit,
    onLinux: () -> Unit,
) {
    val runningOn = runningOn()
    when (runningOn) {
        OperatingSystem.WINDOWS -> onWindows()
        OperatingSystem.MACOS -> onMacOs()
        LINUX -> onLinux()
    }
}

@OptIn(ExperimentalStdlibApi::class)
fun runningOn(): OperatingSystem {
    val currentOs = System.getProperty(OS_NAME_PROPERTY)
    return when {
        currentOs.startsWith(WINDOWS) -> OperatingSystem.WINDOWS
        currentOs.startsWith(MACOS) -> OperatingSystem.MACOS
        else -> LINUX
    }
}