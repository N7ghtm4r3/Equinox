package com.tecknobit.equinoxbackend.utils;

import com.tecknobit.equinoxcore.annotations.Returner;
import com.tecknobit.equinoxcore.annotations.Wrapper;
import com.tecknobit.equinoxcore.dtoutils.DTOConvertible;
import org.apache.logging.log4j.util.TriConsumer;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The {@code DtoUtil} class is useful to convert entity lists to their related DTOs
 *
 * <p>The conversion methods are experimental. Entities implement {@link DTOConvertible};
 * conversion can use their default mapping or a supplied converter
 *
 * <p>Each conversion returns a new list in source order, including an empty list
 * for an empty input. Optional callbacks run synchronously after each conversion and before
 * the DTO is added to the result. Exceptions raised by a converter or callback propagate
 * to the caller and stop conversion
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 2.0.0
 */
public class DtoUtil {

    /**
     * Method used to convert entities to their related DTOs
     *
     * <p>Each entity supplies its DTO through {@link DTOConvertible#convertToRelatedDTO()}.
     * The requested {@code D} type must match the DTO type returned by the entities
     *
     * @param <E>      The entity type implementing {@link DTOConvertible}
     * @param <D>      The DTO type returned by each entity
     * @param entities The non-null list of non-null entities to convert
     *
     * @return the new list of DTOs in source order as {@link List}
     * @throws NullPointerException if {@code entities} or one of its elements is {@code null}
     */
    @Wrapper
    @Returner
    @ApiStatus.Experimental
    public static <E extends DTOConvertible<?>, D> List<D> convertToDTOs(List<E> entities) {
        return convertToDTOs(entities, (BiConsumer<E, D>) null);
    }

    /**
     * Method used to convert entities to their related DTOs and process each converted DTO
     *
     * <p>The callback receives the source entity and its DTO after conversion and before the DTO
     * is added to the result. The requested {@code D} type must match the DTO type returned
     * by the entities
     *
     * @param <E>             The entity type implementing {@link DTOConvertible}
     * @param <D>             The DTO type returned by each entity
     * @param entities        The non-null list of non-null entities to convert
     * @param afterConversion The callback invoked for each converted entity and DTO, or {@code null}
     *
     * @return the new list of DTOs in source order as {@link List}
     * @throws NullPointerException if {@code entities} or one of its elements is {@code null}
     */
    @Returner
    @ApiStatus.Experimental
    public static <E extends DTOConvertible<?>, D> List<D> convertToDTOs(List<E> entities, BiConsumer<E, D> afterConversion) {
        return convertToDTOs(entities, afterConversion, i -> (D) i.convertToRelatedDTO());
    }

    /**
     * Method used to convert entities to their related DTOs without an indexed callback
     *
     * <p>The conversion preserves the source order. The requested {@code D} type must match
     * the DTO type returned by the entities
     *
     * @param <E>      The entity type implementing {@link DTOConvertible}
     * @param <D>      The DTO type returned by each entity
     * @param entities The non-null list of non-null entities to convert
     *
     * @return the new list of DTOs in source order as {@link List}
     * @throws NullPointerException if {@code entities} or one of its elements is {@code null}
     */
    @Wrapper
    @Returner
    @ApiStatus.Experimental
    public static <E extends DTOConvertible<?>, D> List<D> convertToDTOsWithIndex(List<E> entities) {
        return convertToDTOsWithIndex(entities, (TriConsumer<E, Integer, D>) null);
    }

    /**
     * Method used to convert entities to their related DTOs and process each DTO with its source index
     *
     * <p>The callback receives the source entity, its zero-based index and the converted DTO,
     * in this order, before the DTO is added to the result. The requested {@code D} type must
     * match the DTO type returned by the entities
     *
     * @param <E>             The entity type implementing {@link DTOConvertible}
     * @param <D>             The DTO type returned by each entity
     * @param entities        The non-null list of non-null entities to convert
     * @param afterConversion The callback invoked for each entity, source index and DTO, or {@code null}
     *
     * @return the new list of DTOs in source order as {@link List}
     * @throws NullPointerException if {@code entities} or one of its elements is {@code null}
     */
    @Returner
    @ApiStatus.Experimental
    public static <E extends DTOConvertible<?>, D> List<D> convertToDTOsWithIndex(List<E> entities,
                                                                                  TriConsumer<E, Integer, D> afterConversion) {
        return convertToDTOsWithIndex(entities, afterConversion, i -> (D) i.convertToRelatedDTO());
    }

