package com.tecknobit.equinoxbackend.batch;

import com.tecknobit.equinoxbackend.annotations.BatchQueryItem;
import com.tecknobit.equinoxbackend.environment.services.builtin.service.EquinoxItemsHelper;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code JoinTableSyncBatchItem} class is designed to manage data used in batch queries,
 * specifically those that operate on join tables
 *
 * @param <O> The type of the owner entity
 * @param <D> The type of the owned entity
 * @author N7ghtm4r3 - Tecknobit
 * @since 1.0.8
 */
@BatchQueryItem
public class JoinTableSyncBatchItem<O, D> implements EquinoxItemsHelper.ComplexBatchItem {

    /**
     * {@code owner} the owner entity in the relationship
     */
    protected final O owner;

    /**
     * {@code owned} the owned entity in the relationship
     */
    protected final D owned;

    /**
     * Method to initialize the object
     *
     * @param owner The owner entity in the relationship
     * @param owned The owned entity in the relationship
     */
    public JoinTableSyncBatchItem(O owner, D owned) {
        this.owner = owner;
        this.owned = owned;
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
     * Method to get the {@link #owned}
     *
     * @return the {@link #owned} as {@link D}
     */
    public D getOwned() {
        return owned;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof JoinTableSyncBatchItem<?, ?> that))
            return false;
        return owner.equals(that.owner) && owned.equals(that.owned);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = owner.hashCode();
        result = 31 * result + owned.hashCode();
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public List<?> mappedValues() {
        ArrayList<Object> mappedValues = new ArrayList<>();
        mappedValues.add(owner);
        mappedValues.add(owned);
        return mappedValues;
    }

}