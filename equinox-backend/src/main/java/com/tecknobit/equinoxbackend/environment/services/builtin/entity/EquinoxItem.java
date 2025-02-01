package com.tecknobit.equinoxbackend.environment.services.builtin.entity;

import com.tecknobit.apimanager.annotations.Structure;
import com.tecknobit.apimanager.formatters.JsonHelper;
import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.equinoxcore.annotations.Wrapper;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import org.json.JSONObject;

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
     * {@code timeFormatter} the formatter used to format the timestamp values
     */
    @Transient
    protected final TimeFormatter timeFormatter = TimeFormatter.getInstance();

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
     * Constructor to init the {@link EquinoxItem} class
     *
     * @param id: identifier of the item
     */
    public EquinoxItem(String id) {
        this.id = id;
        hItem = null;
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
     * Constructor to init the {@link EquinoxItem} class
     *
     * @param jItem: item formatted as JSON
     */
    public EquinoxItem(JSONObject jItem, String identifierKey) {
        hItem = new JsonHelper(jItem);
        id = hItem.getString(identifierKey);
    }

    /**
     * Method to get {@link #id} instance 
     *
     * @return {@link #id} instance as {@link String}
     */
    public String getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }

}
