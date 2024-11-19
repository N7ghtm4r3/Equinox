package com.tecknobit.equinoxcore.annotations

import kotlin.annotation.AnnotationRetention.SOURCE

/**
 * The `CustomParametersOrder` annotation is useful to manage the custom parameters order for a better readability
 * of the code and to work with that parameters correctly
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 1.0.4
 */
@Retention(SOURCE)
annotation class CustomParametersOrder(
    /**
     * The order of the custom parameters of a user customization
     */
    val order: Array<String>
)
