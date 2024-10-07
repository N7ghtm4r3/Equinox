package com.tecknobit.equinox.environment.helpers.services;

import com.tecknobit.apimanager.annotations.Wrapper;
import com.tecknobit.equinox.environment.records.EquinoxItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * The {@code EquinoxItemsHelper} class is useful to manage all the {@link EquinoxItem} database operations
 *
 * @author N7ghtm4r3 - Tecknobit
 */
@Transactional
public abstract class EquinoxItemsHelper<T extends EquinoxItem> {

    public enum InsertCommand {

        INSERT_INTO("INSERT INTO"),

        INSERT_IGNORE_INTO("INSERT IGNORE INTO"),

        REPLACE_INTO("REPLACE INTO");

        private final String sql;

        InsertCommand(String sql) {
            this.sql = sql + " ";
        }
    }

    /**
     * The {@code BatchQuery} interface is useful to manage the batch queries to insert or delete values in batch
     *
     * @author N7ghtm4r3 - Tecknobit
     */
    public interface BatchQuery {

        /**
         * Method to prepare the batch query such fill the parameters programmatically
         *
         * @param query: query instance used to execute the SQL command
         */
        void prepareQuery(Query query);

    }

    public interface SyncBatchWorkflow {

        List<String> getCurrentIds();

        String insertQuery();

        String deleteQuery();

    }

    /**
     * {@code COUPLE_VALUES_PLACEHOLDER} the placeholder used to insert in the table new row composed by two elements
     */
    protected static final String COUPLE_VALUES_PLACEHOLDER = "(?, ?)";

    /**
     * {@code TUPLE_VALUES_PLACEHOLDER} the placeholder used to insert in the table new row composed by three elements
     */
    protected static final String TUPLE_VALUES_PLACEHOLDER = "(?, ?, ?)";

    /**
     * {@code SINGLE_QUOTE} single quote character
     */
    private static final String SINGLE_QUOTE = "'";

    /**
     * {@code OPENED_ROUND_BRACKET} opened round bracket character
     */
    private static final String OPENED_ROUND_BRACKET = "(";

    /**
     * {@code CLOSED_ROUND_BRACKET} closed round bracket character
     */
    private static final String CLOSED_ROUND_BRACKET = ")";

    /**
     * {@code COMMA} comma character
     */
    private static final String COMMA = ",";

    /**
     * {@code QUESTION_MARK} question mark character
     */
    private static final String QUESTION_MARK = "?";

    /**
     * {@code VALUES} values query part
     */
    private static final String VALUES = " VALUES ";

    /**
     * {@code entityManager} entity manager helper
     */
    @PersistenceContext
    protected EntityManager entityManager;

    @Wrapper
    protected void syncBatch(SyncBatchWorkflow workflow, String targetId, List<String> updatedIds) {
        syncBatch(workflow, COUPLE_VALUES_PLACEHOLDER, targetId, updatedIds,
                query -> {
                    int index = 1;
                    for (String id : updatedIds) {
                        query.setParameter(index++, id);
                        query.setParameter(index++, targetId);
                    }
                }
        );
    }

    protected void syncBatch(SyncBatchWorkflow workflow, String valuePlaceholders, String targetId,
                             List<String> updatedIds, BatchQuery batchQuery) {
        List<String> currentIds = workflow.getCurrentIds();
        batchInsert(workflow.insertQuery(), valuePlaceholders, updatedIds, batchQuery);
        currentIds.removeAll(updatedIds);
        batchDelete(workflow.deleteQuery(), targetId, currentIds);
    }

    @Wrapper
    protected <V> void batchInsert(InsertCommand command, String table, List<V> values, BatchQuery batchQuery,
                                   String... columns) {
        String query = command.sql + table + " " + formatColumns(columns) + VALUES;
        String placeHolder = formatPlaceholder(columns);
        batchInsert(query, placeHolder, values, batchQuery);
    }

    protected <V> void batchInsert(String insertQuery, String valuePlaceholders, List<V> values, BatchQuery batchQuery) {
        if (values.isEmpty())
            return;
        Query query = assembleBatchInsertQuery(insertQuery, valuePlaceholders, values);
        batchQuery.prepareQuery(query);
        query.executeUpdate();
    }

    private <V> Query assembleBatchInsertQuery(String insertQuery, String valuesSlice, List<V> values) {
        String queryAssembled = formatValuesForQuery(insertQuery, values, valuesSlice, false);
        return entityManager.createNativeQuery(queryAssembled);
    }

    private String formatColumns(String... columns) {
        return formatValuesForQuery(OPENED_ROUND_BRACKET, Arrays.stream(columns).toList(), null, true);
    }

    @Wrapper
    private String formatPlaceholder(String... columns) {
        return formatValuesForQuery(OPENED_ROUND_BRACKET, Arrays.stream(columns).toList(), QUESTION_MARK, true);
    }

    private <V> String formatValuesForQuery(String start, List<V> values, String formatter, boolean isToClose) {
        StringBuilder formattedColumns = new StringBuilder(start);
        int totalValues = values.size();
        int lastValueIndex = totalValues - 1;
        for (int j = 0; j < totalValues; j++) {
            if (formatter == null)
                formattedColumns.append(values.get(j));
            else
                formattedColumns.append(formatter);
            if (j < lastValueIndex)
                formattedColumns.append(COMMA);
        }
        if (isToClose)
            formattedColumns.append(CLOSED_ROUND_BRACKET);
        return formattedColumns.toString();
    }

    protected <V> void batchDelete(String deleteQuery, String itemToDeleteId, List<V> values) {
        if (values.isEmpty())
            return;
        Query query = assembleBatchDeleteQuery(deleteQuery, itemToDeleteId, values);
        query.executeUpdate();
    }

    private <V> Query assembleBatchDeleteQuery(String deleteQuery, String itemToDeleteId, List<V> values) {
        deleteQuery = String.format(deleteQuery, itemToDeleteId);
        StringBuilder queryAssembler = new StringBuilder(deleteQuery);
        int size = values.size();
        for (int j = 0; j < size; j++) {
            queryAssembler.append(SINGLE_QUOTE).append(values.get(j)).append(SINGLE_QUOTE);
            if (j < size - 1)
                queryAssembler.append(COMMA);
        }
        queryAssembler.append(CLOSED_ROUND_BRACKET);
        return entityManager.createNativeQuery(queryAssembler.toString());
    }

}