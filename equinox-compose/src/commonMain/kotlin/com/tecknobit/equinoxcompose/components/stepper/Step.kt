package com.tecknobit.equinoxcompose.components.stepper

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource

/**
 * The information related to a single step to represent in the [Stepper] component
 *
 * @property actionControls The controls available for the current step, if `null` will be used the default actions
 * @property initiallyExpanded Whether the step is initially expanded
 * @property enabled Whether the step depends on specific scenario to be enabled
 * @property stepIcon The representative icon of the step
 * @property title The title of the step
 * @property content The content of the step (see the [StepContent] annotation)
 * @property isError The state used to indicate whether the step is currently in error
 * @property dismissAction The action to execute when the action dismissed
 * @property dismissIcon The representative icon to indicate to dismiss the current action
 * @property confirmAction The action to execute when the action confirmed
 * @property confirmIcon The representative icon to indicate to confirm the current action
 *
 * @author N7ghtm4r3
 *
 * @since 1.0.7
 */
@ExperimentalComposeApi
data class Step(
    val actionControls: @Composable (() -> Unit)? = null,
    val initiallyExpanded: Boolean = false,
    val enabled: MutableState<Boolean>? = null,
    val stepIcon: ImageVector,
    val title: StringResource,
    val content: @Composable ColumnScope.() -> Unit,
    val isError: (MutableState<Boolean>)? = null,
    val dismissAction: ((MutableState<Boolean>) -> Unit)? = null,
    val dismissIcon: ImageVector = Icons.Default.Cancel,
    val confirmAction: (MutableState<Boolean>) -> Unit = { it.value = false },
    val confirmIcon: ImageVector = Icons.Default.CheckCircle,
)