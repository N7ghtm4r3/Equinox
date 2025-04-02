package com.tecknobit.equinoxcompose.components.quantitypicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.rememberSaveable
import com.tecknobit.equinoxcore.annotations.Wrapper

/**
 * Method used to create and remember during the recompositions the state for the [QuantityPicker] component
 *
 * @param initialQuantity The initial quantity selected
 * @param minQuantity The minimum threshold quantity allowed
 * @param maxQuantity The maximum threshold quantity allowed
 * @param longPressQuantity The quantity to decrement or increment when the user long press (or double-clicked) the
 * quantity buttons
 *
 * @return the state as [QuantityPickerState]
 */
@Composable
@ExperimentalComposeApi
fun rememberQuantityPickerState(
    initialQuantity: Int = 0,
    minQuantity: Int = Int.MIN_VALUE,
    maxQuantity: Int = Int.MAX_VALUE,
    longPressQuantity: Int? = null,
): QuantityPickerState {
    val quantityPickerState = rememberSaveable(
        stateSaver = QuantityPickerSaver
    ) {
        mutableStateOf(
            QuantityPickerState(
                initialQuantity = initialQuantity,
                minQuantity = minQuantity,
                maxQuantity = maxQuantity,
                longPressQuantity = longPressQuantity
            )
        )
    }
    return quantityPickerState.value
}

/**
 * The `QuantityPickerState` class is useful to handle the [QuantityPicker] component lifecycle
 *
 * @property initialQuantity The initial quantity selected
 * @property currentQuantity The current quantity picked
 * @property minQuantity The minimum threshold quantity allowed
 * @property maxQuantity The maximum threshold quantity allowed
 * @property longPressQuantity The quantity to decrement or increment when the user long press (or double-clicked) the
 * quantity buttons
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 1.1.0
 */
class QuantityPickerState internal constructor(
    val initialQuantity: Int = 0,
    val currentQuantity: Int = initialQuantity,
    val minQuantity: Int = Int.MIN_VALUE,
    val maxQuantity: Int = Int.MAX_VALUE,
    val longPressQuantity: Int? = null,
) {

    /**
     * `quantity` the state used to manage the quantity picked
     */
    private val quantity = mutableStateOf(currentQuantity)

    /**
     * `quantityPicked` the current quantity picked
     */
    val quantityPicked: Int
        get() = quantity.value

    /**
     * `longPressEnabled` whether the [longPressQuantity] option is enabled
     */
    val longPressEnabled = longPressQuantity != null && longPressQuantity > 1

    /**
     * Method used to increment the [quantity] value by the amount specified in [longPressQuantity]
     */
    @Wrapper
    fun longIncrement() {
        require(longPressQuantity != null && longPressQuantity > 1)
        increment(
            incrementValue = longPressQuantity
        )
    }

    /**
     * Method used to simply increment the [quantity] value by one
     */
    @Wrapper
    fun simpleIncrement() {
        increment(
            incrementValue = 1
        )
    }

    /**
     * Method used to increment the [quantity] value
     *
     * @param incrementValue The increment amount to add to the current [quantity] value
     */
    private fun increment(
        incrementValue: Int,
    ) {
        val incrementedQuantity = quantity.value + incrementValue
        if (incrementedQuantity <= maxQuantity)
            quantity.value += incrementValue
    }

    /**
     * Method used to decrement the [quantity] value by the amount specified in [longPressQuantity]
     */
    @Wrapper
    fun longDecrement() {
        require(longPressQuantity != null && longPressQuantity > 1)
        decrement(
            decrementValue = longPressQuantity
        )
    }

    /**
     * Method used to simply decrement the [quantity] value by one
     */
    @Wrapper
    fun simpleDecrement() {
        decrement(
            decrementValue = 1
        )
    }

    /**
     * Method used to decrement the [quantity] value
     *
     * @param decrementValue The decrement amount to subtract from the current [quantity] value
     */
    private fun decrement(
        decrementValue: Int,
    ) {
        val decrementedQuantity = quantity.value - decrementValue
        if (decrementedQuantity >= minQuantity)
            quantity.value -= decrementValue
    }

    /**
     * Method used to reset the current [quantity] picked as the [initialQuantity] amount
     */
    fun reset() {
        quantity.value = initialQuantity
    }

}

/**
 * The `QuantityPickerSaver` custom saver allows to keep in memory the [QuantityPickerState] during the recompositions
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @see Saver
 *
 * @since 1.1.0
 */
internal object QuantityPickerSaver : Saver<QuantityPickerState, Array<Int?>> {

    /**
     * Convert the restored value back to the original Class. If null is returned the value will
     * not be restored and would be initialized again instead.
     */
    override fun restore(
        value: Array<Int?>,
    ): QuantityPickerState {
        return QuantityPickerState(
            initialQuantity = value[0]!!,
            currentQuantity = value[1]!!,
            minQuantity = value[2]!!,
            maxQuantity = value[3]!!,
            longPressQuantity = value[4]
        )
    }

    /**
     * Convert the value into a saveable one. If null is returned the value will not be saved.
     */
    override fun SaverScope.save(
        value: QuantityPickerState,
    ): Array<Int?> {
        return arrayOf(
            value.initialQuantity,
            value.currentQuantity,
            value.minQuantity,
            value.maxQuantity,
            value.longPressQuantity
        )
    }

}