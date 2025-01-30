package com.tecknobit.equinoxbackend.environment.services.builtin.service;

import com.tecknobit.equinoxbackend.environment.services.builtin.entity.EquinoxItem;
import com.tecknobit.equinoxcore.annotations.Wrapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;

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
     * The {@code SyncBatchModel} interface is useful to execute the {@link #syncBatch(SyncBatchModel, String, BatchQuery)}
     * method to synchronize the data. This interface is the model to use during the synchronization procedure
     *
     * @author N7ghtm4r3 - Tecknobit
     */
    public interface SyncBatchModel {

        /**
         * Method to get the current list of data
         *
         * @return the current list of identifiers as {@link ArrayList} of {@link V}
         */
        <V> ArrayList<V> getCurrentData();

        /**
         * Method to get the columns where execute the deletion of the items to delete
         *
         * @return columns where execute the deletion as array of {@link String}
         */
        String[] getDeletingColumns();

        /**
         * Invoked after the synchronization executed, and it is useful to execute certain actions with the data synchronized
         * such logging, notifications, etc...
         */
        default void afterSync() {
        }

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
     *          public void getData() {
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
     *
     *         @Override
     *         public String[] getColumns() {
     *              return new String[] { "user_id", "car_id"};
     *         }
     *
     *     }
     * }
     * </pre>
     */
    public interface BatchQuery<V> {

        /**
         * Method to get the data to use in the batch query
         *
         * @return the updated data as {@link List} of {@link V}
         */
        List<V> getData();

        /**
         * Method to prepare the batch query such fill the parameters programmatically
         *
         * @param query Query instance used to execute the SQL command
         * @param index The pre-increment index to format the values in the query, its initial value is 1
         * @param items The updated items to use in the batch query
         */
        void prepareQuery(Query query, int index, List<V> items);

        /**
         * Method to get the columns used in the query
         *
         * @return columns used in the query as array of {@link String}
         */
        String[] getColumns();

    }

    /**
     * The {@code ComplexBatchItem} interface is useful to execute the {@link #syncBatch(SyncBatchModel, String, BatchQuery)}
     * method with complex custom object with different values to synchronize. For example:
     *
     * <pre>
     * {@code
     *      public class Car {
     *
     *          private String model;
     *
     *          private String plate;
     *
     *          // constr, getter, setter ...
     *
     *      }
     * }
     * </pre>
     *
     * @author N7ghtm4r3 - Tecknobit
     */
    public interface ComplexBatchItem {

        /**
         * Method used to customize the instances of the complex object are to use in the synchronization
         *
         * @return the custom instances of the complex object as {@link List} of "?"
         */
        List<?> mappedValues();

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
     * @param model Contains the data about the synchronization such the columns affected and the current list of the data
     * @param table The table where execute the synchronization of the data
     * @param batchQuery The manager of the batch query to execute
     */
    protected <V> void syncBatch(SyncBatchModel model, String table, BatchQuery<V> batchQuery) {
        List<V> updatedData = batchQuery.getData();
        ArrayList<V> currentData = model.getCurrentData();
        batchInsert(INSERT_IGNORE_INTO, table, batchQuery);
        currentData.removeAll(updatedData);
        batchDelete(table, currentData, model.getDeletingColumns());
        model.afterSync();
    }

    /**
     * Method to execute a batch insert of a list of data simultaneously
     *
     * @param command The insertion command to execute
     * @param table The table where execute the batch insert
     * @param batchQuery The manager of the batch query to execute
     */
    protected <V> void batchInsert(InsertCommand command, String table, BatchQuery<V> batchQuery) {
        List<V> values = batchQuery.getData();
        if (values.isEmpty())
            return;
        String[] columns = batchQuery.getColumns();
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
    protected void batchDelete(String table, List<?> values, @NotNull String... columns) {
        int columnsNumber = columns.length;
        if (columnsNumber == 0 || values.isEmpty())
            return;
        String columnsFormated = formatValuesForQuery(OPENED_ROUND_BRACKET, Arrays.stream(columns).toList(),
                null, true);
        String inClause = formatInClause(columnsNumber, values);
        String deleteQuery = DELETE_FROM_ + table + _WHERE_ + columnsFormated + _IN_CLAUSE_ + inClause;
        Query query = entityManager.createNativeQuery(deleteQuery);
        query.executeUpdate();
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
        if (inValues.get(0) instanceof ComplexBatchItem)
            return formatComplexBatchItemInClause(columns, inValues);
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
     * Method to format the in clause for the query with {@link ComplexBatchItem} values
     *
     * @param columns  The columns number
     * @param inValues The complex batch items used in the in clause to format
     * @return the in clause formatted as {@link String}
     * @apiNote clause formatted: ((col1, col2), (col1a, col2a), ...)
     */
    private String formatComplexBatchItemInClause(int columns, List<?> inValues) {
        StringBuilder inClause = new StringBuilder(OPENED_ROUND_BRACKET);
        int totalValues = inValues.size();
        int lastValue = totalValues - 1;
        int lastColumn = columns - 1;
        for (int j = 0; j < totalValues; j++) {
            if (columns > 1)
                inClause.append(OPENED_ROUND_BRACKET);
            List<?> inClauseParts = ((ComplexBatchItem) inValues.get(j)).mappedValues();
            int clauseParts = inClauseParts.size();
            int lastPart = clauseParts - 1;
            for (int i = 0; i < clauseParts; i++) {
                Object inClausePart = inClauseParts.get(i);
                inClause.append(SINGLE_QUOTE).append(inClausePart).append(SINGLE_QUOTE);
                if (i < lastPart)
                    inClause.append(COMMA);
            }
            if (columns > 1)
                inClause.append(CLOSED_ROUND_BRACKET);
            if (j < lastValue)
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