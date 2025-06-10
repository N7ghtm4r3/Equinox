package com.tecknobit.equinoxcompose.components

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

/**
 * Method used to display a layout when a list of values is empty
 *
 * @param triggers The triggers to use to reinvoke the [loadingRoutine] automatically
 * @param containerModifier The modifier to apply to the container column
 * @param animations The set of the animations to use to animate the layout
 * @param textStyle The style to apply to the text
 * @param loadingRoutine The routine used to load the data
 * @param initialDelay An initial delay to apply to the [loadingRoutine] before to start
 * @param loadingIndicatorBackground The background to apply to the [loadingIndicator] content
 * @param loadingIndicator The loading indicator to display
 * @param contentLoaded The content to display when the data have been loaded
 * @param themeColor The color to use into the composable
 */
@Composable
fun LoadingItemUI(
    vararg triggers: Any?,
    containerModifier: Modifier = Modifier,
    animations: UIAnimations? = null,
    textStyle: TextStyle = LocalTextStyle.current,
    loadingRoutine: suspend () -> Boolean,
    initialDelay: Long? = null,
    contentLoaded: @Composable () -> Unit,
    themeColor: Color = MaterialTheme.colorScheme.primary,
    loadingIndicatorBackground: Color = MaterialTheme.colorScheme.background,
    loadingIndicator: @Composable () -> Unit = {
        Column(
            modifier = containerModifier
                .background(loadingIndicatorBackground),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(85.dp),
                strokeWidth = 8.dp,
                color = themeColor
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
                onEnter = animations.onEnter,
                onExit = animations.onExit,
                loadingIndicator = loadingIndicator,
                contentLoaded = contentLoaded,
                triggers = triggers
            )
        }
    } else {
        LoadingItemUIContent(
            loadingRoutine = loadingRoutine,
            initialDelay = initialDelay,
            loadingIndicator = loadingIndicator,
            contentLoaded = contentLoaded,
            triggers = triggers
        )
    }
}

/**
 * Method used to display the content of the [EmptyListUI]
 *
 * @param loadingRoutine The routine used to load the data
 * @param initialDelay An initial delay to apply to the [loadingRoutine] before to start
 * @param onEnter The [EnterTransition] to use
 * @param onExit The [ExitTransition] to use
 * @param loadingIndicator The loading indicator to display
 * @param contentLoaded The content to display when the data have been loaded
 * @param triggers The triggers to use to reinvoke the [loadingRoutine] automatically
 */
