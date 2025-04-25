package com.tecknobit.equinoxcompose.session.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
 * Related documentation: [EquinoxScreens.md](https://github.com/N7ghtm4r3/Equinox/blob/main/documd/compose/apis/EquinoxScreens.md)
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
     * Method used to display the content of the screen
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
     * Method used to arrange the content of the screen to display
     */
    @Composable
    protected abstract fun ArrangeScreenContent()

    /**
     * Method used to manage the lifecycle of the composable where this Method has been invoked
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
     * Method used to log the event occurred in the current screen
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
     * Method used to log the event occurred in the current screen
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
     * Method used to collect or instantiate the states of the screen
     */
    @Composable
    protected abstract fun CollectStates()

    /**
     * Method used to collect or instantiate the states of the screen after a loading required to correctly assign an
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
     * override fun CollectStatesAfterLoading() {
     *    // initialize your states depending of the loaded value
     * }
     * ```
     */
    @Composable
    protected open fun CollectStatesAfterLoading() {
    }

}