    /**
     * Method used to convert entities to DTOs with a custom converter
     *
     * <p>The converter supplies each DTO instead of the entity's default conversion method
     *
     * @param <E>       The entity type implementing {@link DTOConvertible}
     * @param <D>       The DTO type returned by the converter
     * @param entities  The non-null list of entities to convert
     * @param converter The function used to map each entity to a DTO
     *
     * @return the new list of DTOs in source order as {@link List}
     * @throws NullPointerException if {@code entities} is {@code null}, or {@code converter} is
     *         {@code null} and the list is not empty
     */
    @Wrapper
    @Returner
    @ApiStatus.Experimental
    public static <E extends DTOConvertible<?>, D> List<D> convertToDTOs(List<E> entities, Function<E, D> converter) {
        return convertToDTOs(entities, null, converter);
    }

    /**
     * Method used to convert entities to DTOs with a custom converter and process each converted DTO
     *
     * <p>The callback receives the source entity and the DTO returned by the converter,
     * before the DTO is added to the result
     *
     * @param <E>             The entity type implementing {@link DTOConvertible}
     * @param <D>             The DTO type returned by the converter
     * @param entities        The non-null list of entities to convert
     * @param afterConversion The callback invoked for each converted entity and DTO, or {@code null}
     * @param converter       The function used to map each entity to a DTO
     *
     * @return the new list of DTOs in source order as {@link List}
     * @throws NullPointerException if {@code entities} is {@code null}, or {@code converter} is
     *         {@code null} and the list is not empty
     */
    @Returner
    @ApiStatus.Experimental
    public static <E extends DTOConvertible<?>, D> List<D> convertToDTOs(List<E> entities, BiConsumer<E, D> afterConversion,
                                                                         Function<E, D> converter) {
        List<D> dtos = new ArrayList<>();

        for (E entity : entities) {
            D dto = converter.apply(entity);

            if (afterConversion != null)
                afterConversion.accept(entity, dto);

            dtos.add(dto);
        }

        return dtos;
    }

    /**
     * Method used to convert entities to DTOs with a custom converter without an indexed callback
     *
     * <p>The converter supplies each DTO instead of the entity's default conversion method
     *
     * @param <E>       The entity type implementing {@link DTOConvertible}
     * @param <D>       The DTO type returned by the converter
     * @param entities  The non-null list of entities to convert
     * @param converter The function used to map each entity to a DTO
     *
     * @return the new list of DTOs in source order as {@link List}
     * @throws NullPointerException if {@code entities} is {@code null}, or {@code converter} is
     *         {@code null} and the list is not empty
     */
    @Wrapper
    @Returner
    @ApiStatus.Experimental
    public static <E extends DTOConvertible<?>, D> List<D> convertToDTOsWithIndex(List<E> entities, Function<E, D> converter) {
        return convertToDTOsWithIndex(entities, null, converter);
    }

    /**
     * Method used to convert entities to DTOs with a custom converter and process each DTO with its source index
     *
     * <p>The callback receives the source entity, its zero-based index and the DTO returned
     * by the converter, in this order, before the DTO is added to the result
     *
     * @param <E>             The entity type implementing {@link DTOConvertible}
     * @param <D>             The DTO type returned by the converter
     * @param entities        The non-null list of entities to convert
     * @param afterConversion The callback invoked for each entity, source index and DTO, or {@code null}
     * @param converter       The function used to map each entity to a DTO
     *
     * @return the new list of DTOs in source order as {@link List}
     * @throws NullPointerException if {@code entities} is {@code null}, or {@code converter} is
     *         {@code null} and the list is not empty
     */
    @Returner
    @ApiStatus.Experimental
    public static <E extends DTOConvertible<?>, D> List<D> convertToDTOsWithIndex(List<E> entities,
                                                                                  TriConsumer<E, Integer, D> afterConversion,
                                                                                  Function<E, D> converter) {
        List<D> dtos = new ArrayList<>();

        for (int j = 0; j < entities.size(); j++) {
            E entity = entities.get(j);
            D dto = converter.apply(entity);

            if (afterConversion != null)
                afterConversion.accept(entity, j, dto);

            dtos.add(dto);

        }

        return dtos;
    }

}