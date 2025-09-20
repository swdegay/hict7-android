package dev.sethdegay.hict7.util

import android.content.res.Configuration
import android.graphics.Color
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.util.Consumer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dev.sethdegay.hict7.MainUiState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

private val Configuration.isSystemInDarkTheme: Boolean
    get() = (uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

private fun ComponentActivity.isSystemInDarkTheme(): Flow<Boolean> = callbackFlow {
    channel.trySend(resources.configuration.isSystemInDarkTheme)
    val listener = Consumer<Configuration> {
        channel.trySend(it.isSystemInDarkTheme)
    }
    addOnConfigurationChangedListener(listener)
    awaitClose {
        removeOnConfigurationChangedListener(listener)
    }
}
    .distinctUntilChanged()
    .conflate()

private fun ComponentActivity.setupEdgeToEdge(darkTheme: Boolean) {
    enableEdgeToEdge(
        statusBarStyle = SystemBarStyle.auto(
            lightScrim = Color.TRANSPARENT,
            darkScrim = Color.TRANSPARENT,
            detectDarkMode = { darkTheme },
        ),
        navigationBarStyle = SystemBarStyle.auto(
            lightScrim = Color.argb(0xe6, 0xFF, 0xFF, 0xFF),
            darkScrim = Color.argb(0x80, 0x1b, 0x1b, 0x1b),
            detectDarkMode = { darkTheme },
        )
    )
}

private fun ComponentActivity.applyThemeSettings(
    uiState: StateFlow<MainUiState>,
    onThemeSettingsChange: (ThemeSettings) -> Unit,
) {
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            combine(
                isSystemInDarkTheme(),
                uiState
            ) { isSystemInDarkTheme, uiState ->
                ThemeSettings(
                    darkTheme = uiState.useDarkTheme(isSystemInDarkTheme),
                    dynamicColor = uiState.useDynamicColor,
                )
            }
                .onEach { onThemeSettingsChange(it) }
                .map { it.darkTheme }
                .distinctUntilChanged()
                .collect { setupEdgeToEdge(it) }
        }
    }
}

internal fun ComponentActivity.applyThemeSettings(
    uiState: StateFlow<MainUiState>,
): MutableState<ThemeSettings> {
    val themeSettings = mutableStateOf(
        ThemeSettings(
            darkTheme = resources.configuration.isSystemInDarkTheme,
            dynamicColor = false,
        )
    )
    applyThemeSettings(
        uiState = uiState,
        onThemeSettingsChange = { themeSettings.value = it },
    )
    return themeSettings
}

internal data class ThemeSettings(
    val darkTheme: Boolean,
    val dynamicColor: Boolean,
)
