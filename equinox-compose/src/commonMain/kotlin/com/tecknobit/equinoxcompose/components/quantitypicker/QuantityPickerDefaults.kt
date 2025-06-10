package com.tecknobit.equinoxcompose.components.quantitypicker

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * The `QuantityPickerDefaults` object contains the default properties for the [QuantityPicker] component
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 1.1.3
 */
object QuantityPickerDefaults {

    /**
     * `DisabledContainerAlpha` the constant value for the opacity to apply to the indicators container when the
     * [QuantityPicker] is disabled
     */
    private const val DisabledContainerAlpha: Float = 0.12f

    /**
     * `DisabledIconAlpha` the constant value for the opacity to apply to the icons of the indicators when the
     * [QuantityPicker] is disabled
     */
    private const val DisabledIconAlpha: Float = 0.38f

    /**
     * `IndicatorButtonShape` the default shape for an indicator button
     */
    val IndicatorButtonShape = CircleShape

    /**
     * `IndicatorButtonSize` the default size for an indicator button
     */
    val IndicatorButtonSize = 30.dp

    /**
     * `ContainerColor` the default color of the container of an indicator button
     */
    private val ContainerColor: Color
        @Composable get() = MaterialTheme.colorScheme.primary

    /**
     * `DisabledContainerColor` the default color of the container of an indicator button when the picker is disabled
     */
    private val DisabledContainerColor: Color
        @Composable get() = MaterialTheme.colorScheme.onSurface.copy(
            alpha = DisabledContainerAlpha
        )

    /**
     * `DisabledIconColor` the default color of the icon of an indicator button when the picker is disabled
     */
    private val DisabledIconColor: Color
        @Composable get() = MaterialTheme.colorScheme.onSurface.copy(
            alpha = DisabledIconAlpha
        )

    /**
     * The `QuantityPickerColors` data class allows to customize the colors of the picker
     *
     * @param containerColor The color of the container of an indicator button
     * @param iconColor The color of the icon of an indicator button when the picker is enabled
     * @param disabledContainerColor The color of the container of an indicator button when the picker is disabled
     * @param disabledIconColor The color of the icon of an indicator button when the picker is disabled
     *
     * @author N7ghtm4r3 - Tecknobit
     */
    data class QuantityPickerColors(
        val containerColor: Color,
        val iconColor: Color,
        val disabledContainerColor: Color,
        val disabledIconColor: Color,
    )

    /**
     * Method used to customize the picker colors
     *
     * @param containerColor The color of the container of an indicator button
     * @param iconColor The color of the icon of an indicator button when the picker is enabled
     * @param disabledContainerColor The color of the container of an indicator button when the picker is disabled
     * @param disabledIconColor The color of the icon of an indicator button when the picker is disabled
     *
     * @return the colors to apply as [QuantityPickerColors]
     */
    @Composable
    fun colors(
        containerColor: Color = ContainerColor,
        iconColor: Color = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor: Color = DisabledContainerColor,
        disabledIconColor: Color = DisabledIconColor,
    ): QuantityPickerColors {
        return QuantityPickerColors(
            containerColor = containerColor,
            iconColor = iconColor,
            disabledContainerColor = disabledContainerColor,
            disabledIconColor = disabledIconColor
        )
    }

}