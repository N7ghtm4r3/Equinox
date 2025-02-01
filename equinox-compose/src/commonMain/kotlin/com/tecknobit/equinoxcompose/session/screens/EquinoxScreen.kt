package com.tecknobit.equinoxcompose.session.screens

import com.tecknobit.equinoxcompose.viewmodels.EquinoxViewModel
import com.tecknobit.equinoxcore.annotations.Structure

/**
 * The `EquinoxScreen` class is useful to create a screen with a lifecycle management similar to the Android's activities.
 * This screen supports to attach the related [androidx.lifecycle.ViewModel] and operate with it
 *
 * Related documentation: [EquinoxScreens.md](https://github.com/N7ghtm4r3/Equinox-Compose/blob/main/documd/EquinoxScreens.md)
 *
 * @property loggerEnabled Whether enabled the logging to log the events occurred in the [ShowContent] composable,
 * it is suggested to disable it in production
 * @property viewModel Support viewmodel to automatically manage the retriever suspension or restarting with the
 * lifecycle events of the screen
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @param V generic type used to allow the use of own viewmodel in custom screens
 *
 * @see EquinoxNoModelScreen
 */
@Structure
abstract class EquinoxScreen<V : EquinoxViewModel>(
    private val loggerEnabled: Boolean = true,
    protected open val viewModel: V,
) : EquinoxNoModelScreen(
    loggerEnabled = loggerEnabled
) {

    /**
     * Method invoked when the [ShowContent] composable has been created.
     *
     * Will be set the [com.tecknobit.equinoxcompose.session.Retriever.activeScreen] as the current screen displayed
     *
     */
    override fun onCreate() {
        super.onCreate()
        viewModel.setActiveContext(this::class)
    }

    /**
     * Method invoked when the [ShowContent] composable has been resumed.
     *
     * Will be restarted the [com.tecknobit.equinoxcompose.session.Retriever.retrieverScope]
     *
     */
    override fun onResume() {
        super.onResume()
        viewModel.restartRetriever()
    }

    /**
     * Method invoked when the [ShowContent] composable has been paused.
     *
     * Will be suspended the [com.tecknobit.equinoxcompose.session.Retriever.retrieverScope]
     */
    override fun onPause() {
        super.onPause()
        viewModel.suspendRetriever()
    }

    /**
     * Method invoked when the [ShowContent] composable has been stopped.
     *
     * Will be suspended the [com.tecknobit.equinoxcompose.session.Retriever.retrieverScope]
     */
    override fun onStop() {
        super.onStop()
        viewModel.suspendRetriever()
    }

    /**
     * Method invoked when the [ShowContent] composable has been destroyed.
     *
     * Will be suspended the [com.tecknobit.equinoxcompose.session.Retriever.retrieverScope]
     */
    override fun onDestroy() {
        super.onDestroy()
        viewModel.suspendRetriever()
    }

    /**
     * Method invoked when the [ShowContent] composable has been disposed.
     *
     * Will be suspended the [com.tecknobit.equinoxcompose.session.Retriever.retrieverScope]
     */
    override fun onDispose() {
        super.onDispose()
        viewModel.suspendRetriever()
    }

}