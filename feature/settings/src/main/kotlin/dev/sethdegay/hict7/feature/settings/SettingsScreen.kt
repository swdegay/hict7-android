package dev.sethdegay.hict7.feature.settings

import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.sethdegay.hict7.core.common.res.R.string
import dev.sethdegay.hict7.core.designsystem.component.CardGroupTitle
import dev.sethdegay.hict7.core.designsystem.component.CommonLoadingScreen
import dev.sethdegay.hict7.core.designsystem.component.cardGroupLeadingItem
import dev.sethdegay.hict7.core.designsystem.component.cardGroupMiddleItem
import dev.sethdegay.hict7.core.designsystem.component.cardGroupTrailingItem
import dev.sethdegay.hict7.core.designsystem.component.preference.Hict7BooleanPreference
import dev.sethdegay.hict7.core.designsystem.component.preference.Hict7PreferenceEntry
import dev.sethdegay.hict7.core.designsystem.icon.Hict7Icons
import dev.sethdegay.hict7.core.designsystem.util.asComposableIconButton
import dev.sethdegay.hict7.core.model.ThemeConfig
import dev.sethdegay.hict7.core.model.UserData
import dev.sethdegay.hict7.core.ui.preference.DynamicColorPreference
import dev.sethdegay.hict7.core.ui.preference.TogglePreference
import dev.sethdegay.hict7.core.ui.preference.model.ToggleOption
import dev.sethdegay.hict7.core.ui.preference.model.TogglePosition

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navigateUp: () -> Unit,
    navigateToOssLicensesActivity: () -> Unit,
    viewModel: SettingsViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()

    val listState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(text = stringResource(string.settings_top_app_bar_title))
                },
                navigationIcon = Hict7Icons.ArrowBack.asComposableIconButton(
                    onClick = navigateUp,
                    contentDescription = stringResource(string.common_navigate_up_content_description),
                ),
                scrollBehavior = scrollBehavior,
            )
        }
    ) { padding ->
        when (uiState) {
            is SettingsUiState.Loading -> CommonLoadingScreen(
                modifier = Modifier.padding(padding),
            )

            is SettingsUiState.Success -> {
                SettingsScreen(
                    modifier = Modifier.padding(padding),
                    listState = listState,
                    userData = uiState.userData,
                    setThemeConfig = { viewModel.setThemeConfig(it) },
                    setDynamicColor = { viewModel.setDynamicColor(it) },
                    setTickSound = { viewModel.setTickSound(it) },
                    setCompletionSound = { viewModel.setCompletionSound(it) },
                    setSpeakExercise = { viewModel.setSpeakExercise(it) },
                    navigateToOssLicensesActivity = navigateToOssLicensesActivity,
                )
            }
        }
    }
}

@Composable
private fun SettingsScreen(
    modifier: Modifier,
    listState: LazyListState,
    userData: UserData,
    setThemeConfig: (ThemeConfig) -> Unit,
    setDynamicColor: (Boolean) -> Unit,
    setTickSound: (Boolean) -> Unit,
    setCompletionSound: (Boolean) -> Unit,
    setSpeakExercise: (Boolean) -> Unit,
    navigateToOssLicensesActivity: () -> Unit,
) {
    LazyColumn(modifier = modifier.fillMaxSize(), state = listState) {
        item { Spacer(modifier = Modifier.size(16.dp)) }
        item {
            CardGroupTitle(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                title = stringResource(string.settings_user_interface_section_title)
            )
        }
        cardGroupLeadingItem {
            TogglePreference(
                modifier = Modifier.padding(it),
                title = stringResource(string.settings_theme_title),
                description = stringResource(string.settings_theme_description),
                options = themeConfigOptions(onThemeConfigChanged = setThemeConfig),
                onCheckedRequest = { value -> userData.themeConfig == value },
            )
        }
        cardGroupTrailingItem {
            DynamicColorPreference(
                checked = userData.dynamicColor,
                onCheckedChange = setDynamicColor,
            )
        }
        item { Spacer(modifier = Modifier.size(16.dp)) }
        item {
            CardGroupTitle(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                title = stringResource(string.settings_audio_section_title)
            )
        }
        cardGroupLeadingItem {
            Hict7BooleanPreference(
                title = stringResource(string.settings_tick_sound_title),
                description = stringResource(string.settings_tick_sound_description),
                checked = userData.tickSound,
                onCheckedChange = setTickSound,
            )
        }
        cardGroupMiddleItem {
            Hict7BooleanPreference(
                title = stringResource(string.settings_completion_sound_title),
                description = stringResource(string.settings_completion_sound_description),
                checked = userData.completionSound,
                onCheckedChange = setCompletionSound,
            )
        }
        cardGroupTrailingItem {
            Hict7BooleanPreference(
                title = stringResource(string.settings_speak_exercise_title),
                description = stringResource(string.settings_speak_exercise_description),
                checked = userData.speakExercise,
                onCheckedChange = setSpeakExercise,
            )
        }
        item { Spacer(modifier = Modifier.size(16.dp)) }
        item {
            CardGroupTitle(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                title = stringResource(string.settings_about_section_title)
            )
        }
        cardGroupLeadingItem {
            Hict7PreferenceEntry(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(it),
                title = stringResource(string.settings_version_title),
                description = versionName,
            )
        }
        cardGroupTrailingItem {
            Hict7PreferenceEntry(
                modifier = Modifier
                    .clickable(onClick = navigateToOssLicensesActivity)
                    .fillMaxWidth()
                    .padding(it),
                title = stringResource(string.settings_open_source_licenses_title),
                description = stringResource(string.settings_open_source_licenses_description),
            )
        }
    }
}

private val versionName: String
    @Composable
    get() {
        val context = LocalContext.current
        val packageInfo = try {
            context.packageManager.getPackageInfo(context.packageName, 0)
        } catch (_: PackageManager.NameNotFoundException) {
            null
        }
        return packageInfo?.versionName ?: stringResource(string.common_not_available)
    }

@Composable
private fun themeConfigOptions(
    context: Context = LocalContext.current,
    onThemeConfigChanged: (ThemeConfig) -> Unit,
): List<ToggleOption<ThemeConfig>> {
    return remember {
        listOf(
            ToggleOption(
                label = context.getString(string.settings_system_theme_title),
                iconChecked = Hict7Icons.SystemChecked,
                iconUnchecked = Hict7Icons.SystemUnchecked,
                value = ThemeConfig.FOLLOW_SYSTEM,
                onValueChanged = onThemeConfigChanged,
                togglePosition = TogglePosition.LEADING,
            ),
            ToggleOption(
                label = context.getString(string.settings_light_theme_title),
                iconChecked = Hict7Icons.LightModeChecked,
                iconUnchecked = Hict7Icons.LightModeUnchecked,
                value = ThemeConfig.LIGHT,
                onValueChanged = onThemeConfigChanged,
                togglePosition = TogglePosition.MIDDLE,
            ),
            ToggleOption(
                label = context.getString(string.settings_dark_theme_title),
                iconChecked = Hict7Icons.DarkModeChecked,
                iconUnchecked = Hict7Icons.DarkModeUnchecked,
                value = ThemeConfig.DARK,
                onValueChanged = onThemeConfigChanged,
                togglePosition = TogglePosition.TRAILING,
            ),
        )
    }
}
