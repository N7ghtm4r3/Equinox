package com.tecknobit.equinoxcore.annotations

/**
 * The `Attachment` annotation is useful to indicate those methods which provide an additional behavior to a component,
 * such for example requests to backend, data retrieval, etc... that need to be marked with the `Composable` annotation due
 * `LaunchedEffect` usage or similar
 *
 * ```kotlin
 * @Composable
 * fun MyComponent() {
 *     retrieveData()
 *    // rest of the component
 * }
 *
 * @Attachment
 * @Composable
 * @Suppress("ComposableNaming")
 * private fun retrieveData() {
 *     LaunchedEffect(Unit) {
 *         // your logic
 *     }
 * }
 * ```
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 1.1.9
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class Attachment(

    /**
     * The behavior added to the component
     */
    val behavior: String = "",

    )