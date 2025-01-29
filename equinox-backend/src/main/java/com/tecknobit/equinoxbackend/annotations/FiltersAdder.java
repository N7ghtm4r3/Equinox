package com.tecknobit.equinoxbackend.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@code FiltersAdder} annotation is useful to indicate those methods used to adding the extracted filters to the
 * filtered query. For example:
 *
 * <pre>
 *     {@code
 *          @FiltersAdder(
 *              description = "Will be added to the query the plates such AA000AA, etc..."
 *          )
 *          private void addPlates() {
 *              HashSet<String> names = extractPlatesFilters(); // extract the specific filters from the rawFilters list
 *              if (names != null) {
 *                  Predicate nameIn = root.get("model").in(names);
 *                  predicates.add(nameIn); // add the created predicate to the predicates list
 *              }
 *          }
 *     }
 * </pre>
 *
 * @author N7ghtm4r3 - Tecknobit
 * @since 1.0.7
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface FiltersAdder {

    /**
     * Short description to indicate what filters will be added to the query
     */
    String description() default "";

}
