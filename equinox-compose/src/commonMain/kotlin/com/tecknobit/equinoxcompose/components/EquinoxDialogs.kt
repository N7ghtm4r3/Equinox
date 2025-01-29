package com.tecknobit.equinoxcompose.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.tecknobit.equinoxcompose.resources.Res
import com.tecknobit.equinoxcompose.resources.confirm
import com.tecknobit.equinoxcompose.resources.dismiss
import com.tecknobit.equinoxcompose.viewmodels.EquinoxViewModel
import com.tecknobit.equinoxcore.annotations.Wrapper
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/**
 * Method to display a custom [AlertDialog]
 *
 * @param modifier The modifier to apply to the [AlertDialog]
 * @param titleModifier The modifier to apply to the title of the [AlertDialog]
 * @param titleStyle The style to apply to the title of the [AlertDialog]
 * @param show: whether show the alert dialog
 * @param icon The icon of the alert dialog
 * @param viewModel The viewmodel, if available, used in the context where the [AlertDialog] has been invoked, passing
 * it allows to manage in automatically the refresher, so suspend it or restarting it
 * @param onDismissAction The action to execute when the alert dialog has been dismissed
 * @param title The title of the alert dialog
 * @param text The text displayed in the alert dialog
 * @param dismissAction The action to execute when the user dismissed the action
 * @param dismissText The text of the dismiss [TextButton]
 * @param dismissTextStyle The style to apply to the dismiss text
 * @param confirmAction The action to execute when the used confirmed the action
 * @param confirmText The text of the confirm [TextButton]
 * @param confirmTextStyle The style to apply to the confirmation text
 */
@Composable
@NonRestartableComposable
fun EquinoxAlertDialog(
    modifier: Modifier = Modifier,
    titleModifier: Modifier = Modifier,
    titleStyle: TextStyle = TextStyle.Default,
    show: MutableState<Boolean>,
    icon: ImageVector? = null,
    viewModel: EquinoxViewModel? = null,
    onDismissAction: () -> Unit = {
        show.value = false
        viewModel?.restartRetriever()
    },
    title: StringResource,
    text: StringResource,
    dismissAction: () -> Unit = onDismissAction,
    dismissText: StringResource? = Res.string.dismiss,
    dismissTextStyle: TextStyle = TextStyle.Default,
    confirmAction: () -> Unit,
    confirmText: StringResource = Res.string.confirm,
    confirmTextStyle: TextStyle = TextStyle.Default,
) {
    EquinoxAlertDialog(
        modifier = modifier,
        titleModifier = titleModifier,
        titleStyle = titleStyle,
        show = show,
        icon = icon,
        viewModel = viewModel,
        onDismissAction = onDismissAction,
        title = title,
        text = {
            Text(
                text = stringResource(text),
                textAlign = TextAlign.Justify
            )
        },
        dismissAction = dismissAction,
        dismissText = dismissText,
        dismissTextStyle = dismissTextStyle,
        confirmAction = confirmAction,
        confirmText = confirmText,
        confirmTextStyle = confirmTextStyle
    )
}

/**
 * Method to display a custom [AlertDialog]
 *
 * @param modifier The modifier to apply to the [AlertDialog]
 * @param titleModifier The modifier to apply to the title of the [AlertDialog]
 * @param titleStyle The style to apply to the title of the [AlertDialog]
 * @param show: whether show the alert dialog
 * @param icon The icon of the alert dialog
 * @param viewModel The viewmodel, if available, used in the context where the [AlertDialog] has been invoked, passing
 * it allows to manage in automatically the refresher, so suspend it or restarting it
 * @param onDismissAction The action to execute when the alert dialog has been dismissed
 * @param title The title of the alert dialog
 * @param text The text displayed in the alert dialog
 * @param dismissAction The action to execute when the user dismissed the action
 * @param dismissText The text of the dismiss [TextButton]
 * @param dismissTextStyle The style to apply to the dismiss text
 * @param confirmAction The action to execute when the used confirmed the action
 * @param confirmText The text of the confirm [TextButton]
 * @param confirmTextStyle The style to apply to the confirmation text
 */
