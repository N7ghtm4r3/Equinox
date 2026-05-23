package com.tecknobit.equinoxbackend.apis.batch;

import com.tecknobit.equinoxbackend.annotations.BatchQueryItem;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code JoinTableSyncBatchItem} class is designed to manage data used in batch queries,
 * specifically those that operate on join tables
 *
 * @param <O>   The type of the owner entity
 * @param <D>   The type of the owned entity
 * @param owner {@code owner} the owner entity in the relationship
 * @param owned {@code owned} the owned entity in the relationship
 * @author N7ghtm4r3 - Tecknobit
 * @since 1.0.8
 */
@BatchQueryItem
public record JoinTableSyncBatchItem<O, D>(O owner, D owned) implements EquinoxItemsHelper.ComplexBatchItem {

    /**
     * Method used to initialize the object
     *
     * @param owner The owner entity in the relationship
     * @param owned The owned entity in the relationship
     */
    public JoinTableSyncBatchItem {
    }

    /**
     * Method used to get the {@link #owner}
     *
     * @return the {@link #owner} as {@link O}
     */
    @Override
    public O owner() {
        return owner;
    }

    /**
     * Method used to get the {@link #owned}
     *
     * @return the {@link #owned} as {@link D}
     */
    @Override
    public D owned() {
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
    @NotNull
    @Override
    public List<?> mappedValues() {
        ArrayList<Object> mappedValues = new ArrayList<>();

        mappedValues.add(owner);
        mappedValues.add(owned);

        return mappedValues;
    }

}