package com.tecknobit.equinoxcompose.components

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tecknobit.equinoxcompose.resources.Res
import com.tecknobit.equinoxcompose.resources.an_error_occurred
import com.tecknobit.equinoxcompose.resources.loading_data
import com.tecknobit.equinoxcompose.resources.retry
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

/**
 * Method used to display a layout when a list of values is empty
 *
 * @param containerModifier The modifier to apply to the container column
 * @param animations The set of the animations to use to animate the layout
 * @param textStyle The style to apply to the text
 * @param loadingRoutine The routine used to load the data
 * @param initialDelay An initial delay to apply to the [loadingRoutine] before to start
 * @param loadingIndicator The loading indicator to display
 * @param contentLoaded The content to display when the data have been loaded
 * @param themeColor The color to use into the composable
 */
@Composable
fun LoadingItemUI(
    containerModifier: Modifier = Modifier,
    animations: UIAnimations? = null,
    textStyle: TextStyle = LocalTextStyle.current,
    loadingRoutine: suspend () -> Boolean,
    initialDelay: Long? = null,
    contentLoaded: @Composable () -> Unit,
    themeColor: Color = MaterialTheme.colorScheme.primary,
    loadingIndicator: @Composable () -> Unit = {
        Surface {
            Column(
                modifier = containerModifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(85.dp),
                    strokeWidth = 8.dp
                )
                Text(
                    modifier = Modifier
                        .padding(
                            top = 16.dp
                        ),
                    style = textStyle,
                    text = stringResource(Res.string.loading_data),
                    color = themeColor
                )
            }
        }
    },
) {
    if (animations != null) {
        AnimatedVisibility(
            visible = animations.visible,
            enter = animations.onEnter,
            exit = animations.onExit
        ) {
            LoadingItemUIContent(
                loadingRoutine = loadingRoutine,
                initialDelay = initialDelay,
                loadingIndicator = loadingIndicator,
                contentLoaded = contentLoaded
            )
        }
    } else {
        LoadingItemUIContent(
            loadingRoutine = loadingRoutine,
            initialDelay = initialDelay,
            loadingIndicator = loadingIndicator,
            contentLoaded = contentLoaded
        )
    }
}

/**
 * Method used to display the content of the [EmptyListUI]
 *
 * @param loadingRoutine The routine used to load the data
 * @param loadingIndicator The loading indicator to display
 * @param initialDelay An initial delay to apply to the [loadingRoutine] before to start
 * @param contentLoaded The content to display when the data have been loaded
 */
@Composable
private fun LoadingItemUIContent(
    loadingRoutine: suspend () -> Boolean,
    initialDelay: Long?,
    loadingIndicator: @Composable () -> Unit,
    contentLoaded: @Composable () -> Unit,
) {
    var isLoaded by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        initialDelay?.let {
            delay(initialDelay)
        }
        isLoaded = loadingRoutine()
    }
    AnimatedVisibility(
        visible = isLoaded,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        contentLoaded()
    }
    AnimatedVisibility(
        visible = !isLoaded,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        loadingIndicator()
    }
}

/**
 * Method used to display a layout when a list of values is empty
 *
 * @param containerModifier The modifier to apply to the container column
 * @param imageModifier The modifier to apply to the image icon
 * @param animations The set of the animations to use to animate the layout
 * @param textStyle The style to apply to the text
 * @param icon The icon to display
 * @param themeColor The color to use into the composable
 * @param subText The description of the layout
 */
@Composable
fun EmptyListUI(
    containerModifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    animations: UIAnimations? = null,
    textStyle: TextStyle = LocalTextStyle.current,
    icon: ImageVector,
    themeColor: Color = MaterialTheme.colorScheme.primary,
    subText: StringResource,
) {
    EmptyListUI(
        containerModifier = containerModifier,
        imageModifier = imageModifier,
        animations = animations,
        textStyle = textStyle,
        icon = icon,
        themeColor = themeColor,
        subText = stringResource(subText)
    )
}

/**
 * Method used to display a layout when a list of values is empty
 *
 * @param containerModifier The modifier to apply to the container column
 * @param imageModifier The modifier to apply to the image icon
 * @param animations The set of the animations to use to animate the layout
 * @param textStyle The style to apply to the text
 * @param icon The icon to display
 * @param themeColor The color to use into the composable
 * @param subText The description of the layout
 */
