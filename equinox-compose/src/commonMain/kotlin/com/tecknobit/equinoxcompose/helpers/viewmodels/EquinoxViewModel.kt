package com.tecknobit.equinoxcompose.helpers.viewmodels

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecknobit.equinoxcompose.helpers.Retriever
import com.tecknobit.equinoxcore.annotations.Structure
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

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
 * @see Retriever.RetrieverWrapper
 *
 */
@Structure
abstract class EquinoxViewModel(
    val snackbarHostState: SnackbarHostState? = null,
) : ViewModel(), Retriever.RetrieverWrapper {

    /**
     * **retriever** -> the manager used to fetch the data from the backend
     */
    private val retriever = Retriever(viewModelScope)

    /**
     * Function to get whether the [viewModelScope] can start, so if there aren't other jobs that
     * routine is already executing
     *
     * No-any params required
     *
     * @return whether the [viewModelScope] can start as [Boolean]
     */
    override fun canRetrieverStart(): Boolean {
        return retriever.canStart()
    }

    /**
     * Function to suspend the current [viewModelScope] to execute other requests to the backend,
     * the [isRefreshing] instance will be set as **false** to allow the restart of the routine after executing
     * the other requests
     *
     * No-any params required
     */
    override fun continueToRetrieve(
        currentContext: KClass<*>,
    ): Boolean {
        return retriever.continueToRetrieve(currentContext)
    }

    /**
     * Function to execute the refresh routine designed
     *
     * @param currentContext The current context where the [viewModelScope] is executing
     * @param routine The refresh routine to execute
     * @param repeatRoutine Whether repeat the routine or execute a single time
     * @param refreshDelay The delay between the execution of the requests
     */
    override fun retrieve(
        currentContext: KClass<*>,
        routine: () -> Unit,
        repeatRoutine: Boolean,
        refreshDelay: Long,
    ) {
        retriever.execute(
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
    override fun restartRetriever() {
        retriever.restart()
    }

    /**
     * Function to suspend the current [viewModelScope] to execute other requests to the backend,
     * the [isRefreshing] instance will be set as **false** to allow the restart of the routine after executing
     * the other requests
     *
     * No-any params required
     */
    override fun suspendRetriever() {
        retriever.suspend()
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