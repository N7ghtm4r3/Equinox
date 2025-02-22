package com.tecknobit.equinoxbackend.batch;

import com.tecknobit.equinoxbackend.environment.services.builtin.service.EquinoxItemsHelper;
import com.tecknobit.equinoxcore.annotations.Wrapper;
import jakarta.persistence.EntityManager;
import jdk.jfr.Experimental;

import java.util.Collection;

import static com.tecknobit.equinoxbackend.environment.services.builtin.service.EquinoxItemsHelper.InsertCommand.INSERT_IGNORE_INTO;

/**
 * The {@code BatchSynchronizationProcedure} class is designed to compact and to clean implement a batch synchronization
 * procedure giving the base behavior that a batch synchronization need to have
 *
 * @param <O> The type of the owner entity
 * @param <D> The type of the owned entity
 * @param <V> The type of the support item used during the synchronization such {@link JoinTableSyncBatchItem}
 * @author N7ghtm4r3 - Tecknobit
 * @since 1.0.8
 */
@Experimental
public abstract class BatchSynchronizationProcedure<O, D, V> extends EquinoxItemsHelper {

    /**
     * {@code owner} the owner entity in the relationship
     */
    protected final O owner;

    /**
     * {@code ownedItems} the collection of the entities owned by the {@link #owner} entity
     */
    protected final Collection<D> ownedItems;

    /**
     * {@code model} the model to use during the synchronization procedure
     */
    protected final SyncBatchModel model;

    /**
     * {@code table} the table where execute the synchronization
     */
    protected final String table;

    /**
     * {@code batchQuery} the manager of the batch query to execute
     */
    protected final BatchQuery<V> batchQuery;

    /**
     * {@code currentDataCallback} the callback used to retrieve the current data owned by the {@link #owner} entity
     */
    protected CurrentDataCallback<D> currentDataCallback;

    /**
     * Constructor to init the sync procedure
     *
     * @param owner         The owner entity in the relationship
     * @param ownedItems    The collection of the entities owned by the {@link #owner} entity
     * @param table         The table where execute the synchronization
     * @param entityManager The entity manager helper
     */
    public BatchSynchronizationProcedure(O owner, Collection<D> ownedItems, String table, EntityManager entityManager) {
        this.owner = owner;
        this.ownedItems = ownedItems;
        this.model = createModel();
        this.table = table;
        this.batchQuery = createBatchQuery();
        this.entityManager = entityManager;
    }

    /**
     * Method used to create the sync batch model used by the procedure
     *
     * @return the sync batch model used by the procedure as {@link SyncBatchModel}
     */
    protected abstract SyncBatchModel createModel();

    /**
     * Method used to create the batch query used by the procedure
     *
     * @return the batch query used by the procedure as {@link BatchQuery}
     */
    protected abstract BatchQuery<V> createBatchQuery();

    /**
     * Method used to loads and convert a raw collection of data to the collection used by the procedure
     *
     * @param rawData The collection to convert
     * @return the collection used by the procedure as {@link Collection} of {@link V}
     */
    protected abstract Collection<V> loadDataList(Collection<D> rawData);

    /**
     * Method used to execute the batch synchronization
     */
    @Wrapper
    public void executeBatchSynchronization() {
        executeBatchSynchronization(INSERT_IGNORE_INTO);
    }

    /**
     * Method used to execute the batch synchronization
     *
     * @param command The insertion command to execute
     */
    public void executeBatchSynchronization(InsertCommand command) {
        if (currentDataCallback == null)
            throw new IllegalStateException("You must set the CurrentDataCallback first");
        syncBatch(model, command, table, batchQuery);
    }

    /**
     * Method to get the {@link #owner}
     *
     * @return the {@link #owner} as {@link O}
     */
    public O getOwner() {
        return owner;
    }

    /**
     * Method to get the {@link #ownedItems}
     *
     * @return the {@link #ownedItems} as {@link Collection} of {@link D}
     */
    public Collection<D> getOwnedItems() {
        return ownedItems;
    }

    /**
     * Method to get the {@link #model}
     *
     * @return the {@link #model} as {@link SyncBatchModel}
     */
    public SyncBatchModel getModel() {
        return model;
    }

    /**
     * Method to get the {@link #table}
     *
     * @return the {@link #table} as {@link String}
     */
    public String getTable() {
        return table;
    }

    /**
     * Method to get the {@link #batchQuery}
     *
     * @return the {@link #batchQuery} as {@link BatchQuery} of {@link V}
     */
    public BatchQuery<V> getBatchQuery() {
        return batchQuery;
    }

    /**
     * Method to get the {@link #currentDataCallback}
     *
     * @return the {@link #currentDataCallback} as {@link CurrentDataCallback} of {@link D}
     */
    public CurrentDataCallback<D> getCurrentDataCallback() {
        return currentDataCallback;
    }

    /**
     * Method to set the {@link #currentDataCallback} instance
     *
     * @param currentDataCallback The callback used to retrieve the current data
     */

    public void setCurrentDataCallback(CurrentDataCallback<D> currentDataCallback) {
        this.currentDataCallback = currentDataCallback;
    }

    /**
     * The {@code CurrentDataCallback} interface is useful to define a retriever used to retrieve the current data currently
     * owned by the {@link #owner} entity
     *
     * @param <D> The type of the owned entity
     *
     * @author N7ghtm4r3 - Tecknobit
     * @since 1.0.8
     */
    public interface CurrentDataCallback<D> {

        /**
         * Method to retrieve the current data owned by the {@link #owner} entity
         *
         * @return the current data owned by the {@link #owner} entity as {@link Collection} of {@link D}
         */
        Collection<D> retrieveCurrentData();

    }

}
