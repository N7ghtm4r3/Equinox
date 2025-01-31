@file:OptIn(ExperimentalComposeApi::class)

package com.tecknobit.equinoxcompose.components.stepper

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource

/**
 * This component allows to create a dynamic interaction with the user dividing for example a long procedure such item
 * creation, customization, etc... in different specific steps where the user can interact
 *
 * @param modifier The modifier to apply to the component
 * @param headerSection A custom header of the stepper
 * @param startStepShape The shape to apply to the first visible [Step]
 * @param middleStepShape The shape to apply to those steps in the middle
 * @param finalStepShape The shape to apply to the last visible [Step]
 * @param stepBackgroundColor The color to use as background of the [Step] container
 * @param errorColor The color used to indicate an error
 * @param confirmColor The color used to confirm an action
 * @param expandsStepIcon The icon used to expand each step
 * @param steps The steps to display by the component, a little tip is wrap the creation of the array with the
 * [remember] function to avoid to recreate the array during the recompositions
 */
@ExperimentalLayoutApi
@Composable
@NonRestartableComposable
fun Stepper(
    modifier: Modifier = Modifier,
    headerSection: @Composable (() -> Unit)? = null,
    startStepShape: Shape = RoundedCornerShape(
        topStart = 12.dp,
        topEnd = 12.dp
    ),
    middleStepShape: Shape = RectangleShape,
    finalStepShape: Shape = RoundedCornerShape(
        bottomStart = 12.dp,
        bottomEnd = 12.dp
    ),
    stepBackgroundColor: Color = MaterialTheme.colorScheme.surfaceContainerHighest,
    errorColor: Color = MaterialTheme.colorScheme.error,
    confirmColor: Color = MaterialTheme.colorScheme.primary,
    expandsStepIcon: ImageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
    vararg steps: Step,
) {
    val specialIndexes = remember { steps.mapSpecialIndexes() }
    val firstIndex = remember { specialIndexes.first }
    val lastIndex = remember { specialIndexes.second }
    val lastStep = remember { steps.last() }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        headerSection?.invoke()
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            steps.forEachIndexed { index, step ->
                AnimatedVisibility(
                    visible = step.isEnabled()
                ) {
                    val shape = when (index) {
                        0 -> startStepShape
                        firstIndex -> {
                            if (steps[0].isEnabled())
                                middleStepShape
                            else
                                startStepShape
                        }

                        lastIndex -> {
                            if (lastIndex != steps.lastIndex && lastStep.isEnabled())
                                middleStepShape
                            else
                                finalStepShape
                        }

                        steps.lastIndex -> finalStepShape
                        else -> middleStepShape
                    }
                    StepAction(
                        shape = shape,
                        errorColor = errorColor,
                        confirmColor = confirmColor,
                        stepBackgroundColor = stepBackgroundColor,
                        expandsStepIcon = expandsStepIcon,
                        step = step,
                        bottomDivider = if (lastStep.isEnabled())
                            index != steps.lastIndex
                        else
                            index != lastIndex
                    )
                }
            }
        }
    }
}

/**
 * Method to map the special indexes of the steps lit of the [Stepper] component.
 * The special indexes are those indexes near to [Step] which its enabling depends on a state
 *
 * @return the pair of the special indexes as [Pair] of [Int]
 */
private fun Array<out Step>.mapSpecialIndexes(): Pair<Int, Int> {
    val startIndex = this.findSpecialIndex(
        invalidSpecialIndex = -1,
        elementToCheck = this.first(),
        defaultIndexValue = 0
    )
    val tmpArray = this.reversedArray()
    val offset = this.size - 1
    val lastIndex = tmpArray.findSpecialIndex(
        elementToCheck = this.last(),
        defaultIndexValue = this.lastIndex,
        onAssign = { index -> offset - index }
    )
    return Pair(
        first = startIndex,
        second = lastIndex
    )
}

