package com.tecknobit.equinoxbackend.environment.services.builtin.service;

import com.tecknobit.equinoxbackend.environment.services.builtin.entity.EquinoxItem;
import com.tecknobit.equinoxcore.annotations.Wrapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.tecknobit.equinoxbackend.environment.services.builtin.service.EquinoxItemsHelper.InsertCommand.INSERT_IGNORE_INTO;

/**
 * The {@code EquinoxItemsHelper} class is useful to manage all the {@link EquinoxItem} database operations
 *
 * @author N7ghtm4r3 - Tecknobit
 */
@Transactional
public abstract class EquinoxItemsHelper {

    /**
     * {@code InsertCommand} list of supported commands to insert records in the batch queries
     */
    public enum InsertCommand {

        /**
         * {@code INSERT_INTO} command to insert a new record in the table
         *
         * @apiNote if the primary key already exists will be thrown an error
         */
        INSERT_INTO("INSERT INTO"),

        /**
         * {@code INSERT_IGNORE_INTO} command to insert a new record in the table, but differently from {@link #INSERT_INTO}
         * if the primary key already exists will be ignored and that specific row will not be inserted
         */
        INSERT_IGNORE_INTO("INSERT IGNORE INTO"),

        /**
         * {@code REPLACE_INTO} command to insert a new record in the table if the primary key not exists, otherwise if that
         * primary key already exists will be replaced with the new data are inserting
         */
        REPLACE_INTO("REPLACE INTO");

        /**
         * {@code sql} the sql command value to use in the queries
         */
        private final String sql;

        /**
         * Constructor to instantiate an {@link InsertCommand}
         *
         * @param sql The sql command value to use in the queries
         */
        InsertCommand(String sql) {
            this.sql = sql + " ";
        }

    }

    /**
     * The {@code SyncBatchContainer} interface is useful to execute the {@link #syncBatch(SyncBatchContainer, String, String, BatchQuery)}
     * method to synchronize the data. This interface is the container about the information to use during the synchronization
     * process
     *
     * @author N7ghtm4r3 - Tecknobit
     */
    public interface SyncBatchContainer {

        /**
         * Method to get the current list of data <br>
         *
         * No-any params required
         *
         * @return the current list of identifiers as {@link ArrayList} of {@link V}
         */
        <V> ArrayList<V> getCurrentData();

        /**
         * Method to get the columns affected by the synchronization <br>
         * <p>
         * No-any params required
         *
         * @return columns affected by the synchronization as array of {@link String}
         */
        String[] getColumns();

    }

    /**
     * The {@code BatchQuery} interface is useful to manage the batch queries to insert or delete values in batch
     *
     * @param <V> The type of the item used in the query
     *
     * @author N7ghtm4r3 - Tecknobit
     *
     * @apiNote example usage for a join table of user and his/her cars:
     * <table>
     *     <thead>
     *         <tr>
     *             <th>user_id</th>
     *             <th>car_id</th>
     *         </tr>
     *     </thead>
     *     <tbody>
     *         <tr>
     *             <td>userId</td>
     *             <td>carId</td>
     *         </tr>
     *     </tbody>
     * </table>
     * <pre>
     * {@code
     *     ArrayList<String> carsIds = fetchCarsIdentifiers();
     *     BatchQuery batchQuery = new BatchQuery<String>() {
     *
     *          @Override
     *          public void getUpdatedData() {
     *              return updatedCars; // your updated data list
     *         }
     *
     *          @Override
     *          public void prepareQuery(Query query, int index, List<String> updatedItems) {
     *              for (String carId : updatedItems) {
     *                  // the order of the parameters setting is the same of the table
     *                  query.setParameter(index++, userId);
     *                  query.setParameter(index++, carId);
     *             }
     *         }
     *     }
     * }
     * </pre>
     */
    public interface BatchQuery<V> {

        /**
         * Method to get the updated data to use in the batch query
         *
         * @return the updated data as {@link List} of {@link V}
         */
        List<V> getUpdatedData();

        /**
         * Method to prepare the batch query such fill the parameters programmatically
         *
         * @param query Query instance used to execute the SQL command
         * @param index The pre-increment index to format the values in the query, its initial value is 1
         * @param updatedItems The updated items to use in the batch query
         */
        void prepareQuery(Query query, int index, List<V> updatedItems);

    }

