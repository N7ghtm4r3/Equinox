package com.tecknobit.equinoxbackend.batch;

import com.tecknobit.equinoxbackend.environment.services.builtin.service.EquinoxItemsHelper;
import jdk.jfr.Experimental;

import java.util.Collection;
import java.util.List;

@Experimental
// TODO: 22/02/2025 MISSED DOCUMENTATION
public abstract class BatchSynchronizationProcedure<O, D, V> extends EquinoxItemsHelper {

    protected final O owner;

    protected final List<D> ownedItems;

    protected final SyncBatchModel model;

    protected final String table;

    protected final BatchQuery<V> batchQuery;

    protected CurrentDataCallback<D> currentDataCallback;

    public BatchSynchronizationProcedure(O owner, List<D> ownedItems, String table) {
        this.owner = owner;
        this.ownedItems = ownedItems;
        this.model = createModel();
        this.table = table;
        this.batchQuery = createBatchQuery();
    }

    protected abstract SyncBatchModel createModel();

    protected abstract BatchQuery<V> createBatchQuery();

    protected abstract Collection<V> loadDataList(Collection<D> rawData);

    public void executeBatchSynchronization() {
        if (currentDataCallback == null)
            throw new IllegalStateException("You must set the CurrentDataCallback first");
        syncBatch(model, table, batchQuery);
    }

    public O getOwner() {
        return owner;
    }

    public List<D> getOwnedItems() {
        return ownedItems;
    }

    public SyncBatchModel getModel() {
        return model;
    }

    public String getTable() {
        return table;
    }

    public BatchQuery<V> getBatchQuery() {
        return batchQuery;
    }

    public CurrentDataCallback<D> getCurrentDataCallback() {
        return currentDataCallback;
    }

    public void setCurrentDataCallback(CurrentDataCallback<D> currentDataCallback) {
        this.currentDataCallback = currentDataCallback;
    }

    public interface CurrentDataCallback<D> {

        Collection<D> retrieveCurrentData();

    }

}