@Composable
fun EmptyListUI(
    containerModifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    animations: UIAnimations? = null,
    textStyle: TextStyle = LocalTextStyle.current,
    icon: ImageVector,
    themeColor: Color = MaterialTheme.colorScheme.primary,
    subText: String,
) {
    if (animations != null) {
        AnimatedVisibility(
            visible = animations.visible,
            enter = animations.onEnter,
            exit = animations.onExit
        ) {
            EmptyListUIContent(
                containerModifier = containerModifier,
                imageModifier = imageModifier,
                textStyle = textStyle,
                icon = icon,
                themeColor = themeColor,
                subText = subText
            )
        }
    } else {
        EmptyListUIContent(
            containerModifier = containerModifier,
            imageModifier = imageModifier,
            textStyle = textStyle,
            icon = icon,
            themeColor = themeColor,
            subText = subText
        )
    }
}

/**
 * Method used to display the content of the [EmptyListUI]
 *
 * @param containerModifier The modifier to apply to the container column
 * @param imageModifier The modifier to apply to the image icon
 * @param textStyle The style to apply to the text
 * @param icon The icon to display
 * @param themeColor The color to use into the composable
 * @param subText The description of the layout
 */
@Composable
@NonRestartableComposable
private fun EmptyListUIContent(
    containerModifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    icon: ImageVector,
    themeColor: Color = MaterialTheme.colorScheme.primary,
    subText: String,
) {
    Column(
        modifier = containerModifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = imageModifier
                .size(100.dp),
            imageVector = icon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(
                color = themeColor
            )
        )
        Text(
            style = textStyle,
            text = subText,
            color = themeColor
        )
    }
}

/**
 * Method used to display a layout when an error occurred
 *
 * @param containerModifier The modifier to apply to the container column
 * @param imageModifier The modifier to apply to the image icon
 * @param animations The set of the animations to use to animate the layout
 * @param textStyle The style to apply to the text
 * @param backgroundColor The color of the background
 * @param errorIcon The error icon used, as default is used the **Icons.Default.Error**
 * @param errorColor The error color used, as default is used the **MaterialTheme.colorScheme.errorContainer**
 * @param errorMessage The error that occurred or to indicate a generic error
 * @param retryAction The retry action to execute
 * @param retryText The retry message
 */
@Composable
fun ErrorUI(
    containerModifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    animations: UIAnimations? = null,
    textStyle: TextStyle = LocalTextStyle.current,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    errorIcon: ImageVector = Icons.Default.Error,
    errorColor: Color = MaterialTheme.colorScheme.error,
    errorMessage: StringResource = Res.string.an_error_occurred,
    retryAction: @Composable (() -> Unit)? = null,
    retryText: StringResource? = Res.string.retry,
) {
    ErrorUI(
        containerModifier = containerModifier,
        imageModifier = imageModifier,
        animations = animations,
        textStyle = textStyle,
        backgroundColor = backgroundColor,
        errorIcon = errorIcon,
        errorColor = errorColor,
        errorMessage = stringResource(errorMessage),
        retryAction = retryAction,
        retryText = retryText?.let { stringResource(it) }
    )
}

/**
 * Method used to display the layout when an error occurred
 *
 * @param containerModifier The modifier to apply to the container column
 * @param imageModifier The modifier to apply to the image icon
 * @param animations The set of the animations to use to animate the layout
 * @param textStyle The style to apply to the text
 * @param backgroundColor The color of the background
 * @param errorIcon The error icon used, as default is used the **Icons.Default.Error**
 * @param errorColor The error color used, as default is used the **MaterialTheme.colorScheme.errorContainer**
 * @param errorMessage The error that occurred or to indicate a generic error
 * @param retryAction The retry action to execute
 * @param retryText The retry message
 */
@Composable
fun ErrorUI(
    containerModifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    animations: UIAnimations? = null,
    textStyle: TextStyle = LocalTextStyle.current,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    errorIcon: ImageVector = Icons.Default.Error,
    errorColor: Color = MaterialTheme.colorScheme.error,
    errorMessage: String,
    retryAction: @Composable (() -> Unit)? = null,
    retryText: String? = null,
) {
    if (animations != null) {
        AnimatedVisibility(
            visible = animations.visible,
            enter = animations.onEnter,
            exit = animations.onExit
        ) {
            ErrorUIContent(
                containerModifier = containerModifier,
                imageModifier = imageModifier,
                textStyle = textStyle,
                backgroundColor = backgroundColor,
                errorIcon = errorIcon,
                errorColor = errorColor,
                errorMessage = errorMessage,
                retryAction = retryAction,
                retryText = retryText
            )
        }
    } else {
        ErrorUIContent(
            containerModifier = containerModifier,
            imageModifier = imageModifier,
            textStyle = textStyle,
            backgroundColor = backgroundColor,
            errorIcon = errorIcon,
            errorColor = errorColor,
            errorMessage = errorMessage,
            retryAction = retryAction,
            retryText = retryText
        )
    }
}

