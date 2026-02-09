package com.tecknobit.equinoxcore.annotations

/**
 * The `Implementation` annotation is useful for indicating the methods or components that provide the real implementation
 * of the logic or behavior of a non-exposed method or component that wraps the real one
 *
 * ```kotlin
 * @Composable
 * fun CancelButton() {
 *     ActionButtonImpl(
 *          onAction = {
 *              cancel()
 *          }
 *     )
 * }
 *
 * @Composable
 * fun ConfirmButton() {
 *     ActionButtonImpl(
 *          onAction = {
 *              confirm()
 *          }
 *     )
 * }
 *
 * @Composable
 * @Implementation
 * private fun ActionButtonImpl(
 *      onAction:() -> Unit
 * ) {
 *     // actual button implementation
 * }
 *
 * ```
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 1.1.9
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class Implementation