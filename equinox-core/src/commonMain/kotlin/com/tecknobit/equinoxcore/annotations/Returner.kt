package com.tecknobit.equinoxcore.annotations

/**
 * The `Returner` annotation is used to mark methods that are responsible for formatting and returning
 * data in a specified format
 *
 * This annotation is typically applied to methods whose sole purpose is to transform input data
 * into a formatted version for use elsewhere in the application
 *
 * @since 1.0.6
 *
 * @author N7ghtm4r3 - Tecknobit
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION)
annotation class Returner
