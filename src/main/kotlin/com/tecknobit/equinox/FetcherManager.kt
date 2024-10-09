package com.tecknobit.equinox

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * The **FetcherManager** class is useful to manage the requests to the backend executing these requests
 * with a [CoroutineScope] so to execute in background and not working in the main UI thread
 *
 * @param refreshRoutine: the coroutine used to execute the refresh routines
 *
 * @author N7ghtm4r3 - Tecknobit
 */
class FetcherManager(
    private val refreshRoutine: CoroutineScope,
) {

    companion object {

        /**
         * **activeContext** the active [Class] where a [refreshRoutine] is executing its workflow
         */
        @Volatile
        private lateinit var activeContext: Class<*>

        /**
         * Function to set the current active context where the [refreshRoutine] is executing
         *
         * @param currentContext: the current active context to set
         */
        fun setActiveContext(
            currentContext: Class<*>
        ) {
            activeContext = currentContext
        }

        /**
         * Function to get the current active context where the [refreshRoutine] is executing
         *
         * No-any params required
         */
        fun getActiveContext(): Class<*> {
            return activeContext
        }

    }

    /**
     * **isRefreshing** -> whether the [refreshRoutine] is already refreshing
     */
    private var isRefreshing: Boolean = false

    /**
     * **lastRoutineExecuted** -> the last routine executed by the [execute] method must re-launched after the [suspend]
     * method has been invoked
     */
    private lateinit var lastRoutineExecuted: FetcherRoutine

    /**
     * Function to get whether the [refreshRoutine] can start, so if there aren't other jobs that
     * routine is already executing
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
     * the [isRefreshing] instance will be set as **false** to allow the restart of the routine after executing
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
     * the [isRefreshing] instance will be set as **true** to deny the restart of the routine after executing
     * the other requests
     *
     * No-any params required
     */
    fun restart() {
        if (::lastRoutineExecuted.isInitialized) {
            execute(
                currentContext = lastRoutineExecuted.currentContext,
                routine = lastRoutineExecuted.routine,
                repeatRoutine = lastRoutineExecuted.repeatRoutine,
                refreshDelay = lastRoutineExecuted.refreshDelay
            )
        }
    }

    /**
     * Function to execute the refresh routine designed
     *
     * @param currentContext: the current context where the [refreshRoutine] is executing
     * @param routine: the refresh routine to execute
     * @param repeatRoutine: whether repeat the routine or execute a single time
     * @param refreshDelay: the delay between the execution of the requests
     */
    fun execute(
        currentContext: Class<*>,
        routine: () -> Unit,
        repeatRoutine: Boolean = true,
        refreshDelay: Long = 1000L
    ) {
        if(canStart()) {
            isRefreshing = true
            lastRoutineExecuted = FetcherRoutine(
                currentContext = currentContext,
                routine = routine,
                repeatRoutine = repeatRoutine,
                refreshDelay = refreshDelay
            )
            refreshRoutine.launch {
                if(repeatRoutine) {
                    while (continueToFetch(currentContext)) {
                        routine.invoke()
                        delay(refreshDelay)
                    }
                } else
                    routine.invoke()
            }
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

    /**
     * The **FetcherRoutine** data class is useful to memorize the last routine executed by the [execute] method
     *
     * @param currentContext: the current context where the [refreshRoutine] is executing
     * @param routine: the refresh routine to execute
     * @param repeatRoutine: whether repeat the routine or execute a single time
     * @param refreshDelay: the delay between the execution of the requests
     */
    private data class FetcherRoutine(
        val currentContext: Class<*>,
        val routine: () -> Unit,
        val repeatRoutine: Boolean = true,
        val refreshDelay: Long = 1000L
    )

    /**
     * The **FetcherManagerWrapper** interface is useful for wrapping and facilitating operation with the
     * [FetcherManager], so the inheriting classes will invoke just the wrapper methods for a clean readability
     * of the code, for example:
     *
     * ```kotlin
     *
     * // the super class from which the other classes will be inherited
     * abstract class AbstractClass : FetcherManagerWrapper {
     *
     *     protected val refreshRoutine = CoroutineScope(Dispatchers.Default)
     *
     *     private val fetcherManager = FetcherManager(refreshRoutine)
     *
     *     override fun canRefresherStart(): Boolean {
     *         return fetcherManager.canStart()
     *     }
     *
     *     override fun suspendRefresher() {
     *         fetcherManager.suspend()
     *     }
     *
     *     override fun restartRefresher() {
     *         fetcherManager.restart()
     *     }
     *
     *     override fun continueToFetch(currentContext: Class<*>): Boolean {
     *         return fetcherManager.continueToFetch(currentContext)
     *     }
     *
     *     override fun execRefreshingRoutine(
     *         currentContext: Class<*>,
     *         routine: () -> Unit,
     *         repeatRoutine: Boolean,
     *         refreshDelay: Long
     *     ) {
     *         fetcherManager.execute(
     *             currentContext = currentContext,
     *             routine = routine,
     *             repeatRoutine = repeatRoutine,
     *             refreshDelay = delay
     *         )
     *     }
     *
     * }
     *
     * // an inherit class example
     * class ExampleClass : AbstractClass {
     *
     *     fun example() {
     *         execRefreshingRoutine(
     *             currentContext = this::class.java,
     *             routine = {
     *                 // your routine
     *             }
     *         )
     *     }
     *
     *     fun example1() {
     *         suspendRefresher()
     *     }
     *
     * }
     * ```
     *
     * @author N7ghtm4r3 - Tecknobit
     *
     * @see FetcherManager
     *
     */
    interface FetcherManagerWrapper {

        /**
         * Function to get whether the [refreshRoutine] can start, so if there aren't other jobs that
         * routine is already executing
         *
         * No-any params required
         *
         * @return whether the [refreshRoutine] can start as [Boolean]
         */
        fun canRefresherStart(): Boolean

        /**
         * Function to conditionally suspend the current [refreshRoutine] to execute other requests to the backend,
         * the [isRefreshing] instance will be set as **false** to allow the restart of the routine after executing
         * the other requests
         *
         * @param condition: the condition to verify whether suspend or not the refresher
         */
        fun suspendRefresherIf(
            condition: Boolean
        ) {
            if (condition)
                suspendRefresher()
        }

        /**
         * Function to suspend the current [refreshRoutine] to execute other requests to the backend,
         * the [isRefreshing] instance will be set as **false** to allow the restart of the routine after executing
         * the other requests
         *
         * No-any params required
         */
        fun suspendRefresher()

        /**
         * Function to conditionally restart the current [refreshRoutine] after other requests has been executed,
         * the [isRefreshing] instance will be set as **true** to deny the restart of the routine after executing
         * the other requests
         *
         * @param condition: the condition to verify whether restart or not the refresher
         */
        fun restartRefresherIf(
            condition: Boolean
        ) {
            if (condition)
                restartRefresher()
        }

        /**
         * Function to restart the current [refreshRoutine] after other requests has been executed,
         * the [isRefreshing] instance will be set as **true** to deny the restart of the routine after executing
         * the other requests
         *
         * No-any params required
         */
        fun restartRefresher()

        /**
         * Function to execute the refresh routine designed
         *
         * @param currentContext: the current context where the [refreshRoutine] is executing
         * @param routine: the refresh routine to execute
         * @param repeatRoutine: whether repeat the routine or execute a single time
         * @param refreshDelay: the delay between the execution of the requests
         */
        fun execRefreshingRoutine(
            currentContext: Class<*>,
            routine: () -> Unit,
            repeatRoutine: Boolean = true,
            refreshDelay: Long = 1000L
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

        /**
         * Function to set the current active context where the [refreshRoutine] is executing
         *
         * @param currentContext: the current active context to set
         */
        fun setActiveContext(
            currentContext: Class<*>
        ) {
            FetcherManager.setActiveContext(currentContext)
        }

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

    /**
     * The **GenericFetcher** interface is useful to manage the requests to refresh a list of items and
     * a single item
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see ItemFetcher
     * @see ListFetcher
     */
    interface GenericFetcher: ItemFetcher, ListFetcher

}