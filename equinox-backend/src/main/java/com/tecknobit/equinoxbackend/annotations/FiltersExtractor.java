package com.tecknobit.equinoxbackend.annotations;

/**
 * The {@code FiltersExtractor} annotation is useful to indicate those methods used to extract from the raw filters set
 * the specific set indicates by the method. For example:
 *
 * <pre>
 *     {@code
 *          @FiltersExtractor(
 *              description = "Method used to extracts the plate filters to use"
 *          )
 *          private HashSet<String> extractPlateFilters() {
 *              return extractFiltersByPattern(your_pattern); // the specific pattern to extract the plates
 *          }
 *     }
 * </pre>
 *
 * @author N7ghtm4r3 - Tecknobit
 * @since 1.0.7
 */
public @interface FiltersExtractor {


    /**
     * Short description to indicate what the method will extract
     */
    String description() default "";

}
