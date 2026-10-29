package com.tecknobit.equinoxbackend.utils;

import com.tecknobit.equinoxcore.annotations.Returner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The {@code FiltersUtil} class provides utility methods to handle filters
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 2.0.0
 */
public class FiltersUtil {

    /**
     * Method used to resolve enum filters into a set of constant names
     *
     * <p>A {@code null} or empty filter set selects every constant declared by
     * {@code enumType}. The supplied filter set is not modified
     *
     * @param <E>      The enum type of the filters
     * @param filters  The constants to select, or {@code null} or an empty set to select all constants
     * @param enumType The enum class used to resolve all constants when no filters are supplied
     *
     * @return the selected enum constant names in a new set as {@link Set}
     * @throws NullPointerException if a selected filter is {@code null}, or {@code enumType}
     *                              is {@code null} when the filter set is {@code null} or empty
     */
    @Returner
    public static <E extends Enum<?>> Set<String> resolveEnumSetFilters(Set<E> filters, Class<E> enumType) {
        Set<String> resolvedFilters = new HashSet<>();
        if (filters == null || filters.isEmpty()) {
            List<E> enumEntries = Arrays.stream(enumType.getEnumConstants()).toList();

            filters = new HashSet<>(enumEntries);
        }

        for (E type : filters)
            resolvedFilters.add(type.name());

        return resolvedFilters;
    }

}
