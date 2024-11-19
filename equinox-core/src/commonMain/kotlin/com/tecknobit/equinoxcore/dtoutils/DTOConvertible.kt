package com.tecknobit.equinoxcore.dtoutils

/**
 * The `DTOConvertible` interface allows to convert a complete object into the related Data Transfer Object
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 1.0.5
 */
interface DTOConvertible<T> {

    /**
     * Method to convert the object to related Transfer Data Object
     *
     * @return the DTO as [T]
     */
    fun convertToRelatedDTO(): T

}
