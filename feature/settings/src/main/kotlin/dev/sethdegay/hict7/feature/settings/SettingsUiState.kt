package dev.sethdegay.hict7.feature.settings

import dev.sethdegay.hict7.core.model.ThemeConfig
import dev.sethdegay.hict7.core.model.UserData

sealed interface SettingsUiState {
    data object Loading : SettingsUiState

    data class Success(override val userData: UserData) : SettingsUiState

    val userData: UserData
        get() = UserData(
            themeConfig = ThemeConfig.FOLLOW_SYSTEM,
            dynamicColor = true,
            tickSound = true,
            completionSound = true,
            speakExercise = true,
        )
}