@Composable
@NonRestartableComposable
fun EquinoxAlertDialog(
    modifier: Modifier = Modifier,
    titleModifier: Modifier = Modifier,
    titleStyle: TextStyle = TextStyle.Default,
    show: MutableState<Boolean>,
    icon: ImageVector? = null,
    viewModel: EquinoxViewModel? = null,
    onDismissAction: () -> Unit = {
        show.value = false
        viewModel?.restartRetriever()
    },
    title: String,
    text: String,
    dismissAction: () -> Unit = onDismissAction,
    dismissText: String?,
    dismissTextStyle: TextStyle = TextStyle.Default,
    confirmAction: () -> Unit,
    confirmText: String,
    confirmTextStyle: TextStyle = TextStyle.Default,
) {
    EquinoxAlertDialog(
        modifier = modifier,
        titleModifier = titleModifier,
        titleStyle = titleStyle,
        show = show,
        icon = icon,
        viewModel = viewModel,
        onDismissAction = onDismissAction,
        title = title,
        text = {
            Text(
                text = text,
                textAlign = TextAlign.Justify
            )
        },
        dismissAction = dismissAction,
        dismissText = dismissText,
        dismissTextStyle = dismissTextStyle,
        confirmAction = confirmAction,
        confirmText = confirmText,
        confirmTextStyle = confirmTextStyle
    )
}

/**
 * Method to display a custom [AlertDialog]
 *
 * @param modifier The modifier to apply to the [AlertDialog]
 * @param titleModifier The modifier to apply to the title of the [AlertDialog]
 * @param titleStyle The style to apply to the title of the [AlertDialog]
 * @param show: whether show the alert dialog
 * @param icon The icon of the alert dialog
 * @param viewModel The viewmodel, if available, used in the context where the [AlertDialog] has been invoked, passing
 * it allows to manage in automatically the refresher, so suspend it or restarting it
 * @param onDismissAction The action to execute when the alert dialog has been dismissed
 * @param title The title of the alert dialog
 * @param text The text displayed in the alert dialog
 * @param dismissAction The action to execute when the user dismissed the action
 * @param dismissText The text of the dismiss [TextButton]
 * @param dismissTextStyle The style to apply to the dismiss text
 * @param confirmAction The action to execute when the used confirmed the action
 * @param confirmText The text of the confirm [TextButton]
 * @param confirmTextStyle The style to apply to the confirmation text
 */
@Composable
@NonRestartableComposable
fun EquinoxAlertDialog(
    modifier: Modifier = Modifier,
    titleModifier: Modifier = Modifier,
    titleStyle: TextStyle = TextStyle.Default,
    show: MutableState<Boolean>,
    icon: ImageVector? = null,
    viewModel: EquinoxViewModel? = null,
    onDismissAction: () -> Unit = {
        show.value = false
        viewModel?.restartRetriever()
    },
    title: StringResource,
    text: @Composable () -> Unit,
    dismissAction: () -> Unit = onDismissAction,
    dismissText: StringResource? = Res.string.dismiss,
    dismissTextStyle: TextStyle = TextStyle.Default,
    confirmAction: () -> Unit,
    confirmText: StringResource = Res.string.confirm,
    confirmTextStyle: TextStyle = TextStyle.Default,
) {
    EquinoxAlertDialog(
        modifier = modifier,
        titleModifier = titleModifier,
        titleStyle = titleStyle,
        show = show,
        icon = icon,
        viewModel = viewModel,
        onDismissAction = onDismissAction,
        title = stringResource(title),
        text = text,
        dismissAction = dismissAction,
        dismissText = if (dismissText != null)
            stringResource(dismissText)
        else
            null,
        dismissTextStyle = dismissTextStyle,
        confirmAction = confirmAction,
        confirmText = stringResource(confirmText),
        confirmTextStyle = confirmTextStyle
    )
}