/**
 * Method used to display the content of the [ErrorUI] layout
 *
 * @param containerModifier The modifier to apply to the container column
 * @param imageModifier The modifier to apply to the image icon
 * @param textStyle The style to apply to the text
 * @param backgroundColor The color of the background
 * @param errorIcon The error icon used, as default is used the **Icons.Default.Error**
 * @param errorColor The error color used, as default is used the **MaterialTheme.colorScheme.errorContainer**
 * @param errorMessage The error that occurred or to indicate a generic error
 * @param retryAction The retry action to execute
 * @param retryText The retry message
 */
@Composable
private fun ErrorUIContent(
    containerModifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    errorIcon: ImageVector = Icons.Default.Error,
    errorColor: Color = MaterialTheme.colorScheme.error,
    errorMessage: String,
    retryAction: @Composable (() -> Unit)? = null,
    retryText: String? = null,
) {
    Column(
        modifier = containerModifier
            .fillMaxSize()
            .background(backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = imageModifier
                .size(100.dp),
            imageVector = errorIcon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(
                color = errorColor
            )
        )
        Text(
            style = textStyle,
            text = errorMessage,
            color = errorColor
        )
        if (retryAction != null && retryText != null) {
            var retryActionStart by remember { mutableStateOf(false) }
            TextButton(
                onClick = { retryActionStart = true }
            ) {
                Text(
                    text = retryText
                )
            }
            if (retryActionStart) {
                retryAction()
                retryActionStart = false
            }
        }
    }
}

/**
 * Container component useful to display a custom empty state graphics
 *
 * @param animations The set of the animations to use to animate the layout
 * @param containerModifier The modifier to apply to the container [Column]
 * @param resourceModifier The modifier to apply to the [Image]
 * @param resourceSize The size occupied by the [resource]
 * @param contentDescription The content description
 * @param title Not mandatory representative title
 * @param titleStyle The style to apply to the [title]
 * @param subTitle Not mandatory representative subtitle
 * @param subTitleStyle The style to apply to the [subTitle]
 * @param action Custom content used to allow the user to react to the empty state shown as needed, for example create
 * new item, change search, etc...
 */
@Composable
@ExperimentalMultiplatform
fun EmptyState(
    animations: UIAnimations? = null,
    containerModifier: Modifier = Modifier,
    resourceModifier: Modifier = Modifier,
    resourceSize: Dp = 200.dp,
    resource: DrawableResource,
    contentDescription: String?,
    title: String? = null,
    titleStyle: TextStyle = LocalTextStyle.current,
    subTitle: String? = null,
    subTitleStyle: TextStyle = LocalTextStyle.current,
    action: @Composable (() -> Unit)? = null,
) {
    EmptyState(
        animations = animations,
        containerModifier = containerModifier,
        resourceModifier = resourceModifier,
        resourceSize = resourceSize,
        resource = painterResource(resource),
        contentDescription = contentDescription,
        title = title,
        titleStyle = titleStyle,
        subTitle = subTitle,
        subTitleStyle = subTitleStyle,
        action = action
    )
}

/**
 * Container component useful to display a custom empty state graphics
 *
 * @param animations The set of the animations to use to animate the layout
 * @param containerModifier The modifier to apply to the container [Column]
 * @param resourceModifier The modifier to apply to the [Image]
 * @param resourceSize The size occupied by the [resource]
 * @param contentDescription The content description
 * @param title Not mandatory representative title
 * @param titleStyle The style to apply to the [title]
 * @param subTitle Not mandatory representative subtitle
 * @param subTitleStyle The style to apply to the [subTitle]
 * @param action Custom content used to allow the user to react to the empty state shown as needed, for example create
 * new item, change search, etc...
 */
