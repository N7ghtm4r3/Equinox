package com.tecknobit.equinox.environment.records;

import com.tecknobit.apimanager.annotations.Structure;
import com.tecknobit.apimanager.formatters.JsonHelper;
import com.tecknobit.apimanager.formatters.TimeFormatter;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import org.json.JSONObject;

/**
 * The {@code EquinoxItem} class is useful to create an Equinox's item giving the basis structure to work correctly
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see EquinoxItem
 */
@Structure
@MappedSuperclass
public abstract class EquinoxItem {

    /**
     * {@code IDENTIFIER_KEY} the key for the <b>"id"</b> field
     */
    public static final String IDENTIFIER_KEY = "id";

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
    public EquinoxItem(JSONObject jItem) {
        hItem = new JsonHelper(jItem);
        id = hItem.getString(IDENTIFIER_KEY);
    }

    /**
     * Method to get {@link #id} instance <br>
     * No-any params required
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