    /**
     * {@code SINGLE_QUOTE} single quote character
     */
    public static final String SINGLE_QUOTE = "'";

    /**
     * {@code OPENED_ROUND_BRACKET} opened round bracket character
     */
    public static final String OPENED_ROUND_BRACKET = "(";

    /**
     * {@code CLOSED_ROUND_BRACKET} closed round bracket character
     */
    public static final String CLOSED_ROUND_BRACKET = ")";

    /**
     * {@code COMMA} comma character
     */
    public static final String COMMA = ",";

    /**
     * {@code QUESTION_MARK} question mark character
     */
    public static final String QUESTION_MARK = "?";

    /**
     * {@code VALUES} values query part
     */
    public static final String _VALUES_ = " VALUES ";

    /**
     * {@code DELETE_FROM_} delete from query command
     */
    public static final String DELETE_FROM_ = "DELETE FROM ";

    /**
     * {@code WHERE} where query part
     */
    public static final String _WHERE_ = " WHERE ";

    /**
     * {@code IN_CLAUSE} in clause query part
     */
    private static final String _IN_CLAUSE_ = " IN ";

    /**
     * {@code entityManager} entity manager helper
     */
    @PersistenceContext
    protected EntityManager entityManager;

    /**
     * Method to execute a batch synchronization of a list of data simultaneously
     *
     * @param container Contains the data about the synchronization such the columns affected and the current list of the data
     * @param table The table where execute the synchronization of the data
     * @param targetId The target identifier of the entity where the synchronization must be executed, such a user and
     *                     his/her notes
     * @param batchQuery The manager of the batch query to execute
     */
    protected <V> void syncBatch(SyncBatchContainer container, String table, String targetId, BatchQuery<V> batchQuery) {
        String[] columns = container.getColumns();
        ArrayList<V> currentData = container.getCurrentData();
        List<V> updatedData = batchQuery.getUpdatedData();
        batchInsert(INSERT_IGNORE_INTO, table, batchQuery, columns);
        currentData.removeAll(updatedData);
        batchDelete(table, List.of(List.of(targetId), currentData), columns);
    }

    /**
     * Method to execute a batch insert of a list of data simultaneously
     *
     * @param command The insertion command to execute
     * @param table The table where execute the batch insert
     * @param batchQuery The manager of the batch query to execute
     * @param columns The columns affected by the insertion
     */
    protected <V> void batchInsert(InsertCommand command, String table, BatchQuery<V> batchQuery, String... columns) {
        List<V> values = batchQuery.getUpdatedData();
        if (values.isEmpty())
            return;
        String insertQuery = command.sql + table + " " + formatColumns(columns) + _VALUES_;
        String placeHolder = formatPlaceholder(columns);
        String insertQueryComplete = formatValuesForQuery(insertQuery, values, placeHolder, false);
        Query query = entityManager.createNativeQuery(insertQueryComplete);
        batchQuery.prepareQuery(query, 1, values);
        query.executeUpdate();
    }

    /**
     * Method to execute a batch delete of a single list of data simultaneously
     *
     * @param table   The table where execute the batch insert
     * @param values  The values to simultaneously delete
     * @param columns The columns where execute the in comparison to delete the row correctly
     * @apiNote the query form: DELETE FROM table WHERE (col1, col2, ...) IN ((dataCol1, dataCol2), (dataCol1a, dataCol2a), ...)
     */
    @Wrapper
    protected void batchDeleteOnSingleSet(String table, List<?> values, String... columns) {
        batchDelete(table, List.of(values), columns);
    }

    /**
     * Method to execute a batch delete of a list of data simultaneously
     *
     * @param table The table where execute the batch insert
     * @param values The values to simultaneously delete
     * @param columns The columns where execute the in comparison to delete the row correctly
     * @apiNote the query form: DELETE FROM table WHERE (col1, col2, ...) IN ((dataCol1, dataCol2), (dataCol1a, dataCol2a), ...)
     */
    protected void batchDelete(String table, List<List<?>> values, String... columns) {
        int columnsNumber = columns.length;
        if (columnsNumber == 0)
            return;
        List<?> mergedValues = mergeAlternativelyInColumnsValues(values);
        if (mergedValues.isEmpty() || mergedValues.size() % columnsNumber != 0)
            return;
        String columnsFormated = formatValuesForQuery(OPENED_ROUND_BRACKET, Arrays.stream(columns).toList(),
                null, true);
        String inClause = formatInClause(columnsNumber, mergedValues);
        String deleteQuery = DELETE_FROM_ + table + _WHERE_ + columnsFormated + _IN_CLAUSE_ + inClause;
        Query query = entityManager.createNativeQuery(deleteQuery);
        query.executeUpdate();
    }

