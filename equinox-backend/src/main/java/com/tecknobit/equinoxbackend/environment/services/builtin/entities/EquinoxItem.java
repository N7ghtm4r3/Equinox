package com.tecknobit.equinoxbackend.environment.services.builtin.entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tecknobit.apimanager.annotations.Structure;
import com.tecknobit.apimanager.formatters.JsonHelper;
import com.tecknobit.equinoxcore.annotations.Wrapper;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import org.json.JSONObject;

import static com.tecknobit.equinoxcore.helpers.CommonKeysKt.DELETED_AT_KEY;
import static com.tecknobit.equinoxcore.helpers.CommonKeysKt.IDENTIFIER_KEY;

/**
 * The {@code EquinoxItem} class is useful to create an Equinox's item giving the basis structure to work correctly
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see EquinoxItem
 *
 * @since 1.0.1
 */
@Structure
@MappedSuperclass
public abstract class EquinoxItem {

    /**
     * {@code DISCRIMINATOR_VALUE_KEY} the key for the <b>"dtype"</b> field
     */
    public static final String DISCRIMINATOR_VALUE_KEY = "dtype";

    /**
     * {@code id} identifier of the item
     */
    @Id
    @Column(name = IDENTIFIER_KEY)
    protected final String id;

    /**
     * {@code hItem} helper to work with JSON values
     */
    @Transient
    protected final JsonHelper hItem;

    /**
     * {@code deletedAt} the deletion timestamp in milliseconds since the Unix epoch, or {@code -1} when active
     *
     * @since 2.0.0
     */
    @Column(
            name = DELETED_AT_KEY,
            columnDefinition = "BIGINT DEFAULT -1",
            insertable = false
    )
    protected long deletedAt;

    /**
     * Constructor to init the {@link EquinoxItem} class
     *
     * @param id: identifier of the item
     */
    public EquinoxItem(String id) {
        this.id = id;
        hItem = null;
        deletedAt = -1;
    }

    /**
     * Constructor to init the {@link EquinoxItem} class
     *
     * @param jItem: item formatted as JSON
     */
    @Wrapper
    public EquinoxItem(JSONObject jItem) {
        this(jItem, IDENTIFIER_KEY);
    }

    /**
     * Constructor to init the {@link EquinoxItem} class from JSON
     *
     * <p>The deletion timestamp defaults to {@code -1} when the JSON does not provide it
     *
     * @param jItem         The item details formatted as JSON
     * @param identifierKey The key used to retrieve the item identifier
     */
    public EquinoxItem(JSONObject jItem, String identifierKey) {
        hItem = new JsonHelper(jItem);
        id = hItem.getString(identifierKey);
        deletedAt = hItem.getLong(DELETED_AT_KEY, -1);
    }

    /**
     * Method used to get {@link #id} instance 
     *
     * @return {@link #id} instance as {@link String}
     */
    public String getId() {
        return id;
    }

    /**
     * Method used to get the deletion timestamp of the item
     *
     * @return the deletion timestamp in milliseconds since the Unix epoch, or {@code -1} when active, as {@code long}
     *
     * @since 2.0.0
     */
    @JsonGetter(DELETED_AT_KEY)
    public long getDeletedAt() {
        return deletedAt;
    }

    /**
     * Method used to set the deletion timestamp of this item without persisting it
     *
     * @param deletedAt The deletion timestamp in milliseconds since the Unix epoch, or {@code -1} to mark the item active
     *
     * @since 2.0.0
     */
    public void setDeletedAt(long deletedAt) {
        this.deletedAt = deletedAt;
    }

    /**
     * Method used to check whether the item is soft deleted
     *
     * @return whether the deletion timestamp differs from {@code -1} as {@code boolean}
     *
     * @since 2.0.0
     */
    @JsonIgnore
    public boolean isSoftDeleted() {
        return deletedAt != -1L;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }

}
