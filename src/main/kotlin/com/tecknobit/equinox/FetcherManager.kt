package com.tecknobit.equinox

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class FetcherManager(
    private val refreshRoutine: CoroutineScope
) {

    companion object {

        @Volatile
        lateinit var activeContext: Class<*>

    }

    @Volatile
    private var isRefreshing: Boolean = false

    /**
     * Function to get whether the [refreshRoutine] can start, so if there aren't other jobs that
     * routine is already executing
     * host
     *
     * No-any params required
     *
     * @return whether the [refreshRoutine] can start as [Boolean]
     */
    fun canStart(): Boolean {
        return !isRefreshing
    }

    /**
     * Function to suspend the current [refreshRoutine] to execute other requests to the backend,
     * the [isRefreshing] will be set as **false** to allow the restart of the routine after executing
     * the other requests
     *
     * No-any params required
     */
    fun suspend() {
        refreshRoutine.coroutineContext.cancelChildren()
        isRefreshing = false
    }

    /**
     * Function to restart the current [refreshRoutine] after other requests has been executed,
     * the [isRefreshing] will be set as **true** to deny the restart of the routine after executing
     * the other requests
     *
     * No-any params required
     */
    fun restart() {
        isRefreshing = true
    }

    fun execute(
        block: suspend CoroutineScope.() -> Unit
    ) {
        if(canStart()) {
            restart()
            refreshRoutine.launch(
                block = block
            )
        }
    }

    /**
     * Function to check if the [refreshRoutine] can continue to refresh or need to be stopped, this for
     * example when the UI displayed changes and the requests to refresh the UI data
     * also changes
     *
     * @param currentContext: the current context where the [refreshRoutine] is executing
     *
     * @return whether the [refreshRoutine] can continue to refresh as [Boolean]
     */
    fun continueToFetch(
        currentContext: Class<*>
    ) : Boolean {
        return activeContext == currentContext
    }

    interface FetcherConnector {

        /**
         * Function to get whether the [refreshRoutine] can start, so if there aren't other jobs that
         * routine is already executing
         * host
         *
         * No-any params required
         *
         * @return whether the [refreshRoutine] can start as [Boolean]
         */
        fun canRefresherStart(): Boolean

        /**
         * Function to suspend the current [refreshRoutine] to execute other requests to the backend,
         * the [isRefreshing] will be set as **false** to allow the restart of the routine after executing
         * the other requests
         *
         * No-any params required
         */
        fun suspendRefresher()

        /**
         * Function to restart the current [refreshRoutine] after other requests has been executed,
         * the [isRefreshing] will be set as **true** to deny the restart of the routine after executing
         * the other requests
         *
         * No-any params required
         */
        fun restartRefresher()

        fun execRefreshingRoutine(
            block: suspend CoroutineScope.() -> Unit
        )

        /**
         * Function to check if the [refreshRoutine] can continue to refresh or need to be stopped, this for
         * example when the UI displayed changes and the requests to refresh the UI data
         * also changes
         *
         * @param currentContext: the current context where the [refreshRoutine] is executing
         *
         * @return whether the [refreshRoutine] can continue to refresh as [Boolean]
         */
        fun continueToFetch(
            currentContext: Class<*>
        ) : Boolean

    }

    /**
     * The **ListFetcher** interface is useful to manage the requests to refresh a list of items
     *
     * @author N7ghtm4r3 - Tecknobit
     */
    interface ListFetcher {

        /**
         * Function to refresh a list of item
         *
         * No-any params required
         */
        fun refreshList()

    }

    /**
     * The **ItemFetcher** interface is useful to manage the requests to refresh a single item
     *
     * @author N7ghtm4r3 - Tecknobit
     */
    interface ItemFetcher {

        /**
         * Function to refresh a single item
         *
         * No-any params required
         */
        fun refreshItem()

    }

    interface GenericFetcher: ItemFetcher, ListFetcher

}