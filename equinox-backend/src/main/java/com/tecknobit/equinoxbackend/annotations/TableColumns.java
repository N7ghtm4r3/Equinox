package com.tecknobit.equinoxbackend.annotations;

/**
 * The {@code TableColumns} annotation is useful to indicate the columns of a table and which order those columns have
 * in that table. It is particularly useful for those method which have to handling queries, for example the
 * {@link com.tecknobit.equinoxbackend.environment.services.builtin.service.EquinoxItemsHelper.BatchQuery} methods
 *
 * <pre>
 *     {@code
 *
 *           // The Simple object represents a table with the id, simple and week columns
 *
 *           BatchQuery<Simple> batchQuery = new BatchQuery<>() {
 *             ...
 *
 *             @Override
 *             @TableColumns(columns = {"id", "simple", "week"})
 *             public void prepareQuery(Query query, int index, Collection<Simple> items) {
 *                 for (Simple item : items) {
 *                     query.setParameter(index++, item.getId());
 *                     query.setParameter(index++, item.getSimple());
 *                     query.setParameter(index++, item.getWeek());
 *                 }
 *             }
 *
 *             @Override
 *             @TableColumns(columns = {"id", "simple", "week"})
 *             public String[] getColumns() {
 *                 return new String[]{"id", "simple", "week"};
 *             }
 *         };
 *     }
 * </pre>
 *
 * @author N7ghtm4r3 - Tecknobit
 * @since 1.0.8
 */
public @interface TableColumns {

    /**
     * The list of the columns in the table
     */
    String[] columns();

}