/**
 * Method to find the value of a special index
 *
 * @param invalidSpecialIndex The value of an index to consider as not valid
 * @param elementToCheck The step used to check the special index
 * @param defaultIndexValue The value to use as default special index when not found other
 * @param onAssign The lambda to execute to assign the value to the index
 *
 * @return the special index as [Int]
 */
private fun Array<out Step>.findSpecialIndex(
    invalidSpecialIndex: Int = 0,
    elementToCheck: Step,
    defaultIndexValue: Int,
    onAssign: (Int) -> Int = { it },
): Int {
    if (elementToCheck.enabled != null) {
        forEachIndexed { index, step ->
            if (index != invalidSpecialIndex && step.enabled == null)
                return onAssign.invoke(index)
        }
    }
    return defaultIndexValue
}

/**
 * Scoped function used to check whether a [Step] depends on a special condition to be enabled
 *
 * @return whether a [Step] depends on a special condition to be enabled as [Boolean]
 */
private fun Step.isEnabled(): Boolean {
    return this.enabled?.value ?: true
}

/**
 * Section to execute a profile action
 *
 * @param shape The shape for the container
 * @param bottomDivider Whether create the bottom divider
 */
@Composable
@NonRestartableComposable
private fun StepAction(
    shape: Shape,
    errorColor: Color,
    confirmColor: Color,
    stepBackgroundColor: Color,
    expandsStepIcon: ImageVector,
    step: Step,
    bottomDivider: Boolean = true,
) {
    Column {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = if (step.isError?.value == true)
                    errorColor
                else
                    stepBackgroundColor
            ),
            shape = shape
        ) {
            val expanded = rememberSaveable {
                mutableStateOf(step.initiallyExpanded)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = step.stepIcon,
                    contentDescription = null
                )
                Text(
                    modifier = Modifier
                        .padding(
                            start = 10.dp
                        ),
                    text = stringResource(step.title)
                )
                if (step.actionsControls != null)
                    step.actionsControls.invoke()
                else {
                    ActionControls(
                        expandsStepIcon = expandsStepIcon,
                        expanded = expanded,
                        dismissAction = step.dismissAction,
                        dismissIcon = step.dismissIcon,
                        dismissButtonColor = errorColor,
                        confirmAction = step.confirmAction,
                        confirmIcon = step.confirmIcon,
                        confirmColor = confirmColor
                    )
                }
            }
            AnimatedVisibility(
                visible = expanded.value
            ) {
                step.isError?.value = false
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    HorizontalDivider()
                    step.content.invoke(this)
                }
            }
        }
        if (bottomDivider)
            HorizontalDivider()
    }
}

/**
 * The controls action section to manage the [StepAction]
 *
 * @param expanded Whether the section is visible
 * @param dismissAction The action to execute when the action dismissed
 * @param confirmAction The action to execute when the action confirmed
 */
@Composable
@NonRestartableComposable
private fun ActionControls(
    expandsStepIcon: ImageVector,
    expanded: MutableState<Boolean>,
    dismissAction: (() -> Unit)?,
    dismissIcon: ImageVector,
    dismissButtonColor: Color,
    confirmIcon: ImageVector,
    confirmColor: Color,
    confirmAction: (MutableState<Boolean>) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        AnimatedVisibility(
            visible = expanded.value
        ) {
            Row {
                dismissAction?.let { action ->
                    IconButton(
                        onClick = action
                    ) {
                        Icon(
                            imageVector = dismissIcon,
                            contentDescription = null,
                            tint = dismissButtonColor
                        )
                    }
                }
                IconButton(
                    onClick = {
                        confirmAction.invoke(
                            expanded
                        )
                    }
                ) {
                    Icon(
                        imageVector = confirmIcon,
                        contentDescription = null,
                        tint = confirmColor
                    )
                }
            }
        }
        AnimatedVisibility(
            visible = !expanded.value
        ) {
            IconButton(
                onClick = { expanded.value = true }
            ) {
                Icon(
                    imageVector = expandsStepIcon,
                    contentDescription = null
                )
            }
        }
    }
}