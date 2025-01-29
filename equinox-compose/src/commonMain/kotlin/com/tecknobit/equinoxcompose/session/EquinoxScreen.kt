package com.tecknobit.equinoxcompose.session

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle.Event
import androidx.lifecycle.Lifecycle.Event.*
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import co.touchlab.kermit.Logger
import com.tecknobit.equinoxcompose.session.EquinoxScreen.EquinoxScreenEvent.ON_DISPOSE
import com.tecknobit.equinoxcompose.session.EquinoxScreen.EquinoxScreenEvent.ON_INIT
import com.tecknobit.equinoxcompose.viewmodels.EquinoxViewModel
import com.tecknobit.equinoxcore.annotations.Structure

/**
 * The **EquinoxScreen** class is useful to create a screen with a lifecycle management similar to the Android's activities
 *
 * Related documentation: [EquinoxScreen.md](https://github.com/N7ghtm4r3/Equinox-Compose/blob/main/documd/EquinoxScreen.md.md)
 *
 * @property loggerEnabled Whether enabled the logging to log the events occurred in the [ShowContent] composable,
 * it is suggested to disable it in production
 * @property viewModel If the screen has got a related viewmodel that will be used to automatically manage the refresher suspension
 * or restarting with the lifecycle events of the screen
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @param V generic type used to allow the use of own viewmodel in custom screens
 *
 */
@Structure
abstract class EquinoxScreen<V : EquinoxViewModel>(
    private val loggerEnabled: Boolean = true,
    protected open val viewModel: V? = null,
) {

    /**
     * *EquinoxScreenEvent* -> available [EquinoxScreen] custom statuses
     */
    enum class EquinoxScreenEvent {

        /**
         * *ON_INIT* -> occurs when the screen has been initialized
         */
        ON_INIT,

        /**
         * *ON_DISPOSE* -> occurs when the screen has been disposed
         */
        ON_DISPOSE

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
    private fun LifecycleManager(
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
     * Method invoked when the [EquinoxScreen] has been instantiated.
     *
     * To use this correctly in have to invoke this in your on `init` block of your custom screen, otherwise
     * will be never invoked
     *
     * ### Usage
     *
     * ```kotlin
     * class CustomScreen : EquinoxScreen() {
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
     * Method invoked when the [ShowContent] composable has been created.
     *
     * If the [viewModel] of the screen is not `null` will be set the [com.tecknobit.equinox.Retriever.activeContext]
     * as the current screen displayed
     *
     */
    protected open fun onCreate() {
        logScreenEvent(
            event = ON_CREATE
        )
        viewModel?.setActiveContext(this::class)
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
     * Method invoked when the [ShowContent] composable has been resumed.
     *
     * If the [viewModel] of the screen is not `null` will be restarted the [com.tecknobit.equinoxcompose.helpers.Retriever.retrieverScope]
     *
     */
    protected open fun onResume() {
        logScreenEvent(
            event = ON_RESUME
        )
        viewModel?.restartRetriever()
    }

    /**
     * Method invoked when the [ShowContent] composable has been paused.
     *
     * If the [viewModel] of the screen is not `null` will be suspended the [com.tecknobit.equinoxcompose.helpers.Retriever.retrieverScope]
     */
    protected open fun onPause() {
        logScreenEvent(
            event = ON_PAUSE
        )
        viewModel?.suspendRetriever()
    }

    /**
     * Method invoked when the [ShowContent] composable has been stopped.
     *
     * If the [viewModel] of the screen is not `null` will be suspended the [com.tecknobit.equinoxcompose.helpers.Retriever.retrieverScope]
     */
    protected open fun onStop() {
        logScreenEvent(
            event = ON_STOP
        )
        viewModel?.suspendRetriever()
    }

    /**
     * Method invoked when the [ShowContent] composable has been destroyed.
     *
     * If the [viewModel] of the screen is not `null` will be suspended the [com.tecknobit.equinoxcompose.helpers.Retriever.retrieverScope]
     */
    protected open fun onDestroy() {
        logScreenEvent(
            event = ON_DESTROY
        )
        viewModel?.suspendRetriever()
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
     * Method invoked when the [ShowContent] composable has been disposed.
     *
     * If the [viewModel] of the screen is not `null` will be suspended the [com.tecknobit.equinoxcompose.helpers.Retriever.retrieverScope]
     */
    protected open fun onDispose() {
        logScreenEvent(
            event = ON_DISPOSE.name
        )
        viewModel?.suspendRetriever()
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

}