@Composable
private fun LoadingItemUIContent(
    loadingRoutine: suspend () -> Boolean,
    initialDelay: Long?,
    onEnter: EnterTransition = fadeIn(),
    onExit: ExitTransition = fadeOut(),
    loadingIndicator: @Composable () -> Unit,
    contentLoaded: @Composable () -> Unit,
    vararg triggers: Any?,
) {
    var isLoaded by remember { mutableStateOf(false) }
    LaunchedEffect(triggers) {
        isLoaded = false
        initialDelay?.let {
            delay(initialDelay)
        }
        isLoaded = loadingRoutine()
    }
    AnimatedVisibility(
        visible = isLoaded,
        enter = onEnter,
        exit = onExit
    ) {
        contentLoaded()
    }
    AnimatedVisibility(
        visible = !isLoaded,
        enter = onEnter,
        exit = onExit
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
        modifier = containerModifier,
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
 * @param retryContent The content to retry the failed operation
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
    retryContent: @Composable (() -> Unit)? = null,
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
        retryContent = retryContent
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
 * @param retryContent The content to retry the failed operation
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
    retryContent: @Composable (() -> Unit)? = null,
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
                retryContent = retryContent
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
            retryContent = retryContent
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
 * @param retryContent The content to retry the failed operation
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
    retryContent: @Composable (() -> Unit)? = null,
) {
    Column(
        modifier = containerModifier
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
        retryContent?.invoke()
    }
}

/**
 * Container component useful to display a custom empty state graphics
 *
 * @param animations The set of the animations to use to animate the layout
 * @param containerModifier The modifier to apply to the container [Column]
 * @param resourceModifier The modifier to apply to the [Image]
 * @param resourceSize The size occupied by the empty state
 * @param lightResource The empty state resource to display when is the light theme used
 * @param darkResource The empty state resource to display when is the dark theme used
 * @param useDarkResource Whether to use the [lightResource] or the [darkResource] one
 * @param contentDescription The content description
 * @param verticalSpacing The vertical spacing applied to the [title] and [subTitle] texts
 * @param title Not mandatory representative title
 * @param titleStyle The style to apply to the [title]
 * @param subTitle Not mandatory representative subtitle
 * @param subTitleStyle The style to apply to the [subTitle]
 * @param action Custom content used to allow the user to react to the empty state shown as needed, for example create
 * new item, change search, etc...
 */
@Composable
fun EmptyState(
    animations: UIAnimations? = null,
    containerModifier: Modifier = Modifier,
    resourceModifier: Modifier = Modifier,
    resourceSize: Dp = 200.dp,
    lightResource: DrawableResource,
    darkResource: DrawableResource,
    useDarkResource: Boolean = isSystemInDarkTheme(),
    contentDescription: String?,
    verticalSpacing: Dp = 5.dp,
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
        resource = if (useDarkResource)
            darkResource
        else
            lightResource,
        contentDescription = contentDescription,
        verticalSpacing = verticalSpacing,
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
 * @param resource The empty state resource to display
 * @param contentDescription The content description
 * @param verticalSpacing The vertical spacing applied to the [title] and [subTitle] texts
 * @param title Not mandatory representative title
 * @param titleStyle The style to apply to the [title]
 * @param subTitle Not mandatory representative subtitle
 * @param subTitleStyle The style to apply to the [subTitle]
 * @param action Custom content used to allow the user to react to the empty state shown as needed, for example create
 * new item, change search, etc...
 */
@Composable
fun EmptyState(
    animations: UIAnimations? = null,
    containerModifier: Modifier = Modifier,
    resourceModifier: Modifier = Modifier,
    resourceSize: Dp = 200.dp,
    resource: DrawableResource,
    contentDescription: String?,
    verticalSpacing: Dp = 5.dp,
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
        verticalSpacing = verticalSpacing,
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
 * @param resourceSize The size occupied by the empty state
 * @param lightResource The empty state resource to display when is the light theme used
 * @param darkResource The empty state resource to display when is the dark theme used
 * @param useDarkResource Whether to use the [lightResource] or the [darkResource] one
 * @param contentDescription The content description
 * @param verticalSpacing The vertical spacing applied to the [title] and [subTitle] texts
 * @param title Not mandatory representative title
 * @param titleStyle The style to apply to the [title]
 * @param subTitle Not mandatory representative subtitle
 * @param subTitleStyle The style to apply to the [subTitle]
 * @param action Custom content used to allow the user to react to the empty state shown as needed, for example create
 * new item, change search, etc...
 */
@Composable
fun EmptyState(
    animations: UIAnimations? = null,
    containerModifier: Modifier = Modifier,
    resourceModifier: Modifier = Modifier,
    resourceSize: Dp = 200.dp,
    lightResource: ImageVector,
    darkResource: ImageVector,
    useDarkResource: Boolean = isSystemInDarkTheme(),
    contentDescription: String?,
    verticalSpacing: Dp = 5.dp,
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
        resource = if (useDarkResource)
            darkResource
        else
            lightResource,
        contentDescription = contentDescription,
        verticalSpacing = verticalSpacing,
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
 * @param resource The empty state resource to display
 * @param contentDescription The content description
 * @param verticalSpacing The vertical spacing applied to the [title] and [subTitle] texts
 * @param title Not mandatory representative title
 * @param titleStyle The style to apply to the [title]
 * @param subTitle Not mandatory representative subtitle
 * @param subTitleStyle The style to apply to the [subTitle]
 * @param action Custom content used to allow the user to react to the empty state shown as needed, for example create
 * new item, change search, etc...
 */
@Composable
fun EmptyState(
    animations: UIAnimations? = null,
    containerModifier: Modifier = Modifier,
    resourceModifier: Modifier = Modifier,
    resourceSize: Dp = 200.dp,
    resource: ImageVector,
    contentDescription: String?,
    verticalSpacing: Dp = 5.dp,
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
        verticalSpacing = verticalSpacing,
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
 * @param resourceSize The size occupied by the empty state
 * @param lightResource The empty state resource to display when is the light theme used
 * @param darkResource The empty state resource to display when is the dark theme used
 * @param useDarkResource Whether to use the [lightResource] or the [darkResource] one
 * @param contentDescription The content description
 * @param verticalSpacing The vertical spacing applied to the [title] and [subTitle] texts
 * @param title Not mandatory representative title
 * @param titleStyle The style to apply to the [title]
 * @param subTitle Not mandatory representative subtitle
 * @param subTitleStyle The style to apply to the [subTitle]
 * @param action Custom content used to allow the user to react to the empty state shown as needed, for example create
 * new item, change search, etc...
 */
@Composable
fun EmptyState(
    animations: UIAnimations? = null,
    containerModifier: Modifier = Modifier,
    resourceModifier: Modifier = Modifier,
    resourceSize: Dp = 200.dp,
    lightResource: Painter,
    darkResource: Painter,
    useDarkResource: Boolean = isSystemInDarkTheme(),
    contentDescription: String?,
    verticalSpacing: Dp = 5.dp,
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
        resource = if (useDarkResource)
            darkResource
        else
            lightResource,
        contentDescription = contentDescription,
        verticalSpacing = verticalSpacing,
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
 * @param resource The empty state resource to display
 * @param contentDescription The content description
 * @param verticalSpacing The vertical spacing applied to the [title] and [subTitle] texts
 * @param title Not mandatory representative title
 * @param titleStyle The style to apply to the [title]
 * @param subTitle Not mandatory representative subtitle
 * @param subTitleStyle The style to apply to the [subTitle]
 * @param action Custom content used to allow the user to react to the empty state shown as needed, for example create
 * new item, change search, etc...
 */
@Composable
fun EmptyState(
    animations: UIAnimations? = null,
    containerModifier: Modifier = Modifier,
    resourceModifier: Modifier = Modifier,
    resourceSize: Dp = 200.dp,
    resource: Painter,
    contentDescription: String?,
    verticalSpacing: Dp = 5.dp,
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
                verticalSpacing = verticalSpacing,
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
            verticalSpacing = verticalSpacing,
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
 * @param resource The empty state resource to display
 * @param contentDescription The content description
 * @param verticalSpacing The vertical spacing applied to the [title] and [subTitle] texts
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
    verticalSpacing: Dp = 5.dp,
    title: String? = null,
    titleStyle: TextStyle = LocalTextStyle.current,
    subTitle: String? = null,
    subTitleStyle: TextStyle = LocalTextStyle.current,
    action: @Composable (() -> Unit)? = null,
) {
    Column(
        modifier = containerModifier,
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
                        vertical = verticalSpacing
                    ),
                text = title,
                style = titleStyle
            )
        }
        subTitle?.let {
            Text(
                modifier = Modifier
                    .padding(
                        vertical = verticalSpacing
                    ),
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