package com.tecknobit.equinoxcore.annotations

import kotlin.annotation.AnnotationTarget.*

/**
 * The `RequiresDocumentation` annotation is used to indicate that a snippet of code, or an entire section,
 * still needs to be documented. This annotation serves as a reminder to complete the documentation
 * before publishing or using the code
 *
 * #### Usage example
 * ```kotlin
 * @RequiresDocumentation
 * fun newApi() {
 *      // code
 * }
 *
 *
 * @RequiresDocumentation(
 *      additionalNotes = """
 *          In the documentation must be included the release version
 *      """
 * )
 * fun newApiVersion() {
 *      // code
 * }
 * ```
 *
 * @property additionalNotes Additional information about what the documentation must have
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 1.1.6
 */
@Target(
    allowedTargets = [CLASS, ANNOTATION_CLASS, TYPE_PARAMETER, PROPERTY, FIELD, LOCAL_VARIABLE, VALUE_PARAMETER,
        CONSTRUCTOR, FUNCTION, PROPERTY_GETTER, PROPERTY_SETTER, TYPE, EXPRESSION, FILE, TYPEALIAS]
)
@Retention(value = AnnotationRetention.SOURCE)
annotation class RequiresDocumentation(
    val additionalNotes: String = ""
)
