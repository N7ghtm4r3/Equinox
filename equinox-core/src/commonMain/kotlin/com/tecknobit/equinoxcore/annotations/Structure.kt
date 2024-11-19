package com.tecknobit.equinoxcore.annotations

import kotlin.annotation.AnnotationRetention.SOURCE
import kotlin.annotation.AnnotationTarget.CLASS

/**
 * The `@Structure` annotation is applied to classes that define a general structure
 * and behavior for a potential hierarchy, along with its subclasses. It marks classes
 * that provide a common template or foundation, often used for sharing general properties
 * and functionality across a set of related classes.
 *
 * This annotation is useful when creating base classes that represent common characteristics
 * or behaviors which should be shared by subclasses. It signifies that the annotated class
 * is intended to be part of an inheritance hierarchy where subclasses should follow a
 * consistent structure and can override or extend its behavior.
 *
 * Usage Example:
 * ```kotlin
 * @Structure
 * open class Vehicle {
 *
 *     val plate: String
 *
 *     val horsePower: Int
 *
 * }
 *
 * class Car : Vehicle() {
 *
 *     val plate: String
 *         get = // logic to retrieve the plate value
 *
 *     val horsePower: Int
 *         get = // logic to retrieve the horse power value
 *
 * }
 * ```
 * @author N7ghtm4r3 - Tecknobit
 */
@Retention(SOURCE)
@Target(CLASS)
annotation class Structure 
