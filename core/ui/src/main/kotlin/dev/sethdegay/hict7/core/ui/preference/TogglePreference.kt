package dev.sethdegay.hict7.core.ui.preference

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.sethdegay.hict7.core.designsystem.component.preference.Hict7PreferenceEntry
import dev.sethdegay.hict7.core.designsystem.util.asComposableIcon
import dev.sethdegay.hict7.core.ui.preference.model.ToggleOption
import dev.sethdegay.hict7.core.ui.preference.model.TogglePosition

@Composable
fun <T> TogglePreference(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    spaceBetween: Dp = 16.dp,
    options: List<ToggleOption<T>>,
    onCheckedRequest: (T) -> Boolean,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(spaceBetween),
    ) {
        Hict7PreferenceEntry(
            title = title,
            description = description,
        )
        ToggleButtons(
            options = options,
            onCheckedRequest = onCheckedRequest,
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun <T> ToggleButtons(
    modifier: Modifier = Modifier,
    options: List<ToggleOption<T>>,
    onCheckedRequest: (T) -> Boolean,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
    ) {
        options.forEachIndexed { index, option ->
            ToggleButton(
                modifier = Modifier.weight(1f),
                toggleOption = option,
                checked = onCheckedRequest(option.value),
            )
        }
    }
}

@ExperimentalMaterial3ExpressiveApi
@Composable
private fun <T> ToggleButton(
    modifier: Modifier = Modifier,
    toggleOption: ToggleOption<T>,
    checked: Boolean,
    enabled: Boolean = true,
) {
    ToggleButton(
        checked = checked,
        onCheckedChange = { toggleOption.onValueChanged(toggleOption.value) },
        modifier = modifier
            .semantics { role = Role.RadioButton }
            .fillMaxWidth(),
        shapes = when (toggleOption.togglePosition) {
            TogglePosition.LEADING -> ButtonGroupDefaults.connectedLeadingButtonShapes()
            TogglePosition.TRAILING -> ButtonGroupDefaults.connectedTrailingButtonShapes()
            TogglePosition.MIDDLE -> ButtonGroupDefaults.connectedMiddleButtonShapes()
        },
        enabled = enabled,
    ) {
        (if (checked) toggleOption.iconChecked else toggleOption.iconUnchecked)
            .asComposableIcon().invoke()
        Spacer(modifier = Modifier.size(ToggleButtonDefaults.IconSpacing))
        Text(toggleOption.label)
    }
}
