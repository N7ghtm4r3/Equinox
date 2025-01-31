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

@ExperimentalComposeApi
data class Step(
    val actionsControls: @Composable (() -> Unit)? = null,
    val initiallyExpanded: Boolean = false,
    val enabled: MutableState<Boolean>? = null,
    val stepIcon: ImageVector,
    val title: StringResource,
    val content: @Composable ColumnScope.() -> Unit,
    val isError: (MutableState<Boolean>)? = null,
    val dismissAction: (() -> Unit)? = null,
    val dismissIcon: ImageVector = Icons.Default.Cancel,
    val confirmAction: (MutableState<Boolean>) -> Unit = { it.value = false },
    val confirmIcon: ImageVector = Icons.Default.CheckCircle,
)