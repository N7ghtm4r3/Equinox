package com.tecknobit.equinoxcompose.components.stepper

/**
 * The `StepContent` annotation is used to mark the methods which are the content of a step of the [Stepper] component.
 * It indicates the number of step and whether the step is enabled in specific scenarios.
 *
 * ```kotlin
 * @Composable
 * @StepContent(
 *      number = 0,
 *      enabledWhen = "This step is enabled when etc..."
 * )
 * private fun Check() {
 *     Row(
 *         modifier = Modifier.fillMaxWidth(),
 *         verticalAlignment = Alignment.CenterVertically
 *     ) {
 *         Checkbox(
 *             checked = checked.value,
 *             onCheckedChange = { checked.value = it }
 *         )
 *         Text(
 *             text = "All code is okay"
 *         )
 *     }
 * }
 * ```
 *
 * @since 1.0.7
 * @author N7ghtm4r3 - Tecknobit
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION)
annotation class StepContent(

    /**
     * `number` the number of the step occupied in the [Stepper]
     */
    val number: Int,

    /**
     * `enabledWhen` when this step is enabled if its enabling is conditional
     */
    val enabledWhen: String = "",

    )
