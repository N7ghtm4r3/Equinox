package com.tecknobit.equinoxcore.entities

/**
 * The `Updatable` interface wraps entity-specific update behavior behind a shared operation
 *
 * The entity encapsulates the field assignments, while callers only invoke [update]:
 * ```kotlin
 * override fun update(newDetails: ProductDetails?) {
 *     name = newDetails.name
 *     price = newDetails.price
 * }
 *
 * product.update(newDetails)
 * ```
 *
 * @param D The type of details used to update the entity
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 1.2.1
 */
interface Updatable<D> {

    /**
     * Method used to update the entity with the supplied details
     *
     * Each implementation defines which properties are updated and how null details are handled
     *
     * @param newDetails The new details to apply to the entity, which may be null
     */
    fun update(newDetails: D?)

}