    /**
     * Method to merge alternatively different lists
     *
     * @param inValues The values used in the in clause to alternate
     * @return a single list alternatively merged as {@link List} of {@link ?}
     * @apiNote if a list is smaller than another it will be filled with the own last value available
     */
    private List<?> mergeAlternativelyInColumnsValues(List<List<?>> inValues) {
        List<Object> mergedValues = new ArrayList<>();
        int maxSize = findMaxSize(inValues);
        for (int j = 0; j < maxSize; j++) {
            for (List<?> values : inValues) {
                int valuesSize = values.size();
                if (valuesSize == 0)
                    break;
                if (j < valuesSize)
                    mergedValues.add(values.get(j));
                else
                    mergedValues.add(values.get(valuesSize - 1));
            }
        }
        return mergedValues;
    }

    /**
     * Method to find the max size of a list
     *
     * @param inValues The values used in the in clause to alternate
     * @return the max size of a list as int
     * @apiNote the return value is used to create a list with the all data available
     */
    private int findMaxSize(List<List<?>> inValues) {
        int maxSize = 0;
        for (List<?> values : inValues) {
            int valuesSize = values.size();
            if (maxSize < valuesSize)
                maxSize = valuesSize;
        }
        return maxSize;
    }

    /**
     * Method to format the in clause for the query
     *
     * @param columns The columns number
     * @param inValues The values used in the in clause to format
     * @return the in clause formatted as {@link String}
     * @apiNote clause formatted: ((col1, col2), (col1a, col2a), ...)
     */
    private String formatInClause(int columns, List<?> inValues) {
        StringBuilder inClause = new StringBuilder(OPENED_ROUND_BRACKET);
        int totalValues = inValues.size();
        int lastValue = totalValues - 1;
        int lastColumn = columns - 1;
        for (int j = 0; j < totalValues; ) {
            if (columns > 1)
                inClause.append(OPENED_ROUND_BRACKET);
            for (int i = 0; i < columns; i++, j++) {
                inClause.append(SINGLE_QUOTE).append(inValues.get(j)).append(SINGLE_QUOTE);
                if (i < lastColumn)
                    inClause.append(COMMA);
            }
            if (columns > 1)
                inClause.append(CLOSED_ROUND_BRACKET);
            if (j < lastValue || (j == lastValue && lastColumn == 0))
                inClause.append(COMMA);
        }
        inClause.append(CLOSED_ROUND_BRACKET);
        return inClause.toString();
    }

    /**
     * Method to format the columns value for the query
     *
     * @param columns The columns used in the query
     *
     * @return the columns formatted as {@link String}
     *
     * @apiNote columns formatted: (col1, col2, col3, ...)
     */
    @Wrapper
    private String formatColumns(String... columns) {
        return formatValuesForQuery(OPENED_ROUND_BRACKET, Arrays.stream(columns).toList(), null, true);
    }

    /**
     * Method to format the placeholder value for the query
     *
     * @param columns The columns used in the query
     *
     * @return the placeholder formatted as {@link String}
     *
     * @apiNote placeholder formatted: (?, ?, ?, ...), this based on the numbers of the columns used
     */
    @Wrapper
    private String formatPlaceholder(String... columns) {
        return formatValuesForQuery(OPENED_ROUND_BRACKET, Arrays.stream(columns).toList(), QUESTION_MARK, true);
    }

    /**
     * Method to format a list of values for the query
     *
     * @param start The starter character
     * @param values The values to format
     * @param formatter The character to use as formatter, if <b>null</b> will be used the values of the list as formatter
     * @param isToClose Whether the part formatted is to close with the {@link #CLOSED_ROUND_BRACKET} character
     *
     * @return the list of values formatted as {@link String}
     */
    private String formatValuesForQuery(String start, List<?> values, String formatter, boolean isToClose) {
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