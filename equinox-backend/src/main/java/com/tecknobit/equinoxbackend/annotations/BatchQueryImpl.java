package com.tecknobit.equinoxbackend.annotations;

/**
 * The {@code BatchQueryImpl} annotation is useful to indicate an implementation of a
 * {@link com.tecknobit.equinoxbackend.environment.services.builtin.service.EquinoxItemsHelper.BatchQuery}
 *
 * <pre>
 * {@code
 *
 * // The Simple object represents a table with the id, simple, and week columns
 *
 * @BatchQueryImpl(
 *  // not mandatory
 *   description= """
 *          Informative description about the behavior of the implemented batch query"
 *         """ // suggested text block
 * )
 * public class SimpleBatchQuery implements EquinoxItemsHelper.BatchQuery<Simple> {
 *
 *     private List<Simple> data;
 *
 *     public SimpleBatchQuery(List<Simple> data) {
 *         this.data = data;
 *     }
 *
 *     @Override
 *     public Collection<Simple> getData() {
 *         return data;
 *     }
 *
 *     @Override
 *     public void prepareQuery(Query query, int index, Collection<Simple> items) {
 *         for (Simple item : items) {
 *             query.setParameter(index++, item.getId());
 *             query.setParameter(index++, item.getSimple());
 *             query.setParameter(index++, item.getWeek());
 *         }
 *     }
 *
 *     @Override
 *     public String[] getColumns() {
 *         return new String[]{"id", "simple", "week"};
 *     }
 *
 * }
 * }
 * </pre>
 *
 * @author N7ghtm4r3 - Tecknobit
 * @since 1.0.8
 */
public @interface BatchQueryImpl {

    /**
     * Informative description about the behavior of the implemented batch query
     */
    String description() default "";

}