/**
 * Method to display a custom [AlertDialog]
 *
 * @param modifier The modifier to apply to the [AlertDialog]
 * @param titleModifier The modifier to apply to the title of the [AlertDialog]
 * @param titleStyle The style to apply to the title of the [AlertDialog]
 * @param show: whether show the alert dialog
 * @param icon The icon of the alert dialog
 * @param viewModel The viewmodel, if available, used in the context where the [AlertDialog] has been invoked, passing
 * it allows to manage in automatically the refresher, so suspend it or restarting it
 * @param onDismissAction The action to execute when the alert dialog has been dismissed
 * @param title The title of the alert dialog
 * @param text The text displayed in the alert dialog
 * @param dismissAction The action to execute when the user dismissed the action
 * @param dismissText The text of the dismiss [TextButton]
 * @param dismissTextStyle The style to apply to the dismiss text
 * @param confirmAction The action to execute when the used confirmed the action
 * @param confirmText The text of the confirm [TextButton]
 * @param confirmTextStyle The style to apply to the confirmation text
 */
@Composable
@NonRestartableComposable
fun EquinoxAlertDialog(
    modifier: Modifier = Modifier,
    titleModifier: Modifier = Modifier,
    titleStyle: TextStyle = TextStyle.Default,
    show: MutableState<Boolean>,
    icon: ImageVector? = null,
    viewModel: EquinoxViewModel? = null,
    onDismissAction: () -> Unit = {
        show.value = false
        viewModel?.restartRetriever()
    },
    title: String,
    text: @Composable () -> Unit,
    dismissAction: () -> Unit = onDismissAction,
    dismissText: String?,
    dismissTextStyle: TextStyle = TextStyle.Default,
    confirmAction: () -> Unit,
    confirmText: String,
    confirmTextStyle: TextStyle = TextStyle.Default,
) {
    if (show.value) {
        viewModel?.suspendRetriever()
        AlertDialog(
            modifier = modifier,
            icon = {
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null
                    )
                }
            },
            onDismissRequest = onDismissAction,
            title = {
                Text(
                    modifier = titleModifier,
                    style = titleStyle,
                    text = title
                )
            },
            text = text,
            dismissButton = if (dismissText != null) {
                {
                    TextButton(
                        onClick = dismissAction
                    ) {
                        Text(
                            text = dismissText,
                            style = dismissTextStyle
                        )
                    }
                }
            } else
                null,
            confirmButton = {
                TextButton(
                    onClick = {
                        confirmAction()
                        viewModel?.restartRetriever()
                    }
                ) {
                    Text(
                        text = confirmText,
                        style = confirmTextStyle
                    )
                }
            }
        )
    }
}

/**
 * Simply [Dialog] wrapper to attach the [EquinoxViewModel]'s logic
 *
 * @param show: whether show the dialog
 * @param viewModel The viewmodel, if available, used in the context where the [Dialog] has been invoked, passing
 * it allows to manage in automatically the refresher, so suspend it or restarting it
 * @param dialogProperties The properties to apply to the [Dialog]
 * @param onDismissRequest The action to execute when the dialog has been dismissed
 * @param dialogContent The content of the [Dialog]
 */
@Wrapper(
    wrapperOf = "Dialog"
)
@Composable
@NonRestartableComposable
fun EquinoxDialog(
    show: MutableState<Boolean>,
    viewModel: EquinoxViewModel? = null,
    dialogProperties: DialogProperties = DialogProperties(),
    onDismissRequest: () -> Unit = {
        show.value = false
        viewModel?.restartRetriever()
    },
    dialogContent: @Composable () -> Unit,
) {
    if (show.value) {
        viewModel?.suspendRetriever()
        Dialog(
            properties = dialogProperties,
            onDismissRequest = onDismissRequest,
            content = dialogContent
        )
    }
}