package com.tecknobit.equinoxbackend.annotations;

/**
 * The {@code BatchSyncProcedureImpl} annotation is useful to indicate an implementation of a
 * {@link com.tecknobit.equinoxbackend.apis.batch.BatchSynchronizationProcedure}
 *
 * <pre>
 * {@code
 * @BatchSyncProcedureImpl(
 *  // not mandatory
 *   description= """
 *          Informative description about the behavior of the implemented procedure"
 *         """ // suggested text block
 * )
 * public class SimpleBatchQuerySyncProcedure extends BatchSynchronizationProcedure<Integer, String, SimpleCalendarBatchItem> {
 *
 *     // ... rest of the procedure ...
 *
 * }
 * }
 * </pre>
 *
 * @author N7ghtm4r3 - Tecknobit
 * @since 1.0.8
 */
public @interface BatchSyncProcedureImpl {

    /**
     * Informative description about the behavior of the implemented procedure
     */
    String description() default "";

}
