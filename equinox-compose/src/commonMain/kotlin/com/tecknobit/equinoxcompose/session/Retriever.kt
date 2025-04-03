package com.tecknobit.equinoxcompose.session

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.Volatile
import kotlin.reflect.KClass

/**
 * The `Retriever` handles that repetitive retrieving routines and execute them in background by the [retrieverScope]
 *
 * @param retrieverScope The coroutine used to execute the retrieving routines
 *
 * @author N7ghtm4r3 - Tecknobit
 */
class Retriever(
    private val retrieverScope: CoroutineScope,
) {

    companion object {

        /**
         * `currentContext` The current active context where a [retrieverScope] is executing its workflow
         */
        @Volatile
        private lateinit var currentContext: KClass<*>

        /**
         * Method used to set the current active context where the [retrieverScope] is executing
         *
         * @param currentContext The current active context to set
         */
        fun setActiveContext(
            currentContext: KClass<*>,
        ) {
            this.currentContext = currentContext
        }

        /**
         * Method used to get the current active context where the [retrieverScope] is executing
         *
         * @return the current active context as [KClass]
         */
        fun getActiveContext(): KClass<*> {
            return currentContext
        }

    }

    /**
     * `isRefreshing` whether the [retrieverScope] is already refreshing
     */
    private var isRefreshing: Boolean = false

    /**
     * `lastRoutineExecuted` the last routine executed by the [execute] method must re-launched after the [suspend]
     * method has been invoked
     */
    private lateinit var lastRoutineExecuted: RetrievingRoutine

    /**
     * Method used to get whether the [retrieverScope] can start, so if there aren't other jobs that
     * routine is already executing
     *
     *
     * @return whether the [retrieverScope] can start as [Boolean]
     */
    fun canStart(): Boolean {
        return !isRefreshing
    }

    /**
     * Method used to suspend the current [retrieverScope] to execute other requests to the backend,
     * the [isRefreshing] instance will be set as **false** to allow the restart of the routine after executing
     * the other requests
     *
     */
    fun suspend() {
        retrieverScope.coroutineContext.cancelChildren()
        isRefreshing = false
    }

    /**
     * Method used to restart the current [retrieverScope] after other requests has been executed,
     * the [isRefreshing] instance will be set as **true** to deny the restart of the routine after executing
     * the other requests
     *
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
     * Method used to execute the refresh routine designed
     *
     * @param currentContext The current context where the [retrieverScope] is executing
     * @param routine The refresh routine to execute
     * @param repeatRoutine Whether repeat the routine or execute a single time
     * @param refreshDelay The delay between the execution of the requests
     */
    fun execute(
        currentContext: KClass<*>,
        routine: suspend () -> Unit,
        repeatRoutine: Boolean = true,
        refreshDelay: Long = 1000L,
    ) {
        if(canStart()) {
            isRefreshing = true
            lastRoutineExecuted = RetrievingRoutine(
                currentContext = currentContext,
                routine = routine,
                repeatRoutine = repeatRoutine,
                refreshDelay = refreshDelay
            )
            retrieverScope.launch {
                if(repeatRoutine) {
                    while (continueToRetrieve(currentContext)) {
                        routine()
                        delay(refreshDelay)
                    }
                } else
                    routine()
            }
        }
    }

    /**
     * Method used to check if the [retrieverScope] can continue to refresh or need to be stopped, this for
     * example when the UI displayed changes and the requests to refresh the UI data
     * also changes
     *
     * @param currentContext The current context where the [retrieverScope] is executing
     *
     * @return whether the [retrieverScope] can continue to refresh as [Boolean]
     */
    fun continueToRetrieve(
        currentContext: KClass<*>,
    ) : Boolean {
        return Companion.currentContext == currentContext
    }

    /**
     * The **RetrievingRoutine** data class is useful to memorize the last routine executed by the [execute] method
     *
     * @param currentContext The current context where the [retrieverScope] is executing
     * @param routine The refresh routine to execute
     * @param repeatRoutine Whether repeat the routine or execute a single time
     * @param refreshDelay The delay between the execution of the requests
     */
    private data class RetrievingRoutine(
        val currentContext: KClass<*>,
        val routine: suspend () -> Unit,
        val repeatRoutine: Boolean = true,
        val refreshDelay: Long = 1000L,
    )

    /**
     * The **RetrieverWrapper** interface is useful for wrapping and facilitating operation with the
     * [Retriever], so the inheriting classes will invoke just the wrapper methods for a clean readability
     * of the code, for example:
     *
     * ```kotlin
     *
     * // the super class from which the other classes will be inherited
     * abstract class AbstractClass : RetrieverWrapper {
     *
     *     protected val retrieverScope = CoroutineScope(Dispatchers.Default)
     *
     *     private val retrieverScope = Retriever(retrieverScope)
     *
     *     override fun canRetrieverStart(): Boolean {
     *         return retrieverScope.canStart()
     *     }
     *
     *     override fun suspendRetriever() {
     *         retrieverScope.suspend()
     *     }
     *
     *     override fun restartRetriever() {
     *         retrieverScope.restart()
     *     }
     *
     *     override fun continueToRetrieve(
     *         currentContext: KClass<*>
     *     ): Boolean {
     *         return retrieverScope.continueToRetrieve(currentContext)
     *     }
     *
     *     override fun retrieve(
     *         currentContext: KClass<*>,
     *         routine: () -> Unit,
     *         repeatRoutine: Boolean,
     *         refreshDelay: Long
     *     ) {
     *         retrieverScope.execute(
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
     *         retrieve(
     *             currentContext = this::class.java,
     *             routine = {
     *                 // your routine
     *             }
     *         )
     *     }
     *
     *     fun example1() {
     *         suspendRetriever()
     *     }
     *
     * }
     * ```
     *
     * @author N7ghtm4r3 - Tecknobit
     *
     * @see Retriever
     *
     */
    interface RetrieverWrapper {

        /**
         * Method used to get whether the [retrieverScope] can start, so if there aren't other jobs that
         * routine is already executing
         *
         * No-any params required
         *
         * @return whether the [retrieverScope] can start as [Boolean]
         */
        fun canRetrieverStart(): Boolean

        /**
         * Method used to conditionally suspend the current [retrieverScope] to execute other requests to the backend,
         * the [isRefreshing] instance will be set as **false** to allow the restart of the routine after executing
         * the other requests
         *
         * @param condition The condition to verify whether suspend or not the refresher
         */
        fun suspendRetrieverIf(
            condition: Boolean,
        ) {
            if (condition)
                suspendRetriever()
        }

        /**
         * Method used to suspend the current [retrieverScope] to execute other requests to the backend,
         * the [isRefreshing] instance will be set as **false** to allow the restart of the routine after executing
         * the other requests
         *
         * No-any params required
         */
        fun suspendRetriever()

        /**
         * Method used to conditionally restart the current [retrieverScope] after other requests has been executed,
         * the [isRefreshing] instance will be set as **true** to deny the restart of the routine after executing
         * the other requests
         *
         * @param condition The condition to verify whether restart or not the refresher
         */
        fun restartRetrieverIf(
            condition: Boolean,
        ) {
            if (condition)
                restartRetriever()
        }

        /**
         * Method used to restart the current [retrieverScope] after other requests has been executed,
         * the [isRefreshing] instance will be set as **true** to deny the restart of the routine after executing
         * the other requests
         *
         * No-any params required
         */
        fun restartRetriever()

        /**
         * Method used to execute the refresh routine designed
         *
         * @param currentContext The current context where the [retrieverScope] is executing
         * @param routine The refresh routine to execute
         * @param repeatRoutine Whether repeat the routine or execute a single time
         * @param refreshDelay The delay between the execution of the requests
         */
        fun retrieve(
            currentContext: KClass<*>,
            routine: suspend () -> Unit,
            repeatRoutine: Boolean = true,
            refreshDelay: Long = 1000L,
        )

        /**
         * Method used to check if the [retrieverScope] can continue to refresh or need to be stopped, this for
         * example when the UI displayed changes and the requests to refresh the UI data
         * also changes
         *
         * @param currentContext The current context where the [retrieverScope] is executing
         *
         * @return whether the [retrieverScope] can continue to refresh as [Boolean]
         */
        fun continueToRetrieve(
            currentContext: KClass<*>,
        ) : Boolean

        /**
         * Method used to set the current active context where the [retrieverScope] is executing
         *
         * @param currentContext The current active context to set
         */
        fun setActiveContext(
            currentContext: KClass<*>,
        ) {
            Companion.setActiveContext(currentContext)
        }

    }

}