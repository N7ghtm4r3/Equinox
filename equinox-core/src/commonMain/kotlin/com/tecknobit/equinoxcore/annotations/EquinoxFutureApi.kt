package com.tecknobit.equinoxcore.annotations

import kotlin.annotation.AnnotationTarget.*

/**
 * The `EquinoxFutureApi` annotation is useful to indicate an experimental api implemented in an external project outside
 * `Equinox` that can be potentially implemented in the official library
 *
 * Usage Example:
 * ```kotlin
 * @EquinoxFutureApi(
 *     protoBehavior = """
 *         At the moment the proto api behaves etc...
 *     """ // text block suggested,
 *     releaseVersion = "1.0.9",
 *     additionalNotes = """
 *         - More customization
 *         - Add more styles
 *         - ...
 *     """ // text block suggested
 * )
 * class PotentialApi {
 *      ... behavior
 * }
 * ```
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 1.0.8
 */
@MustBeDocumented
@Target(
    allowedTargets = [CLASS, ANNOTATION_CLASS, TYPE_PARAMETER, PROPERTY, FIELD, LOCAL_VARIABLE, VALUE_PARAMETER,
        CONSTRUCTOR, FUNCTION, PROPERTY_GETTER, PROPERTY_SETTER, TYPE, EXPRESSION, FILE, TYPEALIAS]
)
@Retention(AnnotationRetention.SOURCE)
annotation class EquinoxFutureApi(

    /**
     * The prototypical behavior the api currently has before the official integration
     */
    val protoBehavior: String = "",

    /**
     * The version of the release candidate to implement this api
     */
    val releaseVersion: String = "",

    /**
     * Additional notes about some improvements or any requirements to implement in the official api
     */
    val additionalNotes: String = "",
)
