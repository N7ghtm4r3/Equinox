package com.tecknobit.equinoxcore.annotations

import kotlin.annotation.AnnotationRetention.SOURCE
import kotlin.annotation.AnnotationTarget.*

/**
 * The `RequiresSuperCall` annotation is useful to indicate that an inherited or an overridden method to works correctly
 * and completely requires that also the original method is invoked like the [@CallSuper](https://developer.android.com/reference/androidx/annotation/CallSuper)
 * annotation.
 *
 * This annotation is useful for enforcing that subclasses do not neglect to invoke
 * their super class method, thereby maintaining the expected functionality and state
 *
 * #### Usage example
 * ```kotlin
 * open class Vehicle {
 *
 *     @RequiresSuperCall
 *     open fun startEngine() {
 *         // general logic to start the engine
 *     }
 *
 * }
 *
 * class Car : Vehicle() {
 *
 *     @RequiresSuperCall
 *     override fun startEngine() {
 *         super.startEngine() // required for a complete and correct logic
 *         // Car specific logic to start engine
 *     }
 *
 * }
 * ```
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 1.0.5
 */
@Retention(SOURCE)
@Target(CONSTRUCTOR, FUNCTION, PROPERTY_GETTER, PROPERTY_SETTER)
annotation class RequiresSuperCall