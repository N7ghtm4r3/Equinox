package com.tecknobit.equinoxcompose.viewmodels

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecknobit.equinoxcompose.session.Retriever
import com.tecknobit.equinoxcore.annotations.Structure
import com.tecknobit.equinoxcore.network.Requester.Companion.toResponseContent
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonObject
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString
import kotlin.reflect.KClass

/**
 * The `EquinoxViewModel` class is the support class used by the related activities to communicate
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
@Deprecated(
    level = DeprecationLevel.ERROR,
    message = "The package has been moved under session package",
    replaceWith = ReplaceWith(
        expression = "To replace with the session one",
        "com.tecknobit.equinoxcompose.session.viewmodels"
    )
)
abstract class EquinoxViewModel(
    val snackbarHostState: SnackbarHostState? = null,
) : ViewModel(), Retriever.RetrieverWrapper {

    /**
     * `retriever` the manager used to fetch the data from the backend
     */
    private val retriever = Retriever(viewModelScope)

    /**
     * Method used to get whether the [viewModelScope] can start, so if there aren't other jobs that
     * routine is already executing
     *
     *
     * @return whether the [viewModelScope] can start as [Boolean]
     */
    override fun canRetrieverStart(): Boolean {
        return retriever.canStart()
    }

    /**
     * Method used to suspend the current [viewModelScope] to execute other requests to the backend,
     * the [isRefreshing] instance will be set as **false** to allow the restart of the routine after executing
     * the other requests
     *
     */
    override fun continueToRetrieve(
        currentContext: KClass<*>,
    ): Boolean {
        return retriever.continueToRetrieve(currentContext)
    }

    /**
     * Method used to execute the refresh routine designed
     *
     * @param currentContext The current context where the [viewModelScope] is executing
     * @param routine The refresh routine to execute
     * @param repeatRoutine Whether repeat the routine or execute a single time
     * @param refreshDelay The delay between the execution of the requests
     */
    override fun retrieve(
        currentContext: KClass<*>,
        routine: suspend () -> Unit,
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
     * Method used to restart the current [viewModelScope] after other requests has been executed,
     * the [isRefreshing] instance will be set as **true** to deny the restart of the routine after executing
     * the other requests
     *
     */
    override fun restartRetriever() {
        retriever.restart()
    }

    /**
     * Method used to suspend the current [viewModelScope] to execute other requests to the backend,
     * the [isRefreshing] instance will be set as **false** to allow the restart of the routine after executing
     * the other requests
     *
     */
    override fun suspendRetriever() {
        retriever.suspend()
    }

    /**
     * Method used to display a response message with a snackbar
     *
     * @param response The response from retrieve the message to display
     * @param actionLabel Optional action label to show as button in the Snackbar
     * @param onDismiss Is the callback to invoke when the snackbar dismissed without the user click of the [actionLabel]
     * @param onActionPerformed Is the callback to invoke when the user clicked the [actionLabel]
     * @param withDismissAction A boolean to show a dismiss action in the Snackbar. This is
     *   recommended to be set to true for better accessibility when a Snackbar is set with a
     *   [SnackbarDuration.Indefinite]
     * @param duration Duration to control how long snackbar will be shown in [SnackbarHost], either
     *   [SnackbarDuration.Short], [SnackbarDuration.Long] or [SnackbarDuration.Indefinite]
     */
    fun showSnackbarMessage(
        response: JsonObject,
        actionLabel: String? = null,
        onDismiss: (() -> Unit)? = null,
        onActionPerformed: (() -> Unit)? = null,
        withDismissAction: Boolean = false,
        duration: SnackbarDuration = if (actionLabel == null)
            SnackbarDuration.Short
        else
            SnackbarDuration.Indefinite,
    ) {
        showSnackbarMessage(
            message = response.toResponseContent(),
            actionLabel = actionLabel,
            onDismiss = onDismiss,
            onActionPerformed = onActionPerformed,
            withDismissAction = withDismissAction,
            duration = duration
        )
    }

    /**
     * Method used to display a response message with a snackbar
     *
     * @param message The resource identifier of the message to display
     * @param actionLabel Optional action label to show as button in the Snackbar
     * @param onDismiss Is the callback to invoke when the snackbar dismissed without the user click of the [actionLabel]
     * @param onActionPerformed Is the callback to invoke when the user clicked the [actionLabel]
     * @param withDismissAction A boolean to show a dismiss action in the Snackbar. This is
     *   recommended to be set to true for better accessibility when a Snackbar is set with a
     *   [SnackbarDuration.Indefinite]
     * @param duration Duration to control how long snackbar will be shown in [SnackbarHost], either
     *   [SnackbarDuration.Short], [SnackbarDuration.Long] or [SnackbarDuration.Indefinite]
     */
    fun showSnackbarMessage(
        message: StringResource,
        actionLabel: StringResource? = null,
        onDismiss: (() -> Unit)? = null,
        onActionPerformed: (() -> Unit)? = null,
        withDismissAction: Boolean = false,
        duration: SnackbarDuration = if (actionLabel == null)
            SnackbarDuration.Short
        else
            SnackbarDuration.Indefinite,
    ) {
        viewModelScope.launch {
            showSnackbarMessage(
                message = getString(
                    resource = message
                ),
                actionLabel = if (actionLabel != null) {
                    getString(
                        resource = actionLabel
                    )
                } else
                    null,
                onDismiss = onDismiss,
                onActionPerformed = onActionPerformed,
                withDismissAction = withDismissAction,
                duration = duration
            )
        }
    }

    /**
     * Method used to display a response message with a snackbar
     *
     * @param message The message to display
     * @param actionLabel Optional action label to show as button in the Snackbar
     * @param onDismiss Is the callback to invoke when the snackbar dismissed without the user click of the [actionLabel]
     * @param onActionPerformed Is the callback to invoke when the user clicked the [actionLabel]
     * @param withDismissAction A boolean to show a dismiss action in the Snackbar. This is
     *   recommended to be set to true for better accessibility when a Snackbar is set with a
     *   [SnackbarDuration.Indefinite]
     * @param duration Duration to control how long snackbar will be shown in [SnackbarHost], either
     *   [SnackbarDuration.Short], [SnackbarDuration.Long] or [SnackbarDuration.Indefinite]
     */
    fun showSnackbarMessage(
        message: String,
        actionLabel: String? = null,
        onDismiss: (() -> Unit)? = null,
        onActionPerformed: (() -> Unit)? = null,
        withDismissAction: Boolean = false,
        duration: SnackbarDuration = if (actionLabel == null)
            SnackbarDuration.Short
        else
            SnackbarDuration.Indefinite,
    ) {
        snackbarHostState?.let { state ->
            viewModelScope.launch {
                state.showSnackbar(
                    message = message,
                    actionLabel = actionLabel,
                    withDismissAction = withDismissAction,
                    duration = duration,
                ).let { snackbarResult ->
                    when (snackbarResult) {
                        Dismissed -> onDismiss?.invoke()
                        ActionPerformed -> onActionPerformed?.invoke()
                    }
                }
            }
        }
    }

}