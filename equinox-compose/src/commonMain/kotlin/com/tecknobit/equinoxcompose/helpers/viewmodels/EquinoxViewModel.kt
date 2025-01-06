package com.tecknobit.equinoxcompose.helpers.viewmodels

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecknobit.apimanager.formatters.JsonHelper
import com.tecknobit.equinoxbackend.FetcherManager
import com.tecknobit.equinoxbackend.FetcherManager.FetcherManagerWrapper
import com.tecknobit.equinoxbackend.Requester.Companion.responseData
import com.tecknobit.equinoxcore.annotations.Structure
import kotlinx.coroutines.launch

/**
 * The **EquinoxViewModel** class is the support class used by the related activities to communicate
 * with the backend and to execute the refreshing routines to update the UI data
 *
 * Related documentation: [EquinoxViewModel](https://github.com/N7ghtm4r3/Equinox/blob/main/documd/compose/apis/EquinoxViewModel.md)
 *
 * @param snackbarHostState The host to launch the snackbar messages
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see ViewModel
 * @see FetcherManagerWrapper
 *
 */
@Structure
abstract class EquinoxViewModel(
    val snackbarHostState: SnackbarHostState? = null,
) : ViewModel(), FetcherManagerWrapper {

    /**
     * **fetcherManager** -> the manager used to fetch the data from the backend
     */
    private val fetcherManager = FetcherManager(viewModelScope)

    /**
     * Function to get whether the [viewModelScope] can start, so if there aren't other jobs that
     * routine is already executing
     *
     * No-any params required
     *
     * @return whether the [viewModelScope] can start as [Boolean]
     */
    override fun canRefresherStart(): Boolean {
        return fetcherManager.canStart()
    }

    /**
     * Function to suspend the current [viewModelScope] to execute other requests to the backend,
     * the [isRefreshing] instance will be set as **false** to allow the restart of the routine after executing
     * the other requests
     *
     * No-any params required
     */
    override fun continueToFetch(
        currentContext: Class<*>,
    ): Boolean {
        return fetcherManager.continueToFetch(currentContext)
    }

    /**
     * Function to execute the refresh routine designed
     *
     * @param currentContext The current context where the [viewModelScope] is executing
     * @param routine The refresh routine to execute
     * @param repeatRoutine: whether repeat the routine or execute a single time
     * @param refreshDelay The delay between the execution of the requests
     */
    override fun execRefreshingRoutine(
        currentContext: Class<*>,
        routine: () -> Unit,
        repeatRoutine: Boolean,
        refreshDelay: Long,
    ) {
        fetcherManager.execute(
            currentContext = currentContext,
            routine = routine,
            repeatRoutine = repeatRoutine,
            refreshDelay = refreshDelay
        )
    }

    /**
     * Function to restart the current [viewModelScope] after other requests has been executed,
     * the [isRefreshing] instance will be set as **true** to deny the restart of the routine after executing
     * the other requests
     *
     * No-any params required
     */
    override fun restartRefresher() {
        fetcherManager.restart()
    }

    /**
     * Function to suspend the current [viewModelScope] to execute other requests to the backend,
     * the [isRefreshing] instance will be set as **false** to allow the restart of the routine after executing
     * the other requests
     *
     * No-any params required
     */
    override fun suspendRefresher() {
        fetcherManager.suspend()
    }

    /**
     * Function to display a response message with a snackbar
     *
     * @param helper The response message received by the backend
     */
    fun showSnackbarMessage(
        helper: JsonHelper,
    ) {
        showSnackbarMessage(
            message = helper.responseData()
        )
    }

    /**
     * Function to display a response message with a snackbar
     *
     * @param message The message to display
     */
    fun showSnackbarMessage(
        message: String,
    ) {
        snackbarHostState?.let { state ->
            viewModelScope.launch {
                state.showSnackbar(message)
            }
        }
    }

}