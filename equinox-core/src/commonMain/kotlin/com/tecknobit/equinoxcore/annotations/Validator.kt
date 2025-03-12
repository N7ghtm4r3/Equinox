package com.tecknobit.equinoxcore.annotations

import kotlin.annotation.AnnotationRetention.SOURCE
import kotlin.annotation.AnnotationTarget.FUNCTION

/**
 * The `@Validator` annotation is applied to those methods which are used to check the validity of an input, and it is
 * useful to provide additional information about the annotated method such the validity case of the input checked
 *
 * Example usage:
 * ```kotlin
 * @Validator(
 *     validWhen = "The input is valid when is not null" // not mandatory
 * )
 * fun inputValid(
 *     input: Any?
 * ) : Boolean {
 *     return input != null
 * }
 * ```
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 1.0.9
 */
@Retention(SOURCE)
@Target(FUNCTION)
annotation class Validator(
    val validWhen: String = "",
)
