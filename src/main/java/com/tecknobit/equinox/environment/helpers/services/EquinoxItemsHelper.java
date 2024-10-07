package com.tecknobit.equinox.environment.helpers.services;

import com.tecknobit.apimanager.annotations.Wrapper;
import com.tecknobit.equinox.environment.records.EquinoxItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.tecknobit.equinox.environment.helpers.services.EquinoxItemsHelper.InsertCommand.INSERT_IGNORE_INTO;

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

        ArrayList<String> getCurrentIds();

        String deleteQuery();

        String[] getColumns();

    }

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

    protected void syncBatch(SyncBatchWorkflow workflow, String table, String targetId, List<String> updatedIds,
                             BatchQuery batchQuery) {
        ArrayList<String> currentIds = workflow.getCurrentIds();
        batchInsert(INSERT_IGNORE_INTO, table, updatedIds, batchQuery, workflow.getColumns());
        currentIds.removeAll(updatedIds);
        batchDelete(table, List.of("inhabitants_id", "home_id"), List.of(currentIds));
    }

    protected <V> void batchInsert(InsertCommand command, String table, List<V> values, BatchQuery batchQuery,
                                   String... columns) {
        if (values.isEmpty())
            return;
        String insertQuery = createBatchInsertQueryStart(command, table, columns);
        String placeHolder = formatPlaceholder(columns);
        Query query = assembleBatchInsertQuery(insertQuery, placeHolder, values);
        batchQuery.prepareQuery(query);
        query.executeUpdate();
    }

    private String createBatchInsertQueryStart(InsertCommand command, String table, String... columns) {
        return command.sql + table + " " + formatColumns(columns) + VALUES;
    }

    private <V> Query assembleBatchInsertQuery(String insertQuery, String valuesSlice, List<V> values) {
        String queryAssembled = formatValuesForQuery(insertQuery, values, valuesSlice, false);
        return entityManager.createNativeQuery(queryAssembled);
    }

    protected <V> void batchDelete(String table, List<String> inColumns, List<List<V>> inValues) {

        //  "DELETE FROM " + "users_home" + " WHERE " + "inhabitants_id" + "='%s' " + "AND " + "home_id" + " IN (";
        if (inColumns.isEmpty())
            return;
        String columnsFormated = formatValuesForQuery(OPENED_ROUND_BRACKET, inColumns, null, true);
        List<V> mergedValues = mergeAlternativelyInColumnsValues(inValues);
        if (mergedValues.isEmpty())
            return;
        String inClause = formatInClause(inColumns.size(), mergedValues);
        String deleteQuery = "DELETE FROM " + table + " WHERE " + columnsFormated + " IN " + inClause;
        System.out.println(deleteQuery);


        /*if (inValues.isEmpty())
            return;
        Query query = assembleBatchDeleteQuery(deleteQuery, itemToDeleteId, values);
        query.executeUpdate();*/
    }

    private <V> List<V> mergeAlternativelyInColumnsValues(List<List<V>> inValues) {
        List<V> mergedValues = new ArrayList<>();
        int maxSize = findMaxSize(inValues);
        for (int j = 0; j < maxSize; j++) {
            for (List<V> values : inValues) {
                int valuesSize = values.size();
                if (j < valuesSize)
                    mergedValues.add(values.get(j));
                else
                    mergedValues.add(values.get(valuesSize - 1));
            }
        }
        return mergedValues;
    }

    private <V> int findMaxSize(List<List<V>> inValues) {
        int maxSize = 0;
        for (List<V> values : inValues) {
            int valuesSize = values.size();
            if (maxSize < valuesSize)
                maxSize = valuesSize;
        }
        return maxSize;
    }

    private <V> String formatInClause(int columns, List<V> inValues) {
        StringBuilder inClause = new StringBuilder(OPENED_ROUND_BRACKET);
        int totalValues = inValues.size();
        int lastValue = totalValues - 1;
        int lastColumn = columns - 1;
        for (int j = 0; j < totalValues; j++) {
            inClause.append(OPENED_ROUND_BRACKET);
            for (int i = 0; i < columns; i++) {
                inClause.append(inValues.get(j * i));
                if (i < lastColumn)
                    inClause.append(COMMA);
            }
            inClause.append(CLOSED_ROUND_BRACKET);
            if (j < lastValue)
                inClause.append(COMMA);
        }
        inClause.append(CLOSED_ROUND_BRACKET);
        return inClause.toString();
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

}