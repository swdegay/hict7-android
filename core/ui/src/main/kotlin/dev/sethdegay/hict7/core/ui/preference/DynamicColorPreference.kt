package dev.sethdegay.hict7.core.ui.preference

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.sethdegay.hict7.core.common.res.R.string
import dev.sethdegay.hict7.core.designsystem.component.preference.Hict7BooleanPreference

@Composable
fun DynamicColorPreference(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Hict7BooleanPreference(
        modifier = modifier,
        title = stringResource(string.settings_dynamic_color_title),
        description = stringResource(string.settings_dynamic_color_description),
        checked = checked,
        onCheckedChange = onCheckedChange,
        icon = null,
    )
}

@Preview(showBackground = true)
@Composable
private fun DynamicColorPreferencePreview() {
    DynamicColorPreference(
        checked = true,
        onCheckedChange = {},
    )
}