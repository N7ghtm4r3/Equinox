package com.tecknobit.equinoxcompose.session.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle.Event
import androidx.lifecycle.Lifecycle.Event.*
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import co.touchlab.kermit.Logger
import com.tecknobit.equinoxcompose.session.screens.EquinoxNoModelScreen.EquinoxScreenEvent.ON_DISPOSE
import com.tecknobit.equinoxcompose.session.screens.EquinoxNoModelScreen.EquinoxScreenEvent.ON_INIT
import com.tecknobit.equinoxcore.annotations.Structure

/**
 * The `EquinoxNoModelScreen` class is useful to create a screen with a lifecycle management similar to the Android's activities
 *
 * Related documentation: [EquinoxScreens.md](https://github.com/N7ghtm4r3/Equinox-Compose/blob/main/documd/EquinoxScreens.md)
 *
 * @property loggerEnabled Whether enabled the logging to log the events occurred in the [ShowContent] composable,
 * it is suggested to disable it in production
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 1.0.7
 *
 */
@Structure
abstract class EquinoxNoModelScreen(
    private val loggerEnabled: Boolean = true,
) {

    /**
     * `EquinoxScreenEvent` available [EquinoxNoModelScreen] custom statuses
     */
    enum class EquinoxScreenEvent {

        /**
         * `ON_INIT` occurs when the screen has been initialized
         */
        ON_INIT,

        /**
         * `ON_DISPOSE` occurs when the screen has been disposed
         */
        ON_DISPOSE

    }

    companion object {

        /**
         * `MAX_CONTAINER_WIDTH` constant value used to give a max dimension to container for the large screens
         */
        val MAX_CONTAINER_WIDTH = 1280.dp

    }

    /**
     * Method to display the content of the screen
     *
     * Its lifecycle will be managed invoking the [LifecycleManager]
     */
    @Composable
    fun ShowContent() {
        LifecycleManager()
        CollectStates()
        ArrangeScreenContent()
    }

    /**
     * Method to arrange the content of the screen to display
     */
    @Composable
    protected abstract fun ArrangeScreenContent()

    /**
     * Method to manage the lifecycle of the composable where this Method has been invoked
     *
     * @param lifecycleOwner The owner of the current lifecycle
     */
    @Composable
    protected fun LifecycleManager(
        lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    ) {
        DisposableEffect(lifecycleOwner) {
            val lifecycle = lifecycleOwner.lifecycle
            val observer = LifecycleEventObserver { _, event ->
                when (event) {
                    ON_CREATE -> onCreate()
                    ON_START -> onStart()
                    ON_RESUME -> onResume()
                    ON_PAUSE -> onPause()
                    ON_STOP -> onStop()
                    ON_DESTROY -> onDestroy()
                    else -> onAny()
                }
            }
            lifecycle.addObserver(observer)
            onDispose {
                onDispose()
                lifecycle.removeObserver(observer)
            }
        }
    }

    /**
     * Method invoked when the [EquinoxNoModelScreen] has been instantiated.
     *
     * To use this correctly in have to invoke this in your on `init` block of your custom screen, otherwise
     * will be never invoked
     *
     * ### Usage
     *
     * ```kotlin
     * class CustomScreen : EquinoxNoModelScreen() {
     *
     * 	init {
     * 		onInit();
     * 	}
     *
     * 	override fun onInit() {
     * 		super.onInit();
     * 		// your content here
     * 	}
     *
     * }
     * ```
     */
    protected open fun onInit() {
        logScreenEvent(
            event = ON_INIT.name
        )
    }

    /**
     * Method invoked when the [ShowContent] composable has been created
     */
    protected open fun onCreate() {
        logScreenEvent(
            event = ON_CREATE
        )
    }

    /**
     * Method invoked when the [ShowContent] composable has been started
     */
    protected open fun onStart() {
        logScreenEvent(
            event = ON_START
        )
    }

    /**
     * Method invoked when the [ShowContent] composable has been resumed
     *
     */
    protected open fun onResume() {
        logScreenEvent(
            event = ON_RESUME
        )
    }

    /**
     * Method invoked when the [ShowContent] composable has been paused
     */
    protected open fun onPause() {
        logScreenEvent(
            event = ON_PAUSE
        )
    }

    /**
     * Method invoked when the [ShowContent] composable has been stopped
     */
    protected open fun onStop() {
        logScreenEvent(
            event = ON_STOP
        )
    }

    /**
     * Method invoked when the [ShowContent] composable has been destroyed
     */
    protected open fun onDestroy() {
        logScreenEvent(
            event = ON_DESTROY
        )
    }

    /**
     * Method invoked when in the [ShowContent] composable has occurred any of the possible events
     */
    protected open fun onAny() {
        logScreenEvent(
            event = ON_ANY
        )
    }

    /**
     * Method invoked when the [ShowContent] composable has been disposed
     */
    protected open fun onDispose() {
        logScreenEvent(
            event = ON_DISPOSE.name
        )
    }

    /**
     * Method to log the event occurred in the current screen
     *
     * @param event The event occurred
     */
    protected fun logScreenEvent(
        event: Event,
    ) {
        logScreenEvent(
            event = event.name
        )
    }

    /**
     * Method to log the event occurred in the current screen
     *
     * @param event The event occurred
     */
    protected fun logScreenEvent(
        event: String,
    ) {
        if (loggerEnabled) {
            Logger.i(
                tag = this::class.simpleName!!,
                messageString = "Current status -> $event"
            )
        }
    }

    /**
     * Method to collect or instantiate the states of the screen
     */
    @Composable
    protected abstract fun CollectStates()

    /**
     * Method to collect or instantiate the states of the screen after a loading required to correctly assign an
     * initial value to the states. For example in your custom screen:
     * ```kotlin
     * @Composable
     * override fun ArrangeScreenContent() {
     * LoadingItemUI(
     *      loadingRoutine = {
     *          // your loading routine
     *      },
     *      contentLoaded = {
     *          // invoke this method
     *          CollectStatesAfterLoading()
     *          // rest of the content
     *      }
     *    )
     * }
     *
     * // override and customize it with your logic
     * @Composable
     * @NonRestartableComposable
     * override fun CollectStatesAfterLoading() {
     *    // initialize your states depending of the loaded value
     * }
     * ```
     */
    @Composable
    @NonRestartableComposable
    protected open fun CollectStatesAfterLoading() {
    }

    /**
     * Method used to wait asynchronously the change of the state of a `null` item to its non-null value.
     *
     * This method is useful in those scenarios where the content to display depends on the non-null item.
     *
     * For example:
     * ```kotlin
     * @Composable
     * override fun ArrangeScreenContent() {
     *     val text = remember { mutableStateOf<String?>(null) }
     *
     *     // simulated waiting
     *     LaunchedEffect(Unit) {
     *         delay(1000)
     *         text.value = "Hello World!"
     *     }
     *
     *     awaitNullItemLoaded(
     *         itemToWait = text.value,
     *         extras = null, // any extras condition
     *         loadedContent = { nullSafeText -> // non-null value
     *             // the UI content which depends on the text state
     *             Text(
     *                 text = nullSafeText
     *             )
     *         }
     *     )
     * }
     * ```
     *
     * @param itemToWait The item initially null to wait
     * @param extras Extra conditions to apply to show the [loadedContent] that depends on the non-null value of the
     * [itemToWait]
     * @param loadedContent The content to display when the [itemToWait] is not more `null`
     */
    @Composable
    @NonRestartableComposable
    protected fun <T> awaitNullItemLoaded(
        itemToWait: T?,
        extras: (T) -> Boolean = { true },
        loadedContent: @Composable (T) -> Unit,
    ) {
        var loaded by remember { mutableStateOf(false) }
        LaunchedEffect(itemToWait) {
            loaded = itemToWait != null && extras(itemToWait)
        }
        AnimatedVisibility(
            visible = loaded
        ) {
            loadedContent(itemToWait!!)
        }
    }

}