@Composable
@ExperimentalMultiplatform
fun EmptyState(
    animations: UIAnimations? = null,
    containerModifier: Modifier = Modifier,
    resourceModifier: Modifier = Modifier,
    resourceSize: Dp = 200.dp,
    resource: ImageVector,
    contentDescription: String?,
    title: String? = null,
    titleStyle: TextStyle = LocalTextStyle.current,
    subTitle: String? = null,
    subTitleStyle: TextStyle = LocalTextStyle.current,
    action: @Composable (() -> Unit)? = null,
) {
    EmptyState(
        animations = animations,
        containerModifier = containerModifier,
        resourceModifier = resourceModifier,
        resourceSize = resourceSize,
        resource = rememberVectorPainter(
            image = resource
        ),
        contentDescription = contentDescription,
        title = title,
        titleStyle = titleStyle,
        subTitle = subTitle,
        subTitleStyle = subTitleStyle,
        action = action
    )
}

/**
 * Container component useful to display a custom empty state graphics
 *
 * @param animations The set of the animations to use to animate the layout
 * @param containerModifier The modifier to apply to the container [Column]
 * @param resourceModifier The modifier to apply to the [Image]
 * @param resourceSize The size occupied by the [resource]
 * @param contentDescription The content description
 * @param title Not mandatory representative title
 * @param titleStyle The style to apply to the [title]
 * @param subTitle Not mandatory representative subtitle
 * @param subTitleStyle The style to apply to the [subTitle]
 * @param action Custom content used to allow the user to react to the empty state shown as needed, for example create
 * new item, change search, etc...
 */
@Composable
@ExperimentalMultiplatform
fun EmptyState(
    animations: UIAnimations? = null,
    containerModifier: Modifier = Modifier,
    resourceModifier: Modifier = Modifier,
    resourceSize: Dp = 200.dp,
    resource: Painter,
    contentDescription: String?,
    title: String? = null,
    titleStyle: TextStyle = LocalTextStyle.current,
    subTitle: String? = null,
    subTitleStyle: TextStyle = LocalTextStyle.current,
    action: @Composable (() -> Unit)? = null,
) {
    if (animations != null) {
        AnimatedVisibility(
            visible = animations.visible,
            enter = animations.onEnter,
            exit = animations.onExit
        ) {
            EmptyStateContent(
                containerModifier = containerModifier,
                resourceModifier = resourceModifier,
                resourceSize = resourceSize,
                resource = resource,
                contentDescription = contentDescription,
                title = title,
                titleStyle = titleStyle,
                subTitle = subTitle,
                subTitleStyle = subTitleStyle,
                action = action
            )
        }
    } else {
        EmptyStateContent(
            containerModifier = containerModifier,
            resourceModifier = resourceModifier,
            resourceSize = resourceSize,
            resource = resource,
            contentDescription = contentDescription,
            title = title,
            titleStyle = titleStyle,
            subTitle = subTitle,
            subTitleStyle = subTitleStyle,
            action = action
        )
    }
}

/**
 * Content of the [EmptyState] component
 *
 * @param containerModifier The modifier to apply to the container [Column]
 * @param resourceModifier The modifier to apply to the [Image]
 * @param resourceSize The size occupied by the [resource]
 * @param contentDescription The content description
 * @param title Not mandatory representative title
 * @param titleStyle The style to apply to the [title]
 * @param subTitle Not mandatory representative subtitle
 * @param subTitleStyle The style to apply to the [subTitle]
 * @param action Custom content used to allow the user to react to the empty state shown as needed, for example create
 * new item, change search, etc...
 */
@Composable
@NonRestartableComposable
private fun EmptyStateContent(
    containerModifier: Modifier = Modifier,
    resourceModifier: Modifier = Modifier,
    resourceSize: Dp = 200.dp,
    resource: Painter,
    contentDescription: String?,
    title: String? = null,
    titleStyle: TextStyle = LocalTextStyle.current,
    subTitle: String? = null,
    subTitleStyle: TextStyle = LocalTextStyle.current,
    action: @Composable (() -> Unit)? = null,
) {
    Column(
        modifier = containerModifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = resourceModifier
                .size(resourceSize),
            painter = resource,
            contentDescription = contentDescription
        )
        title?.let {
            Text(
                modifier = Modifier
                    .padding(
                        vertical = 5.dp
                    ),
                text = title,
                style = titleStyle
            )
        }
        subTitle?.let {
            Text(
                text = subTitle,
                style = subTitleStyle
            )
        }
        action?.invoke()
    }
}

/**
 * Data class to use to animate a UI layout
 *
 * @property visible Whether the layout is visible or not
 * @property onEnter The [EnterTransition] to use
 * @property onExit The [ExitTransition] to use
 */
data class UIAnimations(
    val visible: Boolean,
    val onEnter: EnterTransition,
    val onExit: ExitTransition,
)