package dev.sethdegay.hict7.core.ui.preference.model

import androidx.compose.ui.graphics.vector.ImageVector

data class ToggleOption<T>(
    val label: String,
    val iconChecked: ImageVector,
    val iconUnchecked: ImageVector,
    val value: T,
    val onValueChanged: (T) -> Unit,
    val togglePosition: TogglePosition